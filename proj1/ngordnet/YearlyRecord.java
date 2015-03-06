package ngordnet;
import java.util.Collection;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;


public class YearlyRecord {

    TreeMap<String, Integer> countMap;
    TreeMap<Integer, String> oppositeMap;

    /** Creates a new empty YearlyRecord. */
    public YearlyRecord() {
        countMap = new TreeMap<String, Integer>();
        oppositeMap = new TreeMap<Integer, String>();
    }

    /** Creates a YearlyRecord using the given data. */
    public YearlyRecord(HashMap<String, Integer> otherCountMap) {
        countMap = new TreeMap<String, Integer>(otherCountMap);
        oppositeMap = new TreeMap<Integer, String>();
        for (String key : countMap.keySet()) {
            oppositeMap.put(countMap.get(key), key);
        }
    }

    /** Returns the number of times WORD appeared in this year. */
    public int count(String word) {
        return countMap.get(word);
    }

    /** Records that WORD occurred COUNT times in this year. */
    public void put(String word, int count) {
        countMap.put(word, count);
        oppositeMap.put(count, word);
    }

    /** Returns the number of words recorded this year. */
    public int size() {
        return countMap.size();
    }

    /** Returns all words in ascending order of count. */
    public Collection<String> words() {
        return oppositeMap.values();
    }

    /** Returns all counts in ascending order of count. */
    public Collection<Number> counts() {
        return new ArrayList<Number>(oppositeMap.keySet());
    }

    /** Returns rank of WORD. Most common word is rank 1. 
      * If two words have the same rank, break ties arbitrarily. 
      * No two words should have the same rank.
      */
    public int rank(String word) {
        ArrayList<String> rank = new ArrayList<String>(words());
        return size() - rank.indexOf(word);
    }

}
