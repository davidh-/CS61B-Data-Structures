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
	private HashMap<String, Long> newCommittedFiles;
	private HashMap<String, Long> oldCommittedFiles;
	private int id;
	private String timeStamp;

	private Commit oldCommit;

	public Commit() {
		message = "initial commit";
		newCommittedFiles = new HashMap<String, Long>();
		oldCommittedFiles = new HashMap<String, Long>();
		id = 0;
		timeStamp = createTimeStamp();
		oldCommit = null;
	}
	public Commit(String message, Commit oldCommit, int id){
		this.message = message;
		newCommittedFiles = new HashMap<String, Long>();
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
	public HashMap<String, Long> getNewCommittedFiles() {
		return newCommittedFiles;
	}
	public HashMap<String, Long> getAllCommittedFiles() {
		HashMap<String, Long> allCommittedFiles = new HashMap<String, Long>(this.oldCommittedFiles);
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
	public void addFile(String fileName, Long lastModified) {
		newCommittedFiles.put(fileName, lastModified);
	}
	public int getId() {
		return id;
	}
	public Long getFileLastModified(String fileName) {
		HashMap<String, Long> allCommittedFiles = getAllCommittedFiles();
		if (allCommittedFiles.containsKey(fileName)) {
			return allCommittedFiles.get(fileName);
		} else {
			return (long)-1;
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
