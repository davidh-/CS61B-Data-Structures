import edu.princeton.cs.introcs.In;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.io.IOException;
import java.io.FileWriter;
import java.io.*;

/** 
 *  @author David Dominguez Hooper
 */

public class Gitlet {
    private static final String GITLET_DIR = ".gitlet/";
    private static HashMap<String, History> trackedFiles;
    private static HashSet<String> addedFiles;
    private static TreeMap<Integer, Commit> commits;
    private static Commit lastCommit;

	public static <K> void writeObject(File fileName, K object) {
		try {
			FileOutputStream fileOut = new FileOutputStream(fileName);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
	        out.writeObject(object);
	        out.close();
	        fileOut.close();
		} catch(IOException i) {
			i.printStackTrace();
		}
	}

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
	public static void main(String[] args) {
		trackedFiles = new HashMap<String, History>();
		addedFiles = new HashSet<String>();
		commits = new TreeMap<Integer, Commit>();

        String command = args[0];
        switch (command) {
            case "init":
            	File dir = new File(GITLET_DIR);
		        if (dir.exists()) {
		            System.out.println("A gitlet version control system already exists in the current directory.");
		        } else {
		        	dir.mkdirs();
		        }
	        	Commit firstCommit = new Commit();
	        	System.out.println(firstCommit);
	        	commits.put(0, firstCommit);
	        	lastCommit = firstCommit;
            	File lastCommitFileee = new File(GITLET_DIR + "lastCommit.ser");
            	lastCommit.writeObject(lastCommitFileee);
                break;
            case "add":
            	String fileName = args[1];
            	File fileToAdd = new File(fileName);
            	if (!fileToAdd.exists()) {
            		System.out.println("File does not exist.");
            		return;
            	} else if(trackedFiles.containsKey(fileName) && trackedFiles.get(fileName).lastModified() != fileToAdd.lastModified()) {
            		System.out.println("File has not been modified since the last commit.");
            		return;
            	} else {
            		addedFiles.add(fileName);
            	}
            	writeObject(new File(GITLET_DIR + "addedFiles.ser"), addedFiles);
                break;  
            case "commit":
            	String commitMessage = args[1];
            	File lastCommitFilee = new File(GITLET_DIR + "lastCommit.ser");
            	lastCommit = readObject(lastCommitFilee);
            	System.out.println(commitMessage + " " + lastCommit + " " + 1);
            	Commit newCommit = new Commit(commitMessage, lastCommit, lastCommit.getId() + 1);
            	addedFiles = readObject(new File(GITLET_DIR + "addedFiles.ser"));
 				for (String curFile : addedFiles) {
 					File direct = new File(GITLET_DIR + "/" + curFile);
 					if (!direct.exists()) {
 						direct.mkdirs();
 					}
 					File cur = new File(curFile);
 					Long lastMod = cur.lastModified();

 					if (trackedFiles.containsKey(curFile)) {
 						History curHistory = trackedFiles.get(curFile);
 						curHistory.addChange(lastMod);
 					} else {
 						History newHistory = new History(curFile, lastMod);
 						trackedFiles.put(curFile, newHistory);
 					}
 					newCommit.addFile(curFile, lastMod);
 					createFile(GITLET_DIR + curFile + "/" + Long.toString(lastMod) + curFile, getText(curFile));
 				}
 				commits.put(lastCommit.getId(), newCommit);
 				lastCommit = newCommit;
 				File fileF = new File(GITLET_DIR + "lastCommit.ser");
 				lastCommit.writeObject(fileF);
 				addedFiles.clear();
                break;
            case "remove":

                break; 
            case "log": 

                break;  
            case "global log": 

                break;
            case "find":

                break;
            case "status":

                break;
            case "checkout":
            	String checkoutFileName = args[1];
            	File curFile = new File(checkoutFileName);
            	File lastCommitFile = new File(GITLET_DIR + "lastCommit.ser");
            	lastCommit = readObject(lastCommitFile);
            	Long fileIDFromLastCommit = lastCommit.getFileLastModified(checkoutFileName);
            	System.out.println(GITLET_DIR + checkoutFileName + "/" + Long.toString(fileIDFromLastCommit) + checkoutFileName);
            	writeFile(checkoutFileName, getText(GITLET_DIR + checkoutFileName + "/" + Long.toString(fileIDFromLastCommit) + checkoutFileName));
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
      } catch(IOException i)
      {
         i.printStackTrace();
         return null;
      }catch(ClassNotFoundException c)
      {
         System.out.println("Employee class not found");
         c.printStackTrace();
         return null;
      }
	}
}
