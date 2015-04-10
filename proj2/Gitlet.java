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
    private static HashSet<String> addedFiles;
    private static HashSet<String> filesToRemove;

    private static HashMap<Integer, Commit> commits;
    // private static Commit lastCommit;

    private static HashMap<String, Branch> branches;
    private static Branch currentBranch;


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
        }
        Commit firstCommit = new Commit();
        commits.put(firstCommit.getId(), firstCommit);

        Branch master = new Branch();
        branches.put(master.getBranchName(), master);
        currentBranch = master;
        currentBranch.writeObject(lastCurrentBranch);

        writeObject(lastAddedFiles, addedFiles);
        writeObject(lastFilesToRemove, filesToRemove);
        writeObject(lastCommits, commits);
        writeObject(lastBranches, branches);
    }
    private void add(String[] args) {
        commits = readObject(lastCommits);
        currentBranch = readObject(lastCurrentBranch);
        Commit lastCommit = commits.get(currentBranch.getLastCommit());
        filesToRemove = readObject(lastFilesToRemove);
        addedFiles = readObject(lastAddedFiles);
        String fileName = args[1];
        File fileToAdd = new File(fileName);
        if (filesToRemove.contains(fileName)) {
            filesToRemove.remove(fileName);
            writeObject(lastFilesToRemove, filesToRemove);
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
            writeObject(lastAddedFiles, addedFiles);
        }
    }
    private void commit(String[] args) {
        addedFiles = readObject(lastAddedFiles);
        filesToRemove = readObject(lastFilesToRemove);
        commits = readObject(lastCommits);
        currentBranch = readObject(lastCurrentBranch);
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
        writeObject(lastFilesToRemove, filesToRemove);

        for (String curFile : addedFiles) {
            File direct = new File(GITLET_DIR + "/" + curFile);
            if (!direct.exists()) {
                direct.mkdirs();
            }
            File cur = new File(curFile);
            Long lastMod = cur.lastModified();
            newCommit.addFile(curFile, lastMod);
            File f = new File(curFile);
            System.out.println(GITLET_DIR + curFile + "/" 
                                + Long.toString(lastMod) + f.getName());
            createFile(GITLET_DIR + curFile + "/" 
                                + Long.toString(lastMod) + f.getName(), getText(curFile));
        }
        commits.put(newCommit.getId(), newCommit);
        writeObject(lastCommits, commits);

        currentBranch.updateLastCommit(newCommit.getId());
        currentBranch.writeObject(lastCurrentBranch);
        writeObject(lastBranches, branches);

        addedFiles.clear();
        writeObject(lastAddedFiles, addedFiles);
    }
    private void remove(String[] args) {
        commits = readObject(lastCommits);
        currentBranch = readObject(lastCurrentBranch);

        Commit lastCommit = commits.get(currentBranch.getLastCommit());
        addedFiles = readObject(lastAddedFiles);
        filesToRemove = readObject(lastFilesToRemove);

        String removeMessage = args[1];
        // !addedFiles.contains(removeMessage)|| 
        if (!lastCommit.getAllCommitedFiles().containsKey(removeMessage)) {
            System.out.println("No reason to remove the file.");
            return;
        } else {
            if (addedFiles.contains(removeMessage)) {
                addedFiles.remove(removeMessage);
                writeObject(lastAddedFiles, addedFiles);
            } else {
                filesToRemove.add(removeMessage);
                writeObject(lastFilesToRemove, filesToRemove);
            }
        }
    }
    private void log() {
        commits = readObject(lastCommits);
        currentBranch = readObject(lastCurrentBranch);

        Commit lastCommit = commits.get(currentBranch.getLastCommit());
        System.out.println(lastCommit.getCommitHistory());
    }
    private void globalLog() {
        commits = readObject(lastCommits);
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
        commits = readObject(lastCommits);
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
        
    }
    private void checkout(String[] args) {
        commits = readObject(lastCommits);
        currentBranch = readObject(lastCurrentBranch);

        Commit lastCommit = commits.get(currentBranch.getLastCommit());

        String checkoutFileName = args[1];
        File curFile = new File(checkoutFileName);
        Long fileIDFromLastCommit = lastCommit.getFileLastModified(checkoutFileName);
        System.out.println(GITLET_DIR + checkoutFileName + "/" 
                            + fileIDFromLastCommit + curFile.getName());
        writeFile(checkoutFileName, getText(GITLET_DIR + checkoutFileName + "/" 
                            + Long.toString(fileIDFromLastCommit) + curFile.getName()));
    }
    private void branch() {
        
    }
    private void removeBranch() {
        
    }
    private void reset() {
        
    }
    private void merge() {
        
    }
    private void rebase() {
        
    }
    private void interactiveRebase() {
        
    }
    public static void main(String[] args) {
        addedFiles = new HashSet<String>();
        filesToRemove = new HashSet<String>();
        commits = new HashMap<Integer, Commit>();
        branches = new HashMap<String, Branch>();
        Gitlet gitlet = new Gitlet();

        String command = args[0];
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
                gitlet.branch();
                break;
            case "rm-branch":
                gitlet.removeBranch();
                break;
            case "reset":
                gitlet.reset();
                break;
            case "merge":
                gitlet.merge();
                break;
            case "rebase":
                gitlet.rebase();
                break;
            case "i-rebase":
                gitlet.interactiveRebase();
                break;
            default:
                System.out.println("Invalid command.");  
                break;
        }
    }
    private static <K> K readObject(File f) {
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
    public static <K> void writeObject(File fileName, K object) {
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
    private static void createFile(String fileName, String fileText) {
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
    private static void writeFile(String fileName, String fileText) {
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
