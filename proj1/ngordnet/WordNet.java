/** 
 *  @author David Dominguez Hooper
 */

public class WordNet {
    /** Creates a WordNet using files form SYNSETFILENAME and HYPONYMFILENAME */
    public WordNet(String synsetFilename, String hyponymFilename) 

    /* Returns true if NOUN is a word in some synset. */
    public boolean isNoun(String noun)

    /* Returns the set of all nouns. */
    public Set<String> nouns()

    /** Returns the set of all hyponyms of WORD as well as all synonyms of
      * WORD. If WORD belongs to multiple synsets, return all hyponyms of
      * all of these synsets. See http://goo.gl/EGLoys for an example.
      * Do not include hyponyms of synonyms.
      */
    public Set<String> hyponyms(String word)
}