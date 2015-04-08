import java.util.HashMap;
import java.io.*;
/** 
 *  @author David Dominguez Hooper
 */

public class Commit implements Serializable{
	private String message;
	private HashMap<String, Long> commitedFiles;
	private int id;

	private Commit oldCommit;

	public Commit() {
		message = "initial commit.";
		commitedFiles = new HashMap<String, Long>();
		id = 0;
	}
	public Commit(String message, Commit oldCommit, int id){
		this.oldCommit = oldCommit;
		this.message = message;
		commitedFiles = new HashMap<String, Long>();
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void addFile(String fileName, Long lastModified) {
		commitedFiles.put(fileName, lastModified);
	}
	public int getId() {
		return id;
	}
	public Long getFileLastModified(String fileName) {
		System.out.println(commitedFiles);
		if (commitedFiles.containsKey(fileName)) {
			return commitedFiles.get(fileName);
		} else {
			return (long)-1;
		}
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