import java.io.File;
import java.util.HashSet;
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

/** 
 *  @author David Dominguez Hooper
 */

public class Gitlet {
    private static final String GITLET_DIR = ".gitlet/";
    private HashSet<String> addedFiles;
    private HashSet<String> filesToRemove;

    private HashMap<Integer, Commit> commits;
    private HashMap<String, Branch> branches;
    private Branch currentBranch;


    private File lastAddedFiles = new File(GITLET_DIR + "addedFiles.ser");
    private File lastFilesToRemove = new File(GITLET_DIR + "filesToRemove.ser");
    private File lastCommits = new File(GITLET_DIR + "commits.ser");
    private File lastBranches = new File(GITLET_DIR + "branches.ser");
    private File lastCurrentBranch = new File(GITLET_DIR + "currentBranch.ser");

    private void init() {
        File dir = new File(GITLET_DIR);
        if (dir.exists()) {
            String firstHalf = "A gitlet version control system already ";
            String secondHalf = "exists in the current directory.";
            System.out.println(firstHalf + secondHalf);
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
            currentBranch = master;
        }
    }
    private void add(String[] args) {
        Commit lastCommit = commits.get(currentBranch.getLastCommit());
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
            System.out.println("test");
            addedFiles.add(fileName);
        }
    }
    private void commit(String[] args) {
        Commit lastCommit = commits.get(currentBranch.getLastCommit());
        if (addedFiles.isEmpty() && filesToRemove.isEmpty()) {
            System.out.println("No changes added to the commit.");
            return;
        } else if (args.length < 2) {
            System.out.println("Please enter a commit message.");
            return;
        }
        String commitMessage = args[1];
        Commit newCommit = new Commit(commitMessage, lastCommit, lastCommit.getId() + 1);
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
            // System.out.println(GITLET_DIR + curFile + "/" 
            //                     + Long.toString(lastMod) + f.getName());
            createFile(GITLET_DIR + curFile + "/" 
                                + Long.toString(lastMod) + f.getName(), getText(curFile));
        }
        commits.put(newCommit.getId(), newCommit);
        currentBranch.updateLastCommit(newCommit.getId());
        addedFiles.clear();
    }
    private void remove(String[] args) {
        Commit lastCommit = commits.get(currentBranch.getLastCommit());
        String removeMessage = args[1];
        if (!lastCommit.getAllCommitedFiles().containsKey(removeMessage)) {
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
        Commit lastCommit = commits.get(currentBranch.getLastCommit());
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
            if (branch.equals(currentBranch.getBranchName())) {
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
        Commit lastCommit = commits.get(currentBranch.getLastCommit());
        if (args.length == 2) {
            if (branches.containsKey(args[1])) {
                if (currentBranch.getBranchName().equals(args[1])) {
                    System.out.println("No need to checkout the current branch.");
                } else {
                    currentBranch = branches.get(args[1]);
                    lastCommit = commits.get(currentBranch.getLastCommit());
                    for (String file : lastCommit.getAllCommitedFiles().keySet()) {
                        restoreFile(file, lastCommit);
                    }
                }
            } else if (lastCommit.containsFile(args[1])) {
                restoreFile(args[1], lastCommit);
            } else {
                String first = "File does not exist in the most recent commit, ";
                String second = "or no such branch exists.";
                System.out.println(first + second);
            }
        } else if (args.length == 3) {
            int commitId = Integer.parseInt(args[1]);
            String fileName = args[2];
            if (!commits.containsKey(commitId)) {
                System.out.println("No commit with that id exists.");
            } else {
                Commit curCommit = commits.get(commitId);
                HashMap<String, Long> allCommitedFiles = curCommit.getAllCommitedFiles();
                if (!allCommitedFiles.containsKey(fileName)) {
                    System.out.println("File does not exist in that commit.");
                } else  {
                    restoreFile(fileName, curCommit);
                }
            } 
        } 
        // System.out.println(GITLET_DIR + checkoutFileName + "/" 
        //                     + fileIDFromLastCommit + curFile.getName());
    }
    private void branch(String[] args) {
        String branchName = args[1];
        if (branches.containsKey(branchName)) {
            System.out.println("A branch with that name already exists.");
        } else {
            Branch newBranch = new Branch(branchName, currentBranch.getLastCommit());
            branches.put(branchName, newBranch);
        }
    }
    private void removeBranch(String[] args) {
        String branchName = args[1];
        if (!branches.containsKey(branchName)) {
            System.out.println("A branch with that name does not exist.");
        } else if (currentBranch.getBranchName().equals(branchName)) {
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
            for (String file : lastCommit.getAllCommitedFiles().keySet()) {
                restoreFile(file, lastCommit);
            }
            currentBranch.updateLastCommit(commitId);
        }
    }
    private void merge(String[] args) {
        String branchName = args[1];
        if (!branches.containsKey(branchName)) {
            System.out.println("A branch with that name does not exist.");
        } else if (currentBranch.getBranchName().equals(branchName)) {
            System.out.println("Cannot merge a branch with itself.");
        } else  {

        }
    }
    private void rebase(String[] args) {
        String branchName = args[1];
        if (!branches.containsKey(branchName)) {
            System.out.println("A branch with that name does not exist.");
        } else if (currentBranch.getBranchName().equals(branchName)) {
            System.out.println("Cannot merge a branch with itself.");
        } else if (1==1) {
            //fixx meeee
        }
    }
    private void interactiveRebase(String[] args) {
        
    }
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
                gitlet.checkout(args);
                break;
            case "branch":
                gitlet.branch(args);
                break;
            case "rm-branch":
                gitlet.removeBranch(args);
                break;
            case "reset":
                gitlet.reset(args);
                break;
            case "merge":
                gitlet.merge(args);
                break;
            case "rebase":
                gitlet.rebase(args);
                break;
            case "i-rebase":
                gitlet.interactiveRebase(args);
                break;
            default:
                System.out.println("Invalid command.");  
                break;
        }
        gitlet.serialize();
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
        currentBranch.writeObject(lastCurrentBranch);
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
        currentBranch = readObject(lastCurrentBranch); 
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
