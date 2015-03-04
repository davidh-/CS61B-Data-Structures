package ngordnet;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.In;

/** Provides a simple user interface for exploring WordNet and NGram data.
 *  @author David Dominguez Hooper
 */
public class NgordnetUI {
    public static void main(String[] args) {
        In in = new In("./ngordnet/ngordnetui.config");
        System.out.println("Reading ngordnetui.config...");

        String wordFile = in.readString();
        String countFile = in.readString();
        String synsetFile = in.readString();
        String hyponymFile = in.readString();
        System.out.println("\nBased on ngordnetui.config, using the following: "
                           + wordFile + ", " + countFile + ", " + synsetFile +
                           ", and " + hyponymFile + ".");

        System.out.println("\nFor tips on implementing NgordnetUI, see ExampleUI.java.");

    }
} 

