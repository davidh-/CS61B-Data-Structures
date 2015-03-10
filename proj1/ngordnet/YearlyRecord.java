package ngordnet;
import java.util.Collection;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.TreeMap;

public class YearlyRecord {

    private HashMap<String, Integer> countMap;
    private TreeMap<Integer, String> oppositeMap;

    private HashMap<String, Integer> rank;
    private boolean rankNeedsUpdate;

    /** Creates a new empty YearlyRecord. */
    public YearlyRecord() {
        countMap = new HashMap<String, Integer>();
        oppositeMap = new TreeMap<Integer, String>();
        rank = new HashMap<String, Integer>();
        rankNeedsUpdate = true;
    }

    /** Creates a YearlyRecord using the given data. */
    public YearlyRecord(HashMap<String, Integer> otherCountMap) {
        countMap = new HashMap<String, Integer>(otherCountMap);
        rank = new HashMap<String, Integer>(otherCountMap);
        oppositeMap = new TreeMap<Integer, String>();
        for (String key : countMap.keySet()) {
            oppositeMap.put(countMap.get(key), key);
        }
        updateRank();
        rankNeedsUpdate = false;
    }

    private void updateRank() {
        String[] needToRank = words().toArray(new String[words().size()]);
        for (int i = 0; i < needToRank.length; i++) {
            rank.put(needToRank[needToRank.length - i - 1], i + 1);
        }
    }

    /** Returns the number of times WORD appeared in this year. */
    public int count(String word) {
        if (countMap.get(word) == null) {
            return 0;
        } else {
            return countMap.get(word);
        }
    }

    /** Records that WORD occurred COUNT times in this year. */
    public void put(String word, int count) {
        System.out.println(word);
        if (rankNeedsUpdate) {
            System.out.println("hittt11");
            updateRank();
            rankNeedsUpdate = false;
        }
        countMap.put(word, count);
        oppositeMap.put(count, word);
        rankNeedsUpdate = true;
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
        System.out.println(word);
        if (rankNeedsUpdate) {
            updateRank();
            rankNeedsUpdate = false;
        }
        return rank.get(word);
    }

}
