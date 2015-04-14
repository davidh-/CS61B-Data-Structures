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
import java.util.Arrays;

/** 
 *  @author David Dominguez Hooper
 */

public class Gitlet {
    private static final String GITLET_DIR = ".gitlet/";
    private static final HashSet<String> COMMANDS = 
        new HashSet<String>(Arrays.asList(new String[] 
            {"add", "commit", "rm", "log", "global-log", "find", "status", 
                "checkout", "branch", "rm-branch", "reset", "merge", "rebase", "i-rebase"}));
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
                    && lastCommit.getFileLastModified(fileName) == fileToAdd.lastModified()) {
            System.out.println("File has not been modified since the last commit.");
            return;
        } else {
            addedFiles.add(fileName);
        }
    }
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
            Long lastMod = cur.lastModified();
            newCommit.addFile(curFile, lastMod);
            File f = new File(curFile);
            createFile(GITLET_DIR + curFile + "/" 
                                + Long.toString(lastMod) + f.getName(), getText(curFile));
        }
        commits.put(newCommit.getId(), newCommit);
        currentBranch.updateLastCommit(newCommit.getId());
        addedFiles.clear();
    }
    private void remove(String[] args) {
        Commit lastCommit = commits.get(branches.get(currentBranchName).getLastCommit());
        String removeMessage = args[1];
        if (!lastCommit.getAllCommittedFiles().containsKey(removeMessage)) {
            System.out.println("No reason to remove the file.");
            return;
        } else {
            if (addedFiles.contains(removeMessage)) {
                addedFiles.remove(removeMessage);
            } else {
                filesToRemove.add(removeMessage);
            }
        }
    }
    private void log() {
        Commit lastCommit = commits.get(branches.get(currentBranchName).getLastCommit());
        System.out.println(lastCommit.getCommitHistory());
    }
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
                HashMap<String, Long> allCommittedFiles = curCommit.getAllCommittedFiles();
                if (!allCommittedFiles.containsKey(fileName)) {
                    System.out.println("File does not exist in that commit.");
                } else  {
                    restoreFile(fileName, curCommit);
                }
            } 
        }
    }
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
    private void merge(String[] args) {
        Branch currentBranch = branches.get(currentBranchName);
        String branchName = args[1];
        if (!branches.containsKey(branchName)) {
            System.out.println("A branch with that name does not exist.");
        } else if (currentBranchName.equals(branchName)) {
            System.out.println("Cannot merge a branch with itself.");
        } else {
            Branch givenBranch = branches.get(branchName);
            Commit gBranchCommit = commits.get(givenBranch.getLastCommit());
            int splitPointCommit = findSplitPoint(gBranchCommit);
            for (String file : gBranchCommit.getAllCommittedFiles().keySet()) {
                if (commits.get(currentBranch.getLastCommit()).containsFile(file)) {
                    Long splitLastModified = 
                        commits.get(splitPointCommit).getFileLastModified(file);
                    Long givenLastModified = 
                        gBranchCommit.getFileLastModified(file);
                    Long currentLastModified = 
                        commits.get(currentBranch.getLastCommit()).getFileLastModified(file);
                    if (givenLastModified != splitLastModified 
                                && currentLastModified == splitLastModified) {
                        restoreFile(file, gBranchCommit);
                    } else if (givenLastModified != splitLastModified 
                                && currentLastModified != splitLastModified) {
                        File curFile = new File(file);
                        createFile(file + ".conflicted", getText(GITLET_DIR + file + "/" 
                                    + Long.toString(givenLastModified) + curFile.getName()));
                    } 
                }
            }
        }
    }
    private void rebase(String[] args) {
        interactiveRebase(args, false);
    }
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
            int splitPointCommit = -1;
            while (pointer != null) {
                if (commitsOfGBranch.contains(pointer.getId())) {
                    splitPointCommit = pointer.getId();
                    break;
                } else  {
                    pointer = pointer.getOldCommit();
                }
            }
            commitsOfGBranch = new TreeSet<Integer>(commitsOfGBranch.tailSet(splitPointCommit));
            int[] firstLast = calcFirstLastCommit(commitsOfGBranch);

            boolean splitPoint = true;
            for (int commitID : commitsOfGBranch) {
                if (splitPoint) {
                    splitPoint = false;
                } else  {
                    Commit curCommit = commits.get(commitID);
                    String newMessage = curCommit.getMessage();
                    if (interactive) {
                        String decision = 
                            iRebaseUserInput(curCommit, commitID, firstLast[0], firstLast[1]);
                        if (decision.equals("s")) {
                            break;
                        } else if ((decision.equals("m"))) {
                            System.out.println("Please enter a new message for this commit.");
                            Scanner in = new Scanner(System.in);
                            newMessage = in.nextLine();
                        }
                    }
                    Commit lastNewCommit = commits.get(currentBranch.getLastCommit());
                    HashMap<String, Long> newFiles = curCommit.getNewCommittedFiles();
                    Commit newCommit = new Commit(newMessage, lastNewCommit, commits.size());
                    for (String curFile : newFiles.keySet()) {
                        if (!lastCommit.containsFile(curFile)) {
                            newCommit.addFile(curFile, newFiles.get(curFile));
                        }
                    }
                    commits.put(newCommit.getId(), newCommit);
                    currentBranch.updateLastCommit(newCommit.getId());
                }
            }
            Commit lastTrueCommit = commits.get(currentBranch.getLastCommit());
            for (String file : lastTrueCommit.getAllCommittedFiles().keySet()) {
                restoreFile(file, commits.get(currentBranch.getLastCommit()));
            }
        }
    }
    public static void main(String[] args) {
        Gitlet gitlet = new Gitlet();
        String command = args[0];
        if (COMMANDS.contains(command)) {
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
                gitlet.checkout(args);
                break;
            case "branch":
                gitlet.branch(args);
                break;
            case "rm-branch":
                gitlet.removeBranch(args);
                break;
            case "reset":
                gitlet.dangerous();
                gitlet.reset(args);
                break;
            case "merge":
                gitlet.dangerous();
                gitlet.merge(args);
                break;
            case "rebase":
                gitlet.dangerous();
                gitlet.rebase(args);
                break;
            case "i-rebase":
                gitlet.dangerous();
                gitlet.interactiveRebase(args, true);
                break;
            default:
                System.out.println("Invalid command.");  
                break;
        }
        gitlet.serialize();
    }
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

    private void dangerous() {
        System.out.println("Warning: ");
        System.out.print("The command you entered may alter the files in your working directory. ");
        System.out.print("Uncommitted changes may be lost. ");
        System.out.print("Are you sure you want to continue? (yes/no)");
        Scanner in = new Scanner(System.in);
        String answer = in.nextLine();
        if (answer.equals("no")) {
            System.exit(0);
        }
    }
    private void restoreFile(String fileName, Commit curCommit) {
        File curFile = new File(fileName);
        Long fileIDFromLastCommit = curCommit.getFileLastModified(fileName);
        if (curFile.exists()) {
            writeFile(fileName, getText(GITLET_DIR + fileName + "/" 
                        + Long.toString(fileIDFromLastCommit) + curFile.getName()));
        } else {
            createFile(fileName, getText(GITLET_DIR + fileName + "/" 
                        + Long.toString(fileIDFromLastCommit) + curFile.getName()));
        }
        curFile.setLastModified(fileIDFromLastCommit);
    }

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
    private void serialize() {
        writeObject(lastCurrentBranchName, currentBranchName);
        writeObject(lastAddedFiles, addedFiles);
        writeObject(lastFilesToRemove, filesToRemove);
        writeObject(lastCommits, commits);
        writeObject(lastBranches, branches);  
    }
    private void deserialize() {
        addedFiles = readObject(lastAddedFiles);
        filesToRemove = readObject(lastFilesToRemove);
        commits = readObject(lastCommits);
        branches = readObject(lastBranches);
        currentBranchName = readObject(lastCurrentBranchName); 
    }

/**
 * A couple of utility
 * methods.
 * 
 * @author Joseph Moghadam :
 *
 * 
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
}
