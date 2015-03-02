import java.util.Set; /* java.util.Set needed only for challenge problem. */

/** A data structure that uses a linked list to store pairs of keys and values.
 *  Any key must appear at most once in the dictionary, but values may appear multiple
 *  times. Supports get(key), put(key, value), and contains(key) methods. The value
 *  associated to a key is the value in the last call to put with that key. 
 *
 *  For simplicity, you may assume that nobody ever inserts a null key or value
 *  into your map.
 */ 
public class ULLMap<K, V> implements Map61B<K, V> { 
    /** Keys and values are stored in a linked list of Entry objects.
      * This variable stores the first pair in this linked list. You may
      * point this at a sentinel node, or use it as the actual front item
      * of the linked list. 
      */
    private Entry front;

    @Override
    public V get(K key) { 

    }

    @Override
    public void put(K key, V val) { //FIX ME
    //FILL ME IN
    }

    @Override
    public boolean containsKey(K key) { //FIX ME
    //FILL ME IN
        return false; //FIX ME
    }

    @Override
    public int size() {
        return 0; // FIX ME (you can add extra instance variables if you want)
    }

    @Override
    public void clear() {
    //FILL ME IN
    }


    /** Represents one node in the linked list that stores the key-value pairs
     *  in the dictionary. */
    private class Entry {
    
        /** Stores KEY as the key in this key-value pair, VAL as the value, and
         *  NEXT as the next node in the linked list. */
        public Entry(K k, V v, Entry n) {
            key = k;
            val = v;
            next = n;
        }

        /** Returns the Entry in this linked list of key-value pairs whose key
         *  is equal to KEY, or null if no such Entry exists. */
        public Entry get(K k) { 
            Entry pointer = this;
            while (!k.equals(null)) {
                if (pointer.key.equals(k)) {
                    return this.val;
                }
                else {
                    pointer = pointer.next;
                }
            }
            if ((!pointer.equals(null)) && pointer.key.equals(k)) {
                return pointer;
            }
            else {
                return null;
            }
        }

        /** Stores the key of the key-value pair of this node in the list. */
        private K key; 
        /** Stores the value of the key-value pair of this node in the list. */
        private V val; 
        /** Stores the next Entry in the linked list. */
        private Entry next;
    
    }

    /* Methods below are all challenge problems. Will not be graded in any way. 
     * Autograder will not test these. */
    @Override
    public remove(K key) { //FIX ME SO I COMPILE
        throw new UnsupportedOperationException();
    }

    @Override
    public remove(K key, v value) { //FIX ME SO I COMPILE
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<> keySet() { //FIX ME SO I COMPILE
        throw new UnsupportedOperationException();
    }


}