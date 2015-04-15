import java.io.Serializable;

/** 
 *  @author David Dominguez Hooper
 */

public class Branch implements Serializable {
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
    public String getBranchName() {
        return branchName;
    }
    public int getLastCommit() {
        return lastCommit;
    }
    public void updateLastCommit(int lastCommitID) {
        this.lastCommit = lastCommitID;
    }
}
