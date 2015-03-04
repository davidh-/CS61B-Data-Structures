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

    private ArrayList nouns;

    /** Creates a WordNet using files form SYNSETFILENAME and HYPONYMFILENAME */
    public WordNet(String synsetFilename, String hyponymFilename) {
        In inSynset = new In(synsetFilename);
        In inHyponym = new In(hyponymFilename);
        while (inSynset.hasNextLine()) {
            Set curSet = new Set();
            String curLine = inSynset.readLine();
            String[] curLineSplit = curLine.split(",");
            String nounOrNouns = curLineSplit[1];
            String[] possibleMultipleNouns = nounOrNouns.split(" ");
            if (possibleMultipleNouns.length > 1) {
                for (String newWord : newWordsSpace) {
                    curSet.add(newWord);
                }
            }
            else {
                curSet.add(nounOrNouns);
            }
            nouns.add(curSet);
            // //Get rid of everything up to the first "," :
            // while(curLine.substring(1, 2) != ",") {
            //     curLine = curLine.substring(1, curLine.length());
            // }
            // curLine = curLine.substring(1, curLine.length());

            // //Reached noun(s), now get noun(s) :
            // Set curSet = new Set();
            // String curWord = "";
            // while(curLine.substring(1, 2) != ",") {
            //     if (curLine.substring(0, 1) == " ") {
            //         curSet.add(curWord);
            //         curWord = "";
            //     }
            //     curWord += curLine.substring(0, 1);
            //     curLine = curLine.substring(1, curLine.length());
            // }
            // testInSynset.add("curLine");
        }

        
        System.out.println(testInSynset);
    }
    
    /* Returns true if NOUN is a word in some synset. */
    public boolean isNoun(String noun) {
        return false;
    }

    /* Returns the set of all nouns. */
    public Set<String> nouns() {
        return null;
    }

    /** Returns the set of all hyponyms of WORD as well as all synonyms of
      * WORD. If WORD belongs to multiple synsets, return all hyponyms of
      * all of these synsets. See http://goo.gl/EGLoys for an example.
      * Do not include hyponyms of synonyms.
      */
    public Set<String> hyponyms(String word) {
        return null;
    }

    public static void main(String[] args) {
        String test = "wordnet/synsets11.txt";
        In inSynset = new In(test);
        while(!inSynset.isEmpty()) {
            String curLine = inSynset.readLine();
            String[] words = curLine.split(",");
            for (String word : words) {
                String[] newWordsSpace = word.split(" ");
                if (newWordsSpace.length > 1) {
                    for (String newWord : newWordsSpace) {
                        System.out.println("\nNEW  WORDDDD: " + newWord);
                    }
                }
                System.out.println(word);
            }
        }
    }
}