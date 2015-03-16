import java.util.Set;

/** 
 *  @author David Dominguez Hooper
 */

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private Node root; // root of BST

    private class Node {
        private K key; // sorted by key
        private V value; // associated data
        private Node left, right; // left and right subtrees
        private int N; // number of nodes in subtree

        public Node(K oKey, V oValue, int oN) {
            this.key = oKey;
            this.value = oValue;
            this.N = oN; 
        }
    }

    /** Removes all of the mappings from this map. */
    public void clear() {
        root = null;
    }

    /* Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key. 
     */
    public V get(K key) {
        return get(root, key);
    }

    private V get(Node node, K key) {
        if (node == null) {
            return null;
        }
        int compared = key.compareTo(node.key);
        if (compared < 0) {
            return get(node.left, key);
        } else if (compared > 0) {
            return get(node.right, key);
        } else {
            return node.value;
        }
    }

    /* Returns the number of key-value mappings in this map. */
    public int size() {
        return size(root);
    }

    private int size(Node node) {
        if (node == null) {
            return 0;
        } else {
            return node.N;
        }
    }

    /* Associates the specified value with the specified key in this map. */
    public void put(K key, V value) {
        root = put(root, key, value);
    }

    private Node put(Node node, K key, V value) {
        if (node == null) {
            return new Node(key, value, 1);
        }
        int compared = key.compareTo(node.key);
        if (compared < 0) {
            node.left = put(node.left, key, value);
        } else if (compared > 0) {
            node.right = put(node.right, key, value);
        } else {
            node.value = value;
        }
        node.N = 1 + size(node.left) + size(node.right);
        return node;
    }
    /* Removes the mapping for the specified key from this map if present.
     * Not required for HW6. */
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for HW6a. */
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    /* Returns a Set view of the keys contained in this map. Not required for HW6. */
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    public void printInOrder() {
        System.out.println(printInOrder(root));
    }

    private String printInOrder(Node node) {
        if (root == null) {
            return "";
        }
        if (node == null) {
            return "";
        } else {
            String left = printInOrder(node.left);
            String right = printInOrder(node.right);
            if (left.equals("") && !right.equals("")) {
                return node.key + ", " + right;
            } else if (!left.equals("") && right.equals("")) {
                return left + ", " + node.key;
            } else if (!left.equals("") && !right.equals("")) {
                return left + ", " + node.key + ", " + right;
            } else {
                return "" + node.key;
            }
        }
    }
}
