import java.io.File;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.HashMap;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.io.IOException;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.util.Scanner;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

/** 
 *  @author David Dominguez Hooper
 *  Gitlet is a version contol system created 
 *  as a project for the CS61B Data Structures course
 *  at UC Berkeley.
 */

public class Gitlet {
    private static final String GITLET_DIR = ".gitlet/";
    private HashSet<String> addedFiles;
    private HashSet<String> filesToRemove;
    private HashMap<Integer, Commit> commits;
    private HashMap<String, Branch> branches;
    private String currentBranchName;
    private File lastAddedFiles = new File(GITLET_DIR + "addedFiles.ser");
    private File lastFilesToRemove = new File(GITLET_DIR + "filesToRemove.ser");
    private File lastCommits = new File(GITLET_DIR + "commits.ser");
    private File lastBranches = new File(GITLET_DIR + "branches.ser");
    private File lastCurrentBranchName = new File(GITLET_DIR + "currentBranchName.ser");
    
    /**
     * init() initializes Gitlet by checking 
     * to see if .gitlet folder is already made.
     * Creates master branch and initital commit.
     */
    private void init() {
        File dir = new File(GITLET_DIR);
        if (dir.exists()) {
            System.out.println("A gitlet version control system already ");
            System.out.print("exists in the current directory.");
        } else {
            dir.mkdirs();
            addedFiles = new HashSet<String>();
            filesToRemove = new HashSet<String>();
            commits = new HashMap<Integer, Commit>();
            branches = new HashMap<String, Branch>();
            Commit firstCommit = new Commit();
            commits.put(firstCommit.getId(), firstCommit);
            Branch master = new Branch();
            branches.put(master.getBranchName(), master);
            currentBranchName = master.getBranchName();
        }
    }
    /**
     * add() stages files to Gitlet to be ready to be committed.
     */
    private void add(String[] args) {
        Commit lastCommit = commits.get(branches.get(currentBranchName).getLastCommit());
        String fileName = args[1];
        File fileToAdd = new File(fileName);
        if (filesToRemove.contains(fileName)) {
            filesToRemove.remove(fileName);
            return;
        } else if (!fileToAdd.exists()) {
            System.out.println("File does not exist.");
            return;
        } else if (lastCommit.containsFile(fileName)
                    && lastCommit.getFileHash(fileName).equals(hashFile(fileToAdd, "SHA-256"))) {
            System.out.println("File has not been modified since the last commit.");
            return;
        } else {
            addedFiles.add(fileName);
        }
    }
    /**
     * commit() adds all the staged files to a Commit and also removes
     * any pending files that need to be removed.
     */
    private void commit(String[] args) {
        Branch currentBranch = branches.get(currentBranchName);
        Commit lastCommit = commits.get(currentBranch.getLastCommit());
        if (addedFiles.isEmpty() && filesToRemove.isEmpty()) {
            System.out.println("No changes added to the commit.");
            return;
        } else if (args.length < 2) {
            System.out.println("Please enter a commit message.");
            return;
        }
        String commitMessage = args[1];
        Commit newCommit = new Commit(commitMessage, lastCommit, commits.size());
        for (String file : filesToRemove) {
            newCommit.removeFileFromInheritedCommits(file); 
        }
        filesToRemove.clear();
        for (String curFile : addedFiles) {
            File direct = new File(GITLET_DIR + "/" + curFile);
            if (!direct.exists()) {
                direct.mkdirs();
            }
            File cur = new File(curFile);
            String fileHash = hashFile(cur, "SHA-256");
            newCommit.addFile(curFile, fileHash);
            File f = new File(curFile);
            createFile(GITLET_DIR + curFile + "/" 
                                + fileHash + f.getName(), getText(curFile));
        }
        commits.put(newCommit.getId(), newCommit);
        currentBranch.updateLastCommit(newCommit.getId());
        addedFiles.clear();
    }
    /**
     * remove() adds files to be removed from Gitlet.
     */
    private void remove(String[] args) {
        Commit lastCommit = commits.get(branches.get(currentBranchName).getLastCommit());
        String removeMessage = args[1];
        if (addedFiles.contains(removeMessage)) {
            addedFiles.remove(removeMessage);
        }
        else if (!lastCommit.getAllCommittedFiles().containsKey(removeMessage)) {
            System.out.println("No reason to remove the file.");
            return;
        } else {
            filesToRemove.add(removeMessage);
        }
    }
    /**
     * log() provides a printed-out log of the last commit's history.
     */
    private void log() {
        Commit lastCommit = commits.get(branches.get(currentBranchName).getLastCommit());
        System.out.println(lastCommit.getCommitHistory());
    }
    /**
     * global-log() prints out every commit made in Gitlet.
     */
    private void globalLog() {
        String globalLog = "";
        boolean firstRun = true;
        for (Commit commit : commits.values()) {
            if (firstRun) {
                globalLog += commit.getLog();
                firstRun = false;
            } else {
                globalLog += "\n\n" + commit.getLog();
            }
        }
        System.out.println(globalLog);
    }
    /**
     * find() finds any commits with a certain message and prints them for the user.
     */
    private void find(String[] args) {
        String foundCommitIDs = "";
        boolean firstRun = true;
        String findMessage = args[1];
        for (Commit commit : commits.values()) {
            if (findMessage.equals(commit.getMessage())) {
                if (firstRun) {
                    foundCommitIDs += commit.getId();
                    firstRun = false;
                } else {
                    foundCommitIDs += "\n" + commit.getId();
                }
            }   
        }
        if (firstRun) {
            System.out.println("Found no commit with that message.");
        } else {
            System.out.println(foundCommitIDs);
        }
    }
    /**
     * status() prints the current Gitlet status 
     * of the current branch, staged files, and
     * marked files for removal.
     */
    private void status() {
        String status = "=== Branches ===\n";
        for (String branch : branches.keySet()) {
            if (branch.equals(currentBranchName)) {
                status += "*";
            }
            status += branch + "\n";
        }
        status += "\n=== Staged Files ===\n";
        for (String added : addedFiles) {
            status += added + "\n";
        }
        status += "\n=== Files Marked for Removal ===\n";
        for (String markedRemove : filesToRemove) {
            status += markedRemove + "\n";
        }
        System.out.print(status);
    }
    /**
     * checkout() either reverts the given file back 
     * to the current commit or the supplied commit, 
     * or it reverts the workspace back to the supplied
     * branch.
     */
    private void checkout(String[] args) {
        Branch currentBranch = branches.get(currentBranchName);
        Commit lastCommit = commits.get(currentBranch.getLastCommit());
        if (args.length == 2) {
            if (branches.containsKey(args[1])) {
                if (currentBranchName.equals(args[1])) {
                    System.out.println("No need to checkout the current branch.");
                } else {
                    currentBranchName = args[1];
                    currentBranch = branches.get(currentBranchName);
                    lastCommit = commits.get(currentBranch.getLastCommit());
                    for (String file : lastCommit.getAllCommittedFiles().keySet()) {
                        restoreFile(file, lastCommit);
                    }
                }
            } else if (lastCommit.containsFile(args[1])) {
                restoreFile(args[1], lastCommit);
            } else {
                System.out.println("File does not exist in the most recent commit, ");
                System.out.print("or no such branch exists.");
            }
        } else if (args.length == 3) {
            int commitId = Integer.parseInt(args[1]);
            String fileName = args[2];
            if (!commits.containsKey(commitId)) {
                System.out.println("No commit with that id exists.");
            } else {
                Commit curCommit = commits.get(commitId);
                HashMap<String, String> allCommittedFiles = curCommit.getAllCommittedFiles();
                if (!allCommittedFiles.containsKey(fileName)) {
                    System.out.println("File does not exist in that commit.");
                } else  {
                    restoreFile(fileName, curCommit);
                }
            } 
        }
    }
    /**
     * branch() creates a new branch in gitlet with the 
     * supplied name from the user.
     */
    private void branch(String[] args) {
        String branchName = args[1];
        if (branches.containsKey(branchName)) {
            System.out.println("A branch with that name already exists.");
        } else {
            Branch newBranch = 
                new Branch(branchName, branches.get(currentBranchName).getLastCommit());
            branches.put(branchName, newBranch);
        }
    }
    /**
     * removeBranch() removes the requested branch 
     * if there exists a branch with that name.
     */
    private void removeBranch(String[] args) {
        String branchName = args[1];
        if (!branches.containsKey(branchName)) {
            System.out.println("A branch with that name does not exist.");
        } else if (currentBranchName.equals(branchName)) {
            System.out.println("Cannot remove the current branch.");
        } else {
            branches.remove(branchName);
        }
    }
    /**
     * reset() restores all files to their versions 
     * in the commit with the given id. Also moves the 
     * current branch's head to that commit node.
     */
    private void reset(String[] args) {
        int commitId = Integer.parseInt(args[1]);
        if (!commits.containsKey(commitId)) {
            System.out.println("No commit with that id exists.");
        } else {
            Commit lastCommit = commits.get(commitId);
            for (String file : lastCommit.getAllCommittedFiles().keySet()) {
                restoreFile(file, lastCommit);
            }
            branches.get(currentBranchName).updateLastCommit(commitId);
        }
    }
    /**
     * merge() merges files from the head of the given branch
     * into the head of the current branch.
     */
    private void merge(String[] args) {
        Branch currentBranch = branches.get(currentBranchName);
        Commit lastCommit = commits.get(currentBranch.getLastCommit());
        HashMap<String, Integer> currentFilesLastMod = lastCommit.getAllLastModified();
        String branchName = args[1];
        if (!branches.containsKey(branchName)) {
            System.out.println("A branch with that name does not exist.");
        } else if (currentBranchName.equals(branchName)) {
            System.out.println("Cannot merge a branch with itself.");
        } else {
            Branch givenBranch = branches.get(branchName);
            Commit gBranchCommit = commits.get(givenBranch.getLastCommit());
            HashMap<String, Integer> givenFilesLastMod = gBranchCommit.getAllLastModified();
            int splitPointCommit = findSplitPoint(gBranchCommit);
            for (String file : gBranchCommit.getAllCommittedFiles().keySet()) {
                if (givenFilesLastMod.get(file) > splitPointCommit) {
                    if (!lastCommit.containsFile(file)) {
                        restoreFile(file, gBranchCommit);
                    } else {
                        if (currentFilesLastMod.get(file) > splitPointCommit) {
                            String givenFileHash = gBranchCommit.getFileHash(file);
                            File curFile = new File(file);
                            createFile(file + ".conflicted", getText(GITLET_DIR + file + "/" 
                                        + givenFileHash + curFile.getName()));
                        } else {
                            restoreFile(file, gBranchCommit);
                        }
                    }
                } 
            }
        }
    }
    /**
     * rebase() finds the split point of the current branch 
     * and the given branch, then snaps off the current branch 
     * at this point, then reattaches the current branch to 
     * the head of the given branch.
     */
    private void rebase(String[] args) {
        interactiveRebase(args, false);
    }
    /**
     * interactiveRebase() does the same thing as rebase, but 
     * also allows you to step through each commit and decide what to do.
     */
    private void interactiveRebase(String[] args, boolean interactive) {
        String branchName = args[1];
        if (!branches.containsKey(branchName)) {
            System.out.println("A branch with that name does not exist.");
        } else if (currentBranchName.equals(branchName)) {
            System.out.println("Cannot merge a branch with itself.");
        } else {
            Branch currentBranch = branches.get(currentBranchName);
            Commit lastCommit = commits.get(currentBranch.getLastCommit());
            Branch givenBranch = branches.get(branchName);
            Commit gBranchCommit = commits.get(givenBranch.getLastCommit());
            Commit lastCommitPointer = lastCommit;
            while (lastCommitPointer != null) {
                if (lastCommitPointer.getId() == gBranchCommit.getId()) {
                    System.out.println("Already up-to-date.");
                    return;
                }
                lastCommitPointer = lastCommitPointer.getOldCommit();
            }
            TreeSet<Integer> commitsOfGBranch = new TreeSet<Integer>();
            Commit pointer = gBranchCommit;
            while (pointer != null) {
                if (lastCommit.getId() == pointer.getId()) {
                    currentBranch.updateLastCommit(pointer.getId());
                    return;
                }
                commitsOfGBranch.add(pointer.getId());
                pointer = pointer.getOldCommit();
            }
            pointer = commits.get(currentBranch.getLastCommit());
            TreeSet<Integer> commitsOfCBranch = new TreeSet<Integer>();
            int splitPointCommit = -1;
            while (pointer != null) {
                if (commitsOfGBranch.contains(pointer.getId())) {
                    splitPointCommit = pointer.getId();
                    break;
                }
                commitsOfCBranch.add(pointer.getId());
                pointer = pointer.getOldCommit();
            }
            int[] firstLast = {commitsOfCBranch.first(), commitsOfCBranch.last()};
            currentBranch.updateLastCommit(gBranchCommit.getId());
            for (int commitID : commitsOfCBranch) {
                Commit curOldCommit = commits.get(commitID);
                String newMessage = curOldCommit.getMessage();
                if (interactive) {
                    String decision = 
                        iRebaseUserInput(curOldCommit, commitID, firstLast[0], firstLast[1]);
                    if (decision.equals("s")) {
                        break;
                    } else if ((decision.equals("m"))) {
                        System.out.println("Please enter a new message for this commit.");
                        Scanner in = new Scanner(System.in);
                        newMessage = in.nextLine();
                    }
                }
                HashMap<String, String> newFiles = curOldCommit.getNewCommittedFiles();
                lastCommit = commits.get(currentBranch.getLastCommit());
                Commit newCommit = new Commit(newMessage, lastCommit, commits.size());
                HashSet<String> removedFiles = curOldCommit.getRemovedFiles();
                for (String curFile : newFiles.keySet()) {
                    newCommit.addFile(curFile, newFiles.get(curFile));
                }
                for (String removedFile : removedFiles) {
                    newCommit.removeFileFromInheritedCommits(removedFile);
                }
                commits.put(newCommit.getId(), newCommit);
                currentBranch.updateLastCommit(newCommit.getId());
            }
            lastCommit = commits.get(currentBranch.getLastCommit());
            for (String file : lastCommit.getAllCommittedFiles().keySet()) {
                restoreFile(file, lastCommit);
            }
        }
    }
    /**
     * main() is where the processing of user commands happen
     * as well as serialization.
     */
    public static void main(String[] args) {
        Gitlet gitlet = new Gitlet();
        String command = args[0];
        if (!command.equals("init")) {
            gitlet.deserialize();
        }
        switch (command) {
            case "init":
                gitlet.init();
                break;
            case "add":
                gitlet.add(args);
                break;  
            case "commit":
                gitlet.commit(args);
                break;
            case "rm":
                gitlet.remove(args);
                break; 
            case "log":
                gitlet.log();
                break;  
            case "global-log": 
                gitlet.globalLog();
                break;
            case "find":
                gitlet.find(args);
                break;
            case "status":
                gitlet.status();
                break;
            case "checkout":
                if (gitlet.dangerous().equals("yes")) {
                    gitlet.checkout(args);
                }
                break;
            case "branch":
                gitlet.branch(args);
                break;
            case "rm-branch":
                gitlet.removeBranch(args);
                break;
            case "reset":
                if (gitlet.dangerous().equals("yes")) {
                    gitlet.reset(args);
                }
                break;
            case "merge":
                if (gitlet.dangerous().equals("yes")) {
                    gitlet.merge(args);
                }
                break;
            case "rebase":
                if (gitlet.dangerous().equals("yes")) {
                    gitlet.rebase(args);
                }
                break;
            case "i-rebase":
                if (gitlet.dangerous().equals("yes")) {
                    gitlet.interactiveRebase(args, true);
                }
                break;
            default:
                System.out.println("Invalid command.");  
                break;
        }
        gitlet.serialize();
    }
    /**
     * calcFirstLastCommit() will generate an array containing 
     * the first and last commits from all of a branch's commits.
     */
    private int[] calcFirstLastCommit(TreeSet<Integer> commitsOfGBranch) {
        int i = 0;
        int initialCommitOfG = 0;
        for (int cID : commitsOfGBranch) {
            if (i == 1) {
                initialCommitOfG = cID;
            }
            i += 1;
        }
        int lastCommitOfG = commitsOfGBranch.last();
        int[] firstLast = {initialCommitOfG, lastCommitOfG};
        return firstLast;
    }
    /**
     * iRebaseUserInput() is a helper method for interactiveRebase()
     * and it mainly processes the user's choices for interactiveRebase.
     */
    private String iRebaseUserInput(Commit curCommit, 
            int commitID, int initialCommitOfG, int lastCommitOfG) {
        System.out.println("Currently replaying:");
        System.out.println(curCommit.getLog());
        String iMessage1 = "Would you like to (c)ontinue, ";
        String iMessage2 = "(s)kip this commit, or change this commit's (m)essage?";
        System.out.println(iMessage1 + iMessage2);
        Scanner in = new Scanner(System.in);
        String answer = in.nextLine();
        while (!answer.equals("c") && !answer.equals("s") && !answer.equals("m")) {
            System.out.println(iMessage1 + iMessage2);
            answer = in.nextLine();
        }
        if (answer.equals("s")) {
            if (commitID != initialCommitOfG || commitID != lastCommitOfG) {
                return "s";
            } else {
                while (!answer.equals("c") && !answer.equals("m")) {
                    System.out.println(iMessage1 + iMessage2);
                    answer = in.nextLine();
                }
                if ((answer.equals("m"))) {
                    return "m";
                } else {
                    return "c";
                }
            }
        } else if ((answer.equals("m"))) {
            return "m";
        } else {
            return "c";
        }
    }
    /**
     * findSplitPoint() will find the integer id of a commit 
     * that is the split point of the given branch's commit 
     * and the current branch's last commit.
     */
    private int findSplitPoint(Commit gBranchCommit) {
        Branch currentBranch = branches.get(currentBranchName);
        HashSet<Integer> commitsOfGBranch = new HashSet<Integer>();
        Commit pointer = gBranchCommit;
        while (pointer != null) {
            commitsOfGBranch.add(pointer.getId());
            pointer = pointer.getOldCommit();
        }
        pointer = commits.get(currentBranch.getLastCommit());
        int splitPointCommit = -1;
        while (pointer != null) {
            if (commitsOfGBranch.contains(pointer.getId())) {
                splitPointCommit = pointer.getId();
                break;
            } else  {
                pointer = pointer.getOldCommit();
            }
        }
        return splitPointCommit;
    }
    /**
     * dangerous() will print out a message to the user to 
     * check if they agree to the command that may alter the workspace.
     */
    private String dangerous() {
        System.out.println("Warning: ");
        System.out.print("The command you entered may alter the files in your working directory. ");
        System.out.print("Uncommitted changes may be lost. ");
        System.out.print("Are you sure you want to continue? (yes/no)");
        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }
    /**
     * restoreFile() will restore a file 
     * to how it was in the given commit.
     */
    private void restoreFile(String fileName, Commit curCommit) {
        File curFile = new File(fileName);
        String fileHashFromLastCommit = curCommit.getFileHash(fileName);
        if (curFile.exists()) {
            writeFile(fileName, getText(GITLET_DIR + fileName + "/" 
                        + fileHashFromLastCommit + curFile.getName()));
        } else {
            createFile(fileName, getText(GITLET_DIR + fileName + "/" 
                        + fileHashFromLastCommit + curFile.getName()));
        }
    }
    /**
     * readObject() reads in serialized files 
     * that contain java class objects that Gitlet uses
     */
    private <K> K readObject(File f) {
        try {
            FileInputStream fileIn = new FileInputStream(f);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            K e = (K) in.readObject();
            in.close();
            fileIn.close();
            return e;
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return null;
        }
    }
    /**
     * writeObject() writes serialized files 
     * that contain java class objects that Gitlet uses
     */
    private <K> void writeObject(File fileName, K object) {
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(object);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
    /**
     * serialize() automates the serialization of all 
     * java class objects that Gitlet uses
     */
    private void serialize() {
        writeObject(lastCurrentBranchName, currentBranchName);
        writeObject(lastAddedFiles, addedFiles);
        writeObject(lastFilesToRemove, filesToRemove);
        writeObject(lastCommits, commits);
        writeObject(lastBranches, branches);  
    }
    /**
     * deserialize() automates the reading in of all 
     * java class objects that Gitlet uses
     */
    private void deserialize() {
        addedFiles = readObject(lastAddedFiles);
        filesToRemove = readObject(lastFilesToRemove);
        commits = readObject(lastCommits);
        branches = readObject(lastBranches);
        currentBranchName = readObject(lastCurrentBranchName); 
    }
/**
 * A couple of utility methods.
 * @author Joseph Moghadam :
 */
    /**
     * Returns the text from a standard text file (won't work with special
     * characters).
     */
    private static String getText(String fileName) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(fileName));
            return new String(encoded, StandardCharsets.UTF_8);
        } catch (IOException e) {
            return "";
        }
    }
     /**
     * Creates a new file with the given fileName and gives it the text
     * fileText.
     */
    private void createFile(String fileName, String fileText) {
        File f = new File(fileName);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        writeFile(fileName, fileText);
    }
    /**
     * Replaces all text in the existing file with the given text.
     */
    private void writeFile(String fileName, String fileText) {
        FileWriter fw = null;
        try {
            File f = new File(fileName);
            fw = new FileWriter(f, false);
            fw.write(fileText);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /* Credit for hashFile function: 
     * http://www.codejava.net/coding/
     * how-to-calculate-md5-and-sha-hash-values-in-java
     */
    private static final int FIRST = 1024;
    private static final int SECOND = 0xff;
    private static final int THIRD = 0x100;
    private static final int FOURTH = 16;
    /**
     * hashFile() creates a hash from a file
     */
    private static String hashFile(File file, String algorithm) {
        try (FileInputStream inputStream = new FileInputStream(file)) {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            byte[] bytesBuffer = new byte[FIRST];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(bytesBuffer)) != -1) {
                digest.update(bytesBuffer, 0, bytesRead);
            }
            byte[] hashedBytes = digest.digest();
            return convertByteArrayToHexString(hashedBytes);
        } catch (NoSuchAlgorithmException | IOException ex) {
            return "null";
        }
    }
    private static String convertByteArrayToHexString(byte[] arrayBytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < arrayBytes.length; i++) {
            stringBuffer.append(Integer.toString((arrayBytes[i] & SECOND) + THIRD, FOURTH)
                    .substring(1));
        }
        return stringBuffer.toString();
    }
}
