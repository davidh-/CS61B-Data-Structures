import java.util.HashMap;
/** 
 *  @author David Dominguez Hooper
 */

public class Commit {
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
}