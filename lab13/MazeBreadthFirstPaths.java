import java.util.Observable;
/** 
 *  @author Josh Hug
 *  @2_author David Dominguez Hooper
 *  Sources:
 *  (1) http://algs4.cs.princeton.edu/41undirected/BreadthFirstPaths.java.html
 */

public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields: 
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze; 

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;  

    }

    /** Conducts a breadth first search of the maze starting at vertex x. */
    private void bfs(int s) {
        Queue<Integer> queue = new Queue<Integer>();
        distTo[s] = 0;
        marked[s] = true;
        queue.enqueue(s);
        announce();

        while (!queue.isEmpty()) {
            int v = queue.dequeue();
            for (int w : maze.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    announce();
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    announce();
                    queue.enqueue(w);
                }
                if (w == t) {
                    return;
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs(s);
    }
} 

