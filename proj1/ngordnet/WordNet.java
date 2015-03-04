package ngordnet;

import edu.princeton.cs.algs4.Digraph;
import java.util.Set;

import java.util.ArrayList;

import java.util.TreeSet;
import edu.princeton.cs.introcs.In;

/** 
 *  @author David Dominguez Hooper
 */

public class WordNet {

    private ArrayList<TreeSet> synsets;
    private Digraph relationships;
    /** Creates a WordNet using files form SYNSETFILENAME and HYPONYMFILENAME */
    public WordNet(String synsetFilename, String hyponymFilename) {
        synsets = new ArrayList<TreeSet>();
        int sizeSynsets = 0;
        In inSynset = new In(synsetFilename);
        while (inSynset.hasNextLine()) {
            TreeSet<String> curSet = new TreeSet<String>();
            String curLine = inSynset.readLine();
            String[] curLineSplit = curLine.split(",");
            if (!inSynset.hasNextLine()) {
                sizeSynsets = Integer.parseInt(curLineSplit[0]);
            }
            String nounOrNouns = curLineSplit[1];
            String[] possibleMultipleNouns = nounOrNouns.split(" ");
            if (possibleMultipleNouns.length > 1) {
                for (String newWord : possibleMultipleNouns) {
                    curSet.add(newWord);
                }
            }
            else {
                curSet.add(nounOrNouns);
            }
            synsets.add(curSet);
        }

        relationships = new Digraph(sizeSynsets + 1);
        In inHyponym = new In(hyponymFilename);
        while (inHyponym.hasNextLine()) {
            String curLine = inHyponym.readLine();
            String[] curLineSplit = curLine.split(",");
            int i = 1;
            while (i < curLineSplit.length) {
                relationships.addEdge(Integer.parseInt(curLineSplit[0]), 
                    Integer.parseInt(curLineSplit[i]));
                i += 1;
            }
        }
    }
    
    /* Returns true if NOUN is a word in some synset. */
    public boolean isNoun(String noun) {
        return this.nouns().contains(noun);
    }

    /* Returns the set of all nouns. */
    public Set<String> nouns() {
        TreeSet<String> setOfAllNouns = new TreeSet<String>();
        for (TreeSet<String> synset : synsets) {
            if (synset.size() > 1) {
                for (String noun : synset) {
                    setOfAllNouns.add(noun); 
                }   
            }
            else {
                setOfAllNouns.add(synset.first());
            }
        }
        return setOfAllNouns;
    }

    /** Returns the set of all hyponyms of WORD as well as all synonyms of
      * WORD. If WORD belongs to multiple synsets, return all hyponyms of
      * all of these synsets. See http://goo.gl/EGLoys for an example.
      * Do not include hyponyms of synonyms.
      */
    public Set <String> hyponyms(String word) {
        Set<String> allHyponyms = new <String> TreeSet();
        int index = getIndex(word);

        Set<Integer> curWord = new TreeSet<Integer>();
        curWord.add(index);
        Set<Integer> descendantIndexes = ngordnet.GraphHelper.descendants(relationships, curWord);
        for (int descendantIndex : descendantIndexes) {
            TreeSet<String> synset = synsets.get(index);
            for (String noun : synset){
                allHyponyms.add(noun);
            }
        }
        return allHyponyms;
    }

    private int getIndex(String word) {
        for (int i = 0; i < synsets.size(); i++) {
            TreeSet<String> synset = synsets.get(i);
            if (synset.contains(word)) {
                return i;
            }
        }
        return -1;
    }
    private static void main(String[] args) {
        WordNet readInTest = new WordNet("wordnet/synsets11.txt", "wordnet/hyponyms11.txt");
        System.out.println(readInTest.relationships);

    }

}
