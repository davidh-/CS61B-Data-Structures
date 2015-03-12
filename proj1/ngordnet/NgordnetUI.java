package ngordnet;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.In;

/** Provides a simple user interface for exploring WordNet and NGram data.
 *  @author David Dominguez Hooper
 */
public class NgordnetUI {
    public static void main(String[] args) {
        In in = new In("./ngordnet/ngordnetui.config");
        String wordFile = in.readString();
        String countFile = in.readString();
        String synsetFile = in.readString();
        String hyponymFile = in.readString();
        NGramMap nGramMap = new NGramMap(wordFile, countFile);
        WordNet wordNet = new WordNet(synsetFile, hyponymFile);
        int startYear = 0;
        int endYear = 0;
        while (true) {
            try {
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
                        Plotter.plotAllWords(nGramMap, tokens, startYear, endYear);
                        break;
                    case "hypohist":
                        Plotter.plotCategoryWeights(nGramMap, wordNet, tokens, startYear, endYear);
                        break;
                    case "wordlength":
                        WordLengthProcessor wlp = new WordLengthProcessor();
                        Plotter.plotProcessedHistory(nGramMap, startYear, endYear, wlp);
                        break;
                    case "zipf":
                        int yearZipf = Integer.parseInt(tokens[0]);
                        Plotter.plotZipfsLaw(nGramMap, yearZipf);
                        break;
                    default:
                        System.out.println("Invalid command.");  
                        break;
                }
            } catch (RuntimeException e) {
                break;
            }
        }
    }
}
