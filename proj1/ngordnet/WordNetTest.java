package ngordnet;
import org.junit.Test;
import static org.junit.Assert.*;

/** 
 *  @author David Dominguez Hooper
 */

public class WordNetTest {
	@Test
    public void testReadIn() {
    	WordNet readInTest = new WordNet("wordnet/synsets.txt", "wordnet/hyponyms.txt");
	}

	/** Calls tests for WordNet. */
	public static void main(String[] args) {
		jh61b.junit.textui.runClasses(WordNetTest.class);
	}
}
