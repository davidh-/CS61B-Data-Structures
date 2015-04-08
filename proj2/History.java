import java.util.LinkedList;
/** 
 *  @author David Dominguez Hooper
 */

public class History {
	private String fileName;
	private LinkedList<Long> allHistory;
	private long lastModified;

	public History(String fileName, Long lastModified) {
		this.fileName = fileName;
		allHistory = new LinkedList<Long>();
		allHistory.add(lastModified);
		this.lastModified = lastModified;
	}
	public void addChange(Long lastModified) {
		allHistory.add(lastModified);
		this.lastModified = lastModified;
	}
	public long lastModified() {
		return lastModified;
	}
	
}