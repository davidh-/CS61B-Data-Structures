import java.io.File;
import java.util.HashSet;
import java.util.TreeMap;

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
    private static TreeMap<Integer, Commit> commits;
    private static Commit lastCommit;
    // If static, then you have to remove the static thing and 
    // reference it from an instantiated reference.
    private static void init(File lastCommitFile, 
                                File lastAddedFiles, File lastFilesToRemove, File lastCommits) {
        File dir = new File(GITLET_DIR);
        if (dir.exists()) {
            String firstHalf = "A gitlet version control system already ";
            String secondHalf = "exists in the current directory.";
            System.out.println(firstHalf + secondHalf);
        } else {
            dir.mkdirs();
        }
        Commit firstCommit = new Commit();
        // System.out.println(firstCommit);
        commits.put(firstCommit.getId(), firstCommit);
        lastCommit = firstCommit;

        lastCommit.writeObject(lastCommitFile);
        writeObject(lastAddedFiles, addedFiles);
        writeObject(lastFilesToRemove, filesToRemove);
        writeObject(lastCommits, commits);
    }
    private static void add(String[] args, File lastCommitFile, 
                                File lastAddedFiles, File lastFilesToRemove) {
        filesToRemove = readObject(lastFilesToRemove);
        addedFiles = readObject(lastAddedFiles);
        lastCommit = readObject(lastCommitFile);
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
    private static void commit(String[] args, File lastCommitFile, 
                                File lastAddedFiles, File lastFilesToRemove, File lastCommits) {
        addedFiles = readObject(lastAddedFiles);
        filesToRemove = readObject(lastFilesToRemove);
        commits = readObject(lastCommits);
        lastCommit = readObject(lastCommitFile);

        if (addedFiles.isEmpty() && filesToRemove.isEmpty()) {
            System.out.println("No changes added to the commit.");
            return;
        } else if (args.length < 2) {
            System.out.println("Please enter a commit message.");
            return;
        }
        String commitMessage = args[1];
        // System.out.println(commitMessage + " " + lastCommit + " " + 1);
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

        lastCommit = newCommit;
        lastCommit.writeObject(lastCommitFile);

        addedFiles.clear();
        writeObject(lastAddedFiles, addedFiles);
    }
    private static void remove(String[] args, File lastCommitFile, 
                                File lastAddedFiles, File lastFilesToRemove) {
        lastCommit = readObject(lastCommitFile);
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
    private static void log(File lastCommitFile) {
        lastCommit = readObject(lastCommitFile);
        System.out.println(lastCommit.getCommitHistory());
    }
    private static void globalLog(File lastCommits) {
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
    private static void checkout(String[] args, File lastCommitFile) {
        lastCommit = readObject(lastCommitFile);

        String checkoutFileName = args[1];
        File curFile = new File(checkoutFileName);
        Long fileIDFromLastCommit = lastCommit.getFileLastModified(checkoutFileName);
        System.out.println(GITLET_DIR + checkoutFileName + "/" 
                            + fileIDFromLastCommit + curFile.getName());
        writeFile(checkoutFileName, getText(GITLET_DIR + checkoutFileName + "/" 
                            + Long.toString(fileIDFromLastCommit) + curFile.getName()));
    }
    public static void main(String[] args) {
        addedFiles = new HashSet<String>();
        filesToRemove = new HashSet<String>();
        commits = new TreeMap<Integer, Commit>();

        File lastCommitFile = new File(GITLET_DIR + "lastCommit.ser");
        File lastAddedFiles = new File(GITLET_DIR + "addedFiles.ser");
        File lastFilesToRemove = new File(GITLET_DIR + "filesToRemove.ser");
        File lastCommits = new File(GITLET_DIR + "commits.ser");

        String command = args[0];
        switch (command) {
            case "init":
                init(lastCommitFile, lastAddedFiles, lastFilesToRemove, lastCommits);
                break;
            case "add":
                add(args, lastCommitFile, lastAddedFiles, lastFilesToRemove);
                break;  
            case "commit":
                commit(args, lastCommitFile, lastAddedFiles, lastFilesToRemove, lastCommits);
                break;
            case "rm":
                remove(args, lastCommitFile, lastAddedFiles, lastFilesToRemove);
                break; 
            case "log":
                log(lastCommitFile);
                break;  
            case "global-log": 
                globalLog(lastCommits);
                break;
            case "find":

                break;
            case "status":

                break;
            case "checkout":
                checkout(args, lastCommitFile);
                break;
            case "branch":
                break;
            case "remove branch":

                break;
            case "reset":

                break;
            case "merge":

                break;
            case "rebase":

                break;
            case "interactive rebase":

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
