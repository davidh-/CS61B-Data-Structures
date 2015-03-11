package ngordnet;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.In;
import java.util.TreeSet;
import java.util.Set;

/** Provides a simple user interface for exploring WordNet and NGram data.
 *  @author David Dominguez Hooper
 */
public class NgordnetUI {
    public static void main(String[] args) {
        In in = new In("./ngordnet/ngordnetui.config");
        System.out.println("Reading ngordnetui.config...");

        String wordFile = in.readString();
        String countFile = in.readString();
        String synsetFile = in.readString();
        String hyponymFile = in.readString();
        System.out.println("\nBased on ngordnetui.config, using the following: "
                           + wordFile + ", " + countFile + ", " + synsetFile +
                           ", and " + hyponymFile + ".");
        NGramMap nGramMap = new NGramMap(wordFile, countFile);
        WordNet wordNet = new WordNet(synsetFile, hyponymFile);
        Plotter plot = new Plotter();
        int startYear = 0;
        int endYear = 0;
        while (true) {

            System.out.print("> ");
            String line = StdIn.readLine();
            String[] rawTokens = line.split(" ");
            String command = rawTokens[0];
            String[] tokens = new String[rawTokens.length - 1];
            System.arraycopy(rawTokens, 1, tokens, 0, rawTokens.length - 1);

            switch (command) {
                case "quit": 
                    return;
                case "help":
                    In in2 = new In("/ngordnet/help.txt");
                    String helpStr = in2.readAll();
                    System.out.println(helpStr);
                    break;  
                case "range": 
                    startYear = Integer.parseInt(tokens[0]); 
                    endYear = Integer.parseInt(tokens[1]);
                    break;
                case "count":
                    String word = tokens[0]; 
                    int year = Integer.parseInt(tokens[1]);
                    System.out.println(nGramMap.countInYear(word, year));
                    break; 
                case "hyponyms": 
                    String curWord = tokens[0];
                    System.out.println(wordNet.hyponyms(curWord)); 
                    break;  
                case "history": 
                    plot.plotAllWords(nGramMap, tokens, startYear, endYear);
                    break;
                case "hypohist": 
                    TreeSet<String> hyponymsOfWords = new TreeSet<String>();
                    for (String token : tokens) {
                        hyponymsOfWords.addAll(wordNet.hyponyms(token));
                    }
                    plot.plotAllWords(nGramMap, hyponymsOfWords.toArray(new String[hyponymsOfWords.size()]), startYear, endYear);
                    break;
                default:
                    System.out.println("Invalid command.");  
                    break;
            }
        }
    }
} 

