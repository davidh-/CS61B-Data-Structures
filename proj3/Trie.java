/**
 * Prefix-Trie. Supports linear time find() and insert(). 
 * Should support determining whether a word is a full word in the 
 * Trie or a prefix.
 * @author David Dominguez Hooper
 */

public class Trie {

    private Node root = new Node();

    /** 
     * Node class
     */
    private class Node {
        boolean exists;
    }

    /** 
     * @return boolean
     * @param isFullWord is
     * @param s is
     * find
     */
    public boolean find(String s, boolean isFullWord) {
        return false;
    }
    /** 
     * @param s
     * find
     */
    public void insert(String s) {
        if (s.equals("") || s == null) {
            throw new IllegalArgumentException();
        }
    }
    /** 
     * main method
     * @param args is
     */
    public static void main(String[] args) {
        Trie t = new Trie();
        t.insert("hello");
        t.insert("hey");
        t.insert("goodbye");
        System.out.println(t.find("hell", false));
        System.out.println(t.find("hello", true));
        System.out.println(t.find("good", false));
        System.out.println(t.find("bye", false));
        System.out.println(t.find("heyy", false));
        System.out.println(t.find("hell", true));   
    }
}
