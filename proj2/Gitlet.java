import edu.princeton.cs.introcs.In;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

/** 
 *  @author David Dominguez Hooper
 */

public class Gitlet {
    private static final String GITLET_DIR = ".gitlet/";
    private static HashMap<String, History> trackedFiles;
    private static HashSet<String> addedFiles;
    private static TreeMap<Integer, Commit> commits;
    private static Commit lastCommit;
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
		            return;
		        } else {
		        	dir.mkdirs();
		        	Commit firstCommit = new Commit();
		        	commits.put(0, firstCommit);
		        	lastCommit = firstCommit;
		        }
                return;
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
                break;  
            case "commit":
            	String commitMessage = args[1];
            	Commit newCommit = new Commit(commitMessage, lastCommit, lastCommit.getId() + 1);
 				for (String curFile : addedFiles) {
 					File direct = new File(curFile);
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
 				}
 				commits.put(lastCommit.getId(), newCommit);
 				lastCommit = newCommit;
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
}
