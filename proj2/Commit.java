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
	private HashMap<String, Long> newCommitedFiles;
	private HashMap<String, Long> oldCommitedFiles;
	private int id;
	private String timeStamp;

	private Commit oldCommit;

	public Commit() {
		message = "initial commit";
		newCommitedFiles = new HashMap<String, Long>();
		oldCommitedFiles = new HashMap<String, Long>();
		id = 0;
		timeStamp = createTimeStamp();
		oldCommit = null;
	}
	public Commit(String message, Commit oldCommit, int id){
		this.message = message;
		newCommitedFiles = new HashMap<String, Long>();
		oldCommitedFiles = oldCommit.getAllCommitedFiles();
		this.id = id;
		this.timeStamp = createTimeStamp();
		this.oldCommit = oldCommit;
	}
	public boolean containsFile(String word) {
		return getAllCommitedFiles().containsKey(word);
	}
	public HashMap<String, Long> getAllCommitedFiles() {
		HashMap<String, Long> allCommitedFiles = new HashMap<String, Long>(this.oldCommitedFiles);
		allCommitedFiles.putAll(this.newCommitedFiles);
		return allCommitedFiles;
	}
	public void removeFileFromInheritedCommits(String word) {
		oldCommitedFiles.remove(word);
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
		newCommitedFiles.put(fileName, lastModified);
	}
	public int getId() {
		return id;
	}
	public Long getFileLastModified(String fileName) {
		HashMap<String, Long> allCommitedFiles = getAllCommitedFiles();
		if (allCommitedFiles.containsKey(fileName)) {
			return allCommitedFiles.get(fileName);
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
