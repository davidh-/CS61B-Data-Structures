package ngordnet;
import java.util.Collection;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.HashSet;

public class YearlyRecord {

    private HashMap<String, Integer> countMap;
    private TreeMap<Integer, HashSet<String>> oppositeMap;

    private HashMap<String, Integer> rank;
    private boolean rankNeedsUpdate;

    /** Creates a new empty YearlyRecord. */
    public YearlyRecord() {
        countMap = new HashMap<String, Integer>();
        oppositeMap = new TreeMap<Integer, HashSet<String>>();
        rank = new HashMap<String, Integer>();
        rankNeedsUpdate = true;
    }

    /** Creates a YearlyRecord using the given data. */
    public YearlyRecord(HashMap<String, Integer> otherCountMap) {
        countMap = new HashMap<String, Integer>(otherCountMap);
        rank = new HashMap<String, Integer>(otherCountMap);
        oppositeMap = new TreeMap<Integer, HashSet<String>>();
        for (String key : countMap.keySet()) {
            putOppositeMap(countMap.get(key), key);
        }
        updateRank();
        rankNeedsUpdate = false;
    }

    private void updateRank() {
        String[] needToRank = words().toArray(new String[words().size()]);
        // System.out.println("words(): " + words());
        // System.out.println("in update rank: " + oppositeMap);
        // System.out.println("needToRank (before): " + needToRank);
        for (int i = 0; i < needToRank.length; i++) {
            rank.put(needToRank[needToRank.length - i - 1], i + 1);
        }
        // System.out.println("needToRank (after): " + needToRank);
    }

    /** Returns the number of times WORD appeared in this year. */
    public int count(String word) {
        // System.out.println(word + " " + countMap.get(word));
        if (countMap.get(word) == null) {
            return 0;
        } else {
            return countMap.get(word);
        }
    }

    /** Records that WORD occurred COUNT times in this year. */
    public void put(String word, int count) {
        // System.out.println(word);
        // if (rankNeedsUpdate) {
        //     // System.out.println("hittt11");
        //     updateRank();
        //     rankNeedsUpdate = false;
        // }
        putOppositeMap(count, word);
        // oppositeMap.put(count, word);
        rankNeedsUpdate = true;

    }

    private void putOppositeMap(int count, String word) {
        countMap.put(word, count);
        int curCount = countMap.get(word);
        if (oppositeMap.containsKey(curCount)) {
            oppositeMap.get(count).add(word);
        } else {
            HashSet<String> curHashSet = new HashSet<String>();
            curHashSet.add(word);
            oppositeMap.put(count, curHashSet);
        }
    }

    /** Returns the number of words recorded this year. */
    public int size() {
        return countMap.size();
    }

    /** Returns all words in ascending order of count. */
    public Collection<String> words() {
        ArrayList<String> words = new ArrayList<String>();
        // System.out.println("these are the hashsets of opposite values: " + oppositeMap.values());
        for (HashSet<String> curHashSet : oppositeMap.values()) {
            for (String word : curHashSet) {
                words.add(word);
                // System.out.println("these are the words(): " + words);
            }
        }
        return words;
        // return oppositeMap.values(); //if this is too slow, make a data structure of words and update it whenever you put or create and update it when you need to
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
        // System.out.println(word);
        if (rankNeedsUpdate) {
            // System.out.println("about to update (before): " + rank);
            updateRank();
            // System.out.println(oppositeMap + " after: " + rank);
            rankNeedsUpdate = false;
        }
        return rank.get(word);
    }

}
