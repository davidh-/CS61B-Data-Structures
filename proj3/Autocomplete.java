import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Collections;
import java.util.TreeSet;
import java.util.ArrayList;

/**
 * Implements autocomplete on prefixes for a given dictionary of terms and weights.
 * @author David Dominguez Hooper
 */
public class Autocomplete {
    /**
     * Class WeightedString
     */
    private class WeightedString implements Comparable<WeightedString> {
        
        String string;
        Double weight;
        /**
         * Default Constructor for WeightedString
         */
        public WeightedString() {
            string = "";
            weight = 0.0;
        }
        /**
         * Two argument Constructor for WeightedString
         * @param string string
         * @param weight weight
         */
        public WeightedString(String string, Double weight) {
            this.string = string;
            this.weight = weight;
        }
        /**
         * comapreTo method
         * @param wString string
         * @return compared weight
         */
        public int compareTo(WeightedString wString) {
            return wString.weight.compareTo(this.weight);
        }
    }
    private WTrie words;
    private boolean finished;
    /**
     * Initializes required data structures from parallel arrays.
     * @param terms Array of terms.
     * @param weights Array of weights.
     */
    public Autocomplete(String[] terms, double[] weights) {
        if (terms.length != weights.length) {
            throw new IllegalArgumentException(
                "The length of the terms and weights arrays are different");
        }
        words = new WTrie();
        finished = false;
        for (int i = 0; i < terms.length; i++) {
            if (words.find(terms[i], true)) {
                throw new IllegalArgumentException(
                    "There are duplicate input terms");
            } else if (weights[i] < 0) {
                throw new IllegalArgumentException(
                    "There are negative weights");
            }
            words.insert(terms[i], weights[i]);
        }
    }

    /**
     * Find the weight of a given term. If it is not in the dictionary, return 0.0
     * @param term does this
     * @return double
     */
    public double weightOf(String term) {
        Double weightOfTerm = words.findWeight(term, true);
        if (weightOfTerm == null) {
            return 0.0;
        } else {
            return weightOfTerm;
        }
    }

    /**
     * Return the top match for given prefix, or null if there is no matching term.
     * @param prefix Input prefix to match against.
     * @return Best (highest weight) matching string in the dictionary.
     */
    public String topMatch(String prefix) {
        String match = "";
        for (String word : topMatches(prefix, 1)) {
            match = word;
            break;
        }
        return match;
    }

    /**
     * Returns the top k matching terms (in descending order of weight) as an iterable.
     * If there are less than k matches, return all the matching terms.
     * @param prefix does this
     * @param k does this
     * @return an iterable of strings
     */
    public Iterable<String> topMatches(String prefix, int k) {
        if (k < 0) {
            throw new IllegalArgumentException(
                "Cannot find the k top matches for non-positive k.");
        }
        //this is the node that matches the prefix.
        WTrie.Node matchNode = words.get(words.root, prefix, 0, "", false);

        //creating maxPQ which orders by descending order
        PriorityQueue<WTrie.Node> maxPQ = 
            new PriorityQueue<WTrie.Node>(matchNode.links.size() * 2, Collections.reverseOrder());
        TreeSet<WeightedString> matches = new TreeSet<WeightedString>();

        topMatchesR(matchNode, maxPQ, matches, k);

        ArrayList<String> finalMatches = new ArrayList<String>();
        for (WeightedString wString : matches) {
            finalMatches.add(wString.string);
        }
        return finalMatches;
    }
    /**
     * Returns the top k matching terms (in descending order of weight) as an iterable.
     * If there are less than k matches, return all the matching terms.
     * @param x does this
     * @param maxPQ does this
     * @param matches does this
     * @param k does this
     */
    private void topMatchesR(WTrie.Node x, 
            PriorityQueue<WTrie.Node> maxPQ, TreeSet<WeightedString> matches, int k) {
        if (x != null) {
            if (matches.size() >= k && x.max.compareTo(matches.last().weight) <= 0) {
                return;
            }
            for (Character c : x.links.keySet()) {
                maxPQ.add(x.links.get(c));
            }
            if (x.val != null) {
                matches.add(new WeightedString(x.current, x.val));
            }
            while (maxPQ.size() > 0) {
                WTrie.Node curNode = maxPQ.poll();
                topMatchesR(curNode, maxPQ, matches, k);
            }
        }
    }
    /**
     * Returns the highest weighted matches within k edit distance of the word.
     * If the word is in the dictionary, then return an empty list.
     * @param word The word to spell-check
     * @param dist Maximum edit distance to search
     * @param k    Number of results to return 
     * @return Iterable in descending weight order of the matches
     */
    public Iterable<String> spellCheck(String word, int dist, int k) {
        LinkedList<String> results = new LinkedList<String>();  
        /* YOUR CODE HERE; LEAVE BLANK IF NOT PURSUING BONUS */
        return results;
    }
    /**
     * Test client. Reads the data from the file, 
     * then repeatedly reads autocomplete queries from 
     * standard input and prints out the top k matching terms.
     * @param args takes the name of an input file and an integer k as command-line arguments
     */
    public static void main(String[] args) {
        // initialize autocomplete data structure
        In in = new In(args[0]);
        int N = in.readInt();
        String[] terms = new String[N];
        double[] weights = new double[N];
        for (int i = 0; i < N; i++) {
            weights[i] = in.readDouble();   // read the next weight
            in.readChar();                  // scan past the tab
            terms[i] = in.readLine();       // read the next term
        }

        Autocomplete autocomplete = new Autocomplete(terms, weights);
        System.out.println("Ready: ");
        // process queries from standard input
        int k = Integer.parseInt(args[1]);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            for (String term : autocomplete.topMatches(prefix, k)) {
                StdOut.printf("%14.1f  %s\n", autocomplete.weightOf(term), term);
            }
        }
    }
}
