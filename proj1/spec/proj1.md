~ number: 1
~ title: NGordNet (Pre-Alpha)

**Warning: This spec is a pre-release alpha. It is intended for reader and GSI use only. While we cannot stop you if you have discovered this unlinked file, be aware that the spec is subject to arbitrarily gigantic revisions until Feb 25th.**

In this project, we will explore how the volume of printed works in English has changed over time, as well as the structure of the is-a relationships of words in English.

Key things we'll investigate:

 - The relative popularity of words over time.
 - The relative popularity of categories of words over time.
 - The length of words.
 - Zipf's law.

To support these investigations, you will write a new package from scratch called ngorndnet that contains the following classes:

    public class WordNet
    public class TimeSeries<T extends Number> extends TreeMap<Integer, T>
    public class YearlyRecord
    public class NGramMap
    public class WordLengthProcessor implements YearlyRecordProcessor
    public class Plotter
    public class NgrordnetUI (maybe provided)
    Additional protected test classes.

Along the way we'll get lots of experience with different useful data structures. The full technical specification for your package can be found [here](add java docs). Below follows a description of each class. You can go in any order you choose, but we recommend that you work through the project in the order given in this document.

To get started, use the ever fun to type ```git pull skeleton master```. You'll also need to download the project 1 datafiles (not provided via github for space reasons). You can find them [at this link](http://www.cs.berkeley.edu/~hug/p1data.zip). You should unzip these into the proj1 directory.

1: The WordNet Class
=====

Before you write the WordNet class, it'll be important that you understand the nature of the WordNet database and the structure of the files representing it. The purpose of the WordNet class is to take these files and store them in a more useful format that supports rapid queries.

