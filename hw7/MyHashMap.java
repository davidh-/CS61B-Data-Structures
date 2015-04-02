import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Arrays;
/** 
 *  @author David Dominguez Hooper
 */

public class MyHashMap<K, V> implements Map61B<K, V> {

    private class Entry {
        private final K key;
        private V value;

        private Entry() {
            key = null;
            value = null;
        }
        private Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private ArrayList<Entry>[] buckets;
    private HashSet<K> keys;
    private int initialSize;
    private double loadFactor;
    private int size;

	public MyHashMap() {
        initialSize = 16;
        loadFactor = 0.75;
        size = 0;
        buckets = (ArrayList<Entry>[]) new ArrayList[initialSize];
        keys = new HashSet<K>();
	}
	public MyHashMap(int initialSize) {
        this.initialSize = initialSize;
        loadFactor = 0.75;
        size = 0;
        buckets = (ArrayList<Entry>[]) new ArrayList[initialSize];
        keys = new HashSet<K>();
	}
	public MyHashMap(int initialSize, float loadFactor) {
        this.initialSize = initialSize;
        this.loadFactor = loadFactor;
        size = 0;
        buckets = (ArrayList<Entry>[]) new ArrayList[initialSize];
        keys = new HashSet<K>();
	}
    /** Removes all of the mappings from this map. */
    public void clear() {
        buckets = null;
        keys = null;
    }

    /* Returns true if this map contains a mapping for the specified key. 
     * Should run on average constant time when called on a HashMap. 
     */
    public boolean containsKey(K key) {
    	return keys.contains(key);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key. Should run on average constant time
     * when called on a HashMap. 
     */
    public V get(K key) {
        if (key == null) {
            return null;
        }
        ArrayList<Entry> curBucket = buckets[getIndexFromHashCode(key.hashCode())];
        if (curBucket != null) {
            for (Entry entry : curBucket) {
                if (entry.key.equals(key)) {
                    return entry.value;
                }
            }
        }
        return null;
    }

    private int getIndexFromHashCode(int hashCode) {
        if (hashCode < 0) {
            return initialSize + (hashCode % initialSize);
        }
        else {
            return hashCode % initialSize;
        }
    }

    /* Returns the number of key-value mappings in this map. */
    public int size() {
        return size;
    }

    /* Associates the specified value with the specified key in this map. 
     * Should run on average constant time when called on a HashMap. */
    public void put(K key, V value) {
        if (size / initialSize > loadFactor) {
            ArrayList<Entry>[] oldData = Arrays.copyOf(buckets, buckets.length);
            buckets = (ArrayList<Entry>[]) new ArrayList[initialSize * 2];
            for (ArrayList<Entry> curArrayList : oldData) {
                for (Entry e : curArrayList) {
                    put(e.key, e.value);
                }
            }
        }

        int index = getIndexFromHashCode(key.hashCode()); 
        if (containsKey(key)) {
            ArrayList<Entry> curBucket = buckets[index];
            if (curBucket != null) {
                for (Entry entry : curBucket) {
                    if (entry.key.equals(key)) {
                        entry.value = value;
                        return;
                    }
                }
            }
        }
        else {
            ArrayList<Entry> curBucket = buckets[index];
            if (curBucket == null) {
                curBucket = new ArrayList<Entry>();
            }
            curBucket.add(new Entry(key, value));
            size += 1;
            keys.add(key);
        }
    }

    /* Removes the mapping for the specified key from this map if present. 
     * Should run on average constant time when called on a HashMap. */
    public V remove(K key) {
    	if (containsKey(key)) {
            int index = getIndexFromHashCode(key.hashCode()); 
            ArrayList<Entry> curBucket = buckets[index];
            for (int i = 0; i < curBucket.size(); i++) {
                if (curBucket.get(i).key.equals(key)) {
                    Entry removed = curBucket.remove(i);
                    return removed.value;
                }
            }
            return null;
        }
        else {
            return null;
        }
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Should run on average constant time when called on 
     * a HashMap. */
    public V remove(K key, V value) {
        if (containsKey(key)) {
            int index = getIndexFromHashCode(key.hashCode()); 
            ArrayList<Entry> curBucket = buckets[index];
            for (int i = 0; i < curBucket.size(); i++) {
                if (curBucket.get(i).key.equals(key) && curBucket.get(i).value.equals(value)) {
                    Entry removed = curBucket.remove(i);
                    return removed.value;
                }
            }
            return null;
        }
        else {
            return null;
        }
    }

    /* Returns a Set view of the keys contained in this map. */
    public Set<K> keySet() {
    	return keys;
    }
}