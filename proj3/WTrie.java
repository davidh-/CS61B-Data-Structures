import java.util.HashMap;
/**
 * Prefix-Trie. Supports linear time find() and insert(). 
 * Should support determining whether a word is a full word in the 
 * Trie or a prefix.
 * @author David Dominguez Hooper
 */

public class WTrie {

    Node root = new Node();
    /** 
     * Node class
     */
    public class Node implements Comparable<Node> {
        Double val;
        Double max;
        String current;
        HashMap<Character, Node> links;
        /** 
         * Default Constructor for Node class
         */
        public Node() {
            val = null;
            max = 0.0;
            current = null;
            links = new HashMap<Character, Node>();
        }
        /** 
         * three argument Constructor for Node class
         * @param weight is the max weight of the node
         * @param current the current string of the node
         */
        public Node(double weight, String current) {
            this();
            this.max = weight;
            this.current = current;
        }
        /** 
         * compareTo compares to Nodes
         * @param oNode is the other Node
         * @return the comparison to another Node
         */
        public int compareTo(Node oNode) {
            return this.max.compareTo(oNode.max);
        }
    }

    /** 
     * @return whether or not it found the string in the trie
     * @param isFullWord determines if it is a full word or not
     * @param s is the string that needs to be found
     * find
     */
    public boolean find(String s, boolean isFullWord) {
        Node x = get(root, s, 0, "", isFullWord);
        if (x == null) {
            return false;
        }
        return true; 
    }
    /** 
     * @return whether or not it found the string in the trie
     * @param isFullWord determines if it is a full word or not
     * @param s is the string that needs to be found
     * find
     */
    public Double findWeight(String s, boolean isFullWord) {
        Node x = get(root, s, 0, "", isFullWord);
        if (x == null) {
            return null;
        }
        return x.val; 
    }
    /** 
     * @param x is the current Node
     * @param s is the String to insert
     * @param d is the current int 
     * @param current is the current state of the built up word
     * @param isFullWord determines if it is a full word or not 
     * @return is the entire Node
     */
    public Node get(Node x, String s, int d, String current, boolean isFullWord) {
        if (x == null) {
            return null;
        }
        if (!isFullWord && s.equals(current)) {
            return x;
        }
        if (d == s.length()) {
            if (x.val != null) {
                return x;
            } else {
                return null;
            }
        }
        char c = s.charAt(d);
        if (isFullWord) {
            return get(x.links.get(c), s, d + 1, 
                    current, isFullWord);
        } else {
            return get(x.links.get(c), s, d + 1, 
                    current + c, isFullWord);
        }
    }
    /** 
     * @param s is the string that needs to be inserted
     * @param weight is the weight of the string
     */
    public void insert(String s, Double weight) {
        insert(root, s, "", 0, weight);
    }
    /** 
     * @param x is the current Node
     * @param s is the String to insert
     * @param current is the current string
     * @param d is the current int 
     * @param weight is weight
     * @return is the entire Node
     */
    private Node insert(Node x, String s, String current, int d, Double weight) {
        if (s.equals("") || s == null) {
            throw new IllegalArgumentException();
        }
        if (x == null) {
            x = new Node(weight, current);
        } else {
            if (x.max.compareTo(weight) < 0) {
                x.max = weight;
            }
        }
        if (d == s.length()) {
            x.val = weight;
            return x;
        } 
        char c = s.charAt(d);
        x.links.put(c, 
            insert(x.links.get(c), s, current + c, d + 1, weight));
        return x;
    }
    /**
     * main method
     * @param args takes the name of an input file and an integer k as command-line arguments
     */
    public static void main(String[] args) {
        Trie t = new Trie();
        t.insert("hello");
        t.insert("hey");
        t.insert("goodbye");
        System.out.println(t.find("hell", false)); //true
        System.out.println(t.find("hello", true)); //true
        System.out.println(t.find("good", false)); //true
        System.out.println(t.find("bye", false)); //false
        System.out.println(t.find("heyy", false)); //false
        System.out.println(t.find("hell", true)); //fasle  
    }
}
