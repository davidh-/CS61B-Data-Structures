import java.util.HashMap;
import java.util.Scanner;
/** 
 *  @author David Dominguez Hooper
 */

public class AlphabetSort {
    private HashMap<Integer, Character> dictionary;
    /**
     * No argument constructor for AlphabetSort
     */
    public AlphabetSort() {
        dictionary = new HashMap<Integer, Character>();
    }
    /**
     * @param x current node of the Trie
     * @param cur current built up string for the word to be sorted
     */
    private void sortWords(Trie.Node x, String cur) {
        if (x.exists) {
            System.out.println(cur);
        }
        for (int i = 0; i < dictionary.size(); i++) {
            Character curC = dictionary.get(i);
            if (x.links.containsKey(curC)) {
                sortWords(x.links.get(curC), cur + curC);
            }
        }
    }
    /**
     * @param args takes the name of an input file and an integer k as command-line arguments
     */
    public static void main(String[] args) {
        AlphabetSort aS = new AlphabetSort();
        Trie allWords = new Trie();
        Scanner in = new Scanner(System.in);

        if (in.hasNextLine()) {
            char[] dictSplit = in.nextLine().toCharArray();
            for (int i = 0; i < dictSplit.length; i++) {
                if (aS.dictionary.containsValue(dictSplit[i])) {
                    throw new IllegalArgumentException(
                        "A letter appears multiple times in the alphabet.");
                } else {
                    aS.dictionary.put(i, dictSplit[i]);
                }
            }
        } else {
            throw new IllegalArgumentException("No words or alphabet are given.");
        }
        if (in.hasNextLine()) {
            while (in.hasNextLine()) {
                allWords.insert(in.nextLine());
            }
        } else {
            throw new IllegalArgumentException("No words or alphabet are given.");
        }
        aS.sortWords(allWords.root, "");
    }
}
