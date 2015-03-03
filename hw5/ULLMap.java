import java.util.Set; /* java.util.Set needed only for challenge problem. */
import java.util.Iterator;
/** A data structure that uses a linked list to store pairs of keys and values.
 *  Any key must appear at most once in the dictionary, but values may appear multiple
 *  times. Supports get(key), put(key, value), and contains(key) methods. The value
 *  associated to a key is the value in the last call to put with that key. 
 *
 *  For simplicity, you may assume that nobody ever inserts a null key or value
 *  into your map.
 */ 
public class ULLMap<K, V> implements Map61B<K, V>, Iterable<K>{ 
    /** Keys and values are stored in a linked list of Entry objects.
      * This variable stores the first pair in this linked list. You may
      * point this at a sentinel node, or use it as the actual front item
      * of the linked list. 
      */
    private Entry front;
    private int size;


    public static <K, V> ULLMap<V, K> invert(ULLMap<K, V> dict) {
        ULLMap<V, K> inverseDict = new ULLMap<V, K>();
        for (K curKey : dict) {
            inverseDict.put(dict.get(curKey), curKey);
        }
        return inverseDict;
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key. 
     */
    @Override
    public V get(K key) { 
        if (this.front == null) {
            return null;
        }
        Entry expectedEntryWithValue = this.front.get(key);
        if (expectedEntryWithValue == null) {
            return null;
        }
        else { 
            return expectedEntryWithValue.val;
        }
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V val) { 
        if (this.front != null) {
            Entry currentEntry = this.front.get(key);
            if (currentEntry == null) {
                this.front = new Entry(key, val, this.front);
            }
            else {
                currentEntry.val = val;
            }
        }
        else {
            this.front = new Entry(key, val, this.front);
            this.size += 1;
        }
    }

    /* Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) { 
        if (this.front == null) {
            return false;
        }
        else {
            if (this.front.get(key) != null) {
                return true;
            }
            else {
                return false;
            }
        }
    }

   /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return this.size; 
    }

    /** Removes all of the mappings from this map. */
    @Override
    public void clear() {
        this.size = 0;
        this.front = null;
    }

    @Override
    public Iterator<K> iterator() {
        return new ULLMapIter();
    }

    private class ULLMapIter implements Iterator<K>{
        private Entry curEntry;

        public ULLMapIter() {
            this.curEntry = front;
        }
        public boolean hasNext() {
            return this.curEntry != null;
        }
        public K next() {
            K needToReturnKey = this.curEntry.key;
            this.curEntry = this.curEntry.next;
            return needToReturnKey;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
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
            while (pointer != null) {
                if (pointer.key.equals(k)) {
                    return pointer;
                }
                else {
                    pointer = pointer.next;
                }
            }
            return pointer;
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

    /* Removes the mapping for the specified key from this map if present.
     * Not required for HW5. */
    @Override
    public V remove(K key) { //FIX ME SO I COMPILE
        throw new UnsupportedOperationException();
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for HW5. */
    @Override
    public V remove(K key, V value) { //FIX ME SO I COMPILE
        throw new UnsupportedOperationException();
    }

    /* Returns a Set view of the keys contained in this map. Not required for HW5. */
    @Override
    public Set<K> keySet() { //FIX ME SO I COMPILE
        // TreeSet<K> keyCopy = new TreeSet<K>();
        // for (K key : this) {
        //     keyCopy.add(key);
        // }
        // return keyCopy;
        throw new UnsupportedOperationException();
    }


}