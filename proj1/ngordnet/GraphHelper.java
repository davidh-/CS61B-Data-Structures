package ngordnet;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedDFS;
import java.util.TreeSet;
import java.util.Set;

/** DO NOT MODIFY THIS FILE.
 *  @author Josh Hug
 */

public class GraphHelper {
    /** Returns the set of all vertex numbers reachable from the start vertices. */
    public static Set<Integer> descendants(Digraph G, Set<Integer> synsetIDs) {
        DirectedDFS dfdp = new DirectedDFS(G, synsetIDs);
        TreeSet<Integer> reachable = new TreeSet<Integer>();

        for (int i = 1; i < G.V(); i += 1) {
            if (dfdp.marked(i)) {
                reachable.add(i);
            }
        }

        return reachable;
    }

} 