[WordNet](http://en.wikipedia.org/wiki/WordNet) is a semantic lexicon for the English language that is used extensively by computational linguists and cognitive scientists; for example, it was a key component in IBM's Watson. WordNet groups words into sets of synonyms called synsets and describes semantic relationships between them. One such relationship is the is-a relationship, which connects a hyponym (more specific synset) to a hypernym (more general synset). For example, "change" is a hypernym of "demotion", since "demotion" is-a (type of) "change". "change" is in turn a hyponym of "action", since "change" is-a (type of) "action". If you'd like to get a deeper understanding of the WordNet database, you can [check out the web interface](http://wordnetweb.princeton.edu/perl/webwn?o2=&o0=1&o8=1&o1=1&o7=&o5=&o9=&o6=&o3=&o4=&r=1&s=sturgeon&i=3&h=1000#c).

Synsets consist of one or more words in English that all have the same meaning. For example, one synset is ["jump, parachuting"](http://wordnetweb.princeton.edu/perl/webwn?o2=&o0=1&o8=1&o1=1&o7=&o5=&o9=&o6=&o3=&o4=&s=jump&i=6&h=000010000000000000000000#c), which represents the act of descending to the ground with a parachute. "jump, parachuting" is a hyponym of "descent", since "jump, parachuting" is-a "descent". 

Words in English may also belong to multiple synsets. For example, the word jump also belongs to the synset ["jump, leap"](http://wordnetweb.princeton.edu/perl/webwn?o2=&o0=1&o8=1&o1=1&o7=&o5=&o9=&o6=&o3=&o4=&s=jump&i=2&h=100000000000000000000000#c), which represents the more figurative notion of jumping (e.g. a jump in attendance). The hypernym of the synset "jump, leap" is "increase", since "jump, leap" is-an "increase", though of course there are other ways to increase.

Synsets may include not just words, but also what are known as [http://en.wikipedia.org/wiki/Collocation](collocations). You can think of these as single words that occur next to each other so often that they are considered a single word, e.g. [car pool](http://wordnetweb.princeton.edu/perl/webwn?s=car+pool&sub=Search+WordNet&o2=&o0=1&o8=1&o1=1&o7=&o5=&o9=&o6=&o3=&o4=&h=100100). For simplicity, we will refer to collocations as simply "words" throughout this document. 

Occasionally, synsets may have multiple hypernyms, for example, the mysterious synset ["group action"](http://wordnetweb.princeton.edu/perl/webwn?o2=&o0=1&o8=1&o1=1&o7=&o5=&o9=&o6=&o3=&o4=&s=group+action&i=3&h=1000#c), which has two hyperynms "act, deed, human action, human activity" and "event".


The WordNet digraph 
-----------

To store the WordNet dataset, you'll need to use at least two data structures. One of these will be a data structure we haven't discussed in class yet called a DAG (short for DirectedAcyclicGraph). 

A small subgraph of the WordNet DAG is illustrated below. In our copy of the dataset (and in the graph below), words that belong to the same synset are separated by spaces. To avoid ambiguity with collocations, we represent collocations as being separated by underscores instead of spaces (e.g. "car_pool").

![WordNet](wordnet-fig1.png "WordNet")

A graph consists of a set of V vertices and E edges (drawn as arrows above) between vertices. For example, in the graph above, V = 27 and E = 26. We see that the synset "jump leap" points at "increase". 

Your first task in this assignment is to convert the provided synsets.txt and hypernyms.txt files into a graph. We'll be using a class from the ```edu.princeton.cs.algs4``` package called Digraph (which you can import with ```edu.princeton.cs.algs4.Digraph```. You can think of this class as having only a constructor an addEdge method.

    public Digraph() {
        /** Creates a new Digraph with V vertices. */
        public Digraph(int V)

        /** Adds an edge between vertex v and w. */
        public void addEdge(int v, int w)
    }

Note that the Digraph class requires us to know the number of vertices in advance, and only allows us to add edges based on a vertex number, e.g. the following desirable code wouldn't work:

    Digraph g = new Digraph(100);
    g.addEdge("dash sprint", "run running");
    g.addEdge("locomotion travel", "motion movement move");

This might seem like an annoying limitation of our Digraph class. However, even if the Digraph class allowed such convenient syntax, it wouldn't work for WordNet, because there can be multiple Synsets that have the exact same String. For example there are two synsets represented by exactly the String ["American"](http://wordnetweb.princeton.edu/perl/webwn?s=American&sub=Search+WordNet&o2=&o0=1&o8=1&o1=1&o7=&o5=&o9=&o6=&o3=&o4=&h=0), each with their own hypernyms. 

To avoid this ambiguity, the synsets.txt and hypernyms.txt files have a special structure, described in the next section.

The WordNet input file formats.
------
We now describe the two data files that you will use to create the wordnet digraph. The files are in CSV format: each line contains a sequence of fields, separated by commas.

 - List of noun synsets. The file synsets.txt lists all the (noun) synsets in WordNet. The first field is the synset id (an integer), the second field is the synonym set (or synset), and the third field is its dictionary definition (or gloss). For example, the line

        36,AND_circuit AND_gate,a circuit in a computer that fires only when all of its inputs fire

    means that the synset ```{ AND_circuit, AND_gate }``` has an id number of 36 and its definition is "a circuit in a computer that fires only when all of its inputs fire". The individual nouns that comprise a synset are separated by spaces (and a synset element is not permitted to contain a space). The S synset ids are numbered 0 through S − 1; the id numbers will appear consecutively in the synset file.

  - List of hyponyms. The file hyponyms.txt contains the hyponym relationships: The first field is a synset id; subsequent fields are the id numbers of the synset's direct hyponyms. For example, the following line

        79537,38611,9007

    means that the the synset 79537 ("viceroy vicereine") has two hypernyms: 38611 ("exarch") and 9007 ("Khedive"), representing that exarchs and Khedives are both types of viceroys. The synsets are obtained from the corresponding lines in the file synsets.txt:

        79537,viceroy vicereine,governor of a country or province who rules...
        38611,exarch,a viceroy who governed a large province in the Roman Empire
        9007,Khedive,one of the Turkish viceroys who ruled Egypt between...

Tips on Developing the WordNet Class
--------

To see the API that you must follow for WordNet, see the [WordNet javadocs](javadocs/index.html?ngordnet/WordNet.html). Make sure to take advantage of the MethodSignatures file provided with the skeleton. **You may not add additional public or protected methods. You may add additional package protected or private methods as you please.**

See the p1/examples/GraphDemo.java file for an example of using the Digraph class. Since this example is part of the p1.examples package, you'll need to compile it frmo inside the p1/examples folder, and you'll need to run it with the command ```java p1.examples.GraphDemo```. See HW4 for more.

Almost all of the work is going to be in the constructor. Creating the WordNet class will include a non-trivial amount of design! You'll need to pick the right data structures. It is very easy to go down the wrong path while writing the constructor. You should not be afraid to scrap work if your original design does not work out.

It is OK to share design ideas with other students, though we suggest trying to pick your own data structures for a better learning experience.

When you're done, you'll be able to programatically ask for the hyponyms of a word. For example hyponyms("animal") should return a huge set including: ankylosaurus, dinosaur, tenpounder, porcupine, chinchilla, bufflehead, adjutant, blindworm, nightwalker, mutant, ... 

2: Intro to the NGrams Dataset
=====

The [Google Ngram dataset](http://storage.googleapis.com/books/ngrams/books/datasetsv2.html) provides ~3 terabytes of information about the historical frequencies of all observed words and phrases in English (or more precisely all observed [kgrams](http://en.wikipedia.org/wiki/N-gram)). Google provides the Google Ngram Viewer on the web, allowing users to visualize the relative historical popularity of words and phrases.

Our next task will be to allow for the visualization of this historical data, but it'll be a bit more involved. Ultimately, we'll combine this dataset with the WordNet dataset to be able to ask new and interesting questions that I don't think have ever been asked before this assignment was created (cool!). 

See the [project 1 slides](not yet available) for a top-down view of the NGramMap system.

3: TimeSeries
=====

There is no built-in type in Java that is convenient for representing a [time series](http://en.wikipedia.org/wiki/Time_series), so we're going to build our own. Your class should obey the [TimeSeries javadocs](javadocs/index.html?ngordnet/TimeSeries.html). As with everything in this assignment you should not create additional public or protected methods.

Warning: It is very easy to run into issues with generics. Compile frequently. Do not dare write more than one of these methods at a time.

4: YearlyRecord
=====

While the TimeSeries type will be handy for representing historical data, we will sometimes want to consider the data regarding all words for an entire year. See the [YearlyRecord javadocs](javadocs/index.html?ngordnet/YearlyRecord.html).

This one will be a bit more involved than TimeSeries. The rank, size, and count methods must all be very fast, no matter how many words are in the database. In other words, their runtime must be measurably independent of the number of entries in the YearlyRecord. That means no looping, recursion, or similar. You can achieve this through judicious use of the right data structures.

5: NGramMap (part 1)
=====

The [NGramMap (javadocs)](javadocs/index.html?ngordnet/NGramMap.html) type will provide a series of convenient methods for interacting with Google's NGrams dataset. 

An NGramMap will provide handy methods for looking up the TimeSeries for a given word or the YearlyRecord for a given year. For example, we might request the relative popularity of the word "fish" since the year 1850 until 1933. The full details are available in the Javadocs.

As with WordNet, most of the work will be in the constructor. Make sure to pick your data structures carefully. For this part of the assignment, you should do everything except the processedHistory method.

The NGram Input File Formats
------
As with the Wordnet file formats, the data comes in two different file types. The first are wfiles. Each line of a wfile provides tab separated information about the history of a particular word in English during a given year. 

    airport     2007    175702  32788
    airport     2008    173294  31271
    request     2005    646179  81592
    request     2006    677820  86967
    request     2007    697645  92342
    request     2008    795265  125775
    wandered    2005    83769   32682
    wandered    2006    87688   34647
    wandered    2007    108634  40101
    wandered    2008    171015  64395

The first entry in each row is the word. The second entry is the year. The third entry is the the number of times that the word appeared in any book that year. The fourth entry is the number of distinct sources that contain that word. Your program should ignore this fourth column. For example, from the text file above, we can observe that the word wandered appeared 171,015 times during the year 2008, and these appearances were spread across 64,395 distinct texts.

The other type of file is a tfile. Each line of a tfile provides comma separated information about the total corpus of data available for each calendar year. 

    1505,32059,231,1
    1507,49586,477,1
    1515,289011,2197,1
    1520,51783,223,1
    1524,287177,1275,1
    1525,3559,69,1

The first entry in each row is the year. The second is the total number of words recorded from all texts that year. The third number is the total number of pages of text from that year. The fourth is the total number of distinct sources from that year. Your program should ignore the third and fourth numbers. For example, we see that Google has exactly one text from the year 1505, and that it contains 32,059 words and 231 pages.

You may wonder why one file is tab separated and the other is comma separated. I didn't do it. Google did. Luckily it'll be easy to handle.


6: Plotter (part 1)
=====

The Plotter class will use a WordNet and/or NGramMap object to create plots of data. The [Plotter (javadocs)](javadocs/index.html?ngordnet/Plotter.html) type will provide methods for graphing data using the [XChart graphing library](http://xeiam.com/xchart/). We expect you to use the available documentation to figure out how to get the plots you want. We will not be grading these plots, but you're missing out if you don't write the methods that produce them. The code is all very straightforward. 

For this part, complete every method except plotProcessedHistory and plotZipfsLaw.

(possibly add our own home made examples)

7: NgrordnetUI
=====

Hard coded queries are no fun. In this part, you'll create a UI with the following commands:
 
    quit: program exits
    dates [start] [end]: resets the start and end years
    count [word] [year]: returns the count of word in the given year
    history [words...]: plots normalized counts of all words from start to end
    hypohist [word]: plots normalized count of all hyponyms of word from start to end  


8: WordLengthProcessor
=====

In this penultimate part of the assignment, you'll fill out the [WordLengthProcessor (javadocs)](javadocs/index.html?ngordnet/WordLengthProcessor.html). This should be fairly quick.

You'll then add the processedHistory methods in [NGramMap](javadocs/index.html?ngordnet/NGramMap.html). Now add the command below to NgordnetUI.

    wordlength: plots length of the average year from start to end


9: Zipf's Law
=====

As the last part of this project, we'll add the plotZipfsLaw method to [Plotter (javadocs)](javadocs/index.html?ngordnet/Plotter.html). 

Finally, add the follow command to NgornetUI:


    zipf year: plots the count of every word vs. its rank on a log log plot.


You should observe that the data is on a straight line. This is a surprising fact! For example, this means that the 4th most common word occurs 5 times as often as the 20th most popular word. S'weird.

You are done. Hoorah.
