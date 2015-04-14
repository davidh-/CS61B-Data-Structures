import java.util.HashMap;
import java.sql.Timestamp;
import java.util.Date;
import java.io.IOException;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.File;
/** 
 *  @author David Dominguez Hooper
 */

public class Commit implements Serializable{
	private String message;
	private HashMap<String, String> newCommittedFiles;
	private HashMap<String, String> oldCommittedFiles;
	private int id;
	private String timeStamp;

	private Commit oldCommit;

	public Commit() {
		message = "initial commit";
		newCommittedFiles = new HashMap<String, String>();
		oldCommittedFiles = new HashMap<String, String>();
		id = 0;
		timeStamp = createTimeStamp();
		oldCommit = null;
	}
	public Commit(String message, Commit oldCommit, int id){
		this.message = message;
		newCommittedFiles = new HashMap<String, String>();
		oldCommittedFiles = oldCommit.getAllCommittedFiles();
		this.id = id;
		this.timeStamp = createTimeStamp();
		this.oldCommit = oldCommit;
	}
	public Commit getOldCommit() {
		return oldCommit;
	}
	public boolean containsFile(String word) {
		return getAllCommittedFiles().containsKey(word);
	}
	public HashMap<String, String> getNewCommittedFiles() {
		return newCommittedFiles;
	}
	public HashMap<String, String> getAllCommittedFiles() {
		HashMap<String, String> allCommittedFiles = new HashMap<String, String>(this.oldCommittedFiles);
		allCommittedFiles.putAll(this.newCommittedFiles);
		return allCommittedFiles;
	}
	public void removeFileFromInheritedCommits(String word) {
		oldCommittedFiles.remove(word);
	}
	private String createTimeStamp() {
        Date date = new Date();
    	Timestamp timeStamp = new Timestamp(date.getTime());
    	return timeStamp.toString();
	}
	public String getMessage() {
		return message;
	}
	public void addFile(String fileName, String lastModified) {
		newCommittedFiles.put(fileName, lastModified);
	}
	public int getId() {
		return id;
	}
	public String getFileHash(String fileName) {
		HashMap<String, String> allCommittedFiles = getAllCommittedFiles();
		if (allCommittedFiles.containsKey(fileName)) {
			return allCommittedFiles.get(fileName);
		} else {
			return null;
		}
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public String getCommitHistory() {
		String commitHistory = "";
		Commit pointer = this;
		boolean needsEmptyLine = false;
		while (pointer != null) {
			if (!needsEmptyLine) {
				needsEmptyLine = true;
			}
			else {
				commitHistory += "\n\n";
			}
			commitHistory += "====\n" + "Commit " + pointer.getId() + ".\n" + pointer.getTimeStamp() + "\n" + pointer.getMessage();
			pointer = pointer.oldCommit;
		}
		return commitHistory;
	}
	public String getLog() {
		return "====\n" + "Commit " + getId() + ".\n" + getTimeStamp() + "\n" + getMessage();
	}
	public void writeObject(File fileName) {
		try {
			FileOutputStream fileOut = new FileOutputStream(fileName);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
	        out.writeObject(this);
	        out.close();
	        fileOut.close();
		} catch(IOException i) {
			i.printStackTrace();
		}
	}
}
