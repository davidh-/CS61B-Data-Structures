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
        // System.out.println("Reading ngordnetui.config...");

        String wordFile = in.readString();
        String countFile = in.readString();
        String synsetFile = in.readString();
        String hyponymFile = in.readString();
        // System.out.println("\nBased on ngordnetui.config, using the following: "
        //                    + wordFile + ", " + countFile + ", " + synsetFile +
        //                    ", and " + hyponymFile + ".");

        NGramMap nGramMap = new NGramMap(wordFile, countFile);
        WordNet wordNet = new WordNet(synsetFile, hyponymFile);
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
                    if (tokens.length == 2) {
                        try {
                            startYear = Integer.parseInt(tokens[0]); 
                            endYear = Integer.parseInt(tokens[1]);
                        } catch (Exception e){
                            break;
                        }
                    }
                    break;
                case "count":
                    if (tokens.length == 2) {
                        try {
                            String word = tokens[0]; 
                            int year = Integer.parseInt(tokens[1]);
                            System.out.println(nGramMap.countInYear(word, year));
                        } catch (Exception e){
                            break;
                        }
                    }
                    break; 
                case "hyponyms": 
                    if (tokens.length == 1) {
                        try {
                            String curWord = tokens[0];
                            System.out.println(wordNet.hyponyms(curWord)); 
                        } catch (Exception e){
                            break;
                        }
                    }
                    break;  
                case "history": 
                    if (tokens.length > 0) {
                        try {
                            Plotter.plotAllWords(nGramMap, tokens, startYear, endYear);
                        } catch (Exception e){
                            break;
                        }
                    }
                    break;
                case "hypohist":
                    if (tokens.length > 0) {
                        try {
                            Plotter.plotCategoryWeights(nGramMap, wordNet, tokens, startYear, endYear);
                        } catch (Exception e){
                            break;
                        }
                    } 
                    break;
                case "wordlength":
                    Plotter.plotProcessedHistory(nGramMap, startYear, endYear, new WordLengthProcessor());
                    break;
                case "zipf":
                    if (tokens.length == 1) {
                        try {
                            int year = Integer.parseInt(tokens[0]);
                            Plotter.plotZipfsLaw(nGramMap, year);
                        } catch(Exception e) {
                            break;
                        }
                    }
                    break;
                default:
                    System.out.println("Invalid command.");  
                    break;
            }
        }
    }
} 

