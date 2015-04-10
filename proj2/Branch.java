import java.io.Serializable;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.File;

/** 
 *  @author David Dominguez Hooper
 */

public class Branch implements Serializable{
	private String branchName;
	private int lastCommit;
	public Branch() {
		branchName = "master";
		lastCommit = 0;
	}
	public Branch(String branchName, int lastCommit) {
		this.branchName = branchName;
		this.lastCommit = lastCommit;
	}
	public void updateLastCommit(int lastCommit) {
		this.lastCommit = lastCommit;
	}
	public String getBranchName() {
		return branchName;
	}
	public int getLastCommit() {
		return lastCommit;
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