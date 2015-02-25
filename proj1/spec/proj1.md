~ number: 1
~ title: NGordNet (Pre-Alpha)

**Warning: This spec is in alpha testing. It is intended for reader and GSI use only. While we cannot stop you if you have discovered this unlinked file, be aware that the spec is subject to arbitrarily gigantic revisions until Feb 25th.**

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

**While reading this spec, don't start coding until we tell you to. If you jump in early, you're likely to go down the wrong path. The spec should be your first source of information and you should consult this document before seeking outside help. **

1: The WordNet Class
=====

Your ultimate goal in building the WordNet class is to read input files and store them in ADTs of your choice that allow for rapid queries. Before you write the WordNet class, it'll be important that you understand the nature of the WordNet dataset and the structure of the files representing it. 

[WordNet](http://en.wikipedia.org/wiki/WordNet) is a semantic lexicon for the English language that is used extensively by computational linguists and cognitive scientists; for example, it was a key component in IBM's Watson. WordNet groups words into sets of synonyms called synsets and describes semantic relationships between them. One such relationship is the is-a relationship, which connects a hyponym (more specific synset) to a hypernym (more general synset). For example, "change" is a **hypernym** of "demotion", since "demotion" is-a (type of) "change". "change" is in turn a **hyponym** of "action", since "change" is-a (type of) "action". A visual depiction of some hyponym relationships in English is given below:

![WordNet](wordnet-fig1.png "WordNet")

Synsets consist of one or more words in English that all have the same meaning. For example, one synset is ["jump, parachuting"](http://wordnetweb.princeton.edu/perl/webwn?o2=&o0=1&o8=1&o1=1&o7=&o5=&o9=&o6=&o3=&o4=&s=jump&i=6&h=000010000000000000000000#c), which represents the act of descending to the ground with a parachute. "jump, parachuting" is a hyponym of "descent", since "jump, parachuting" is-a "descent". 

Words in English may belong to multiple synsets. This is just another way of saying words may have multiple meanings. For example, the word jump also belongs to the synset ["jump, leap"](http://wordnetweb.princeton.edu/perl/webwn?o2=&o0=1&o8=1&o1=1&o7=&o5=&o9=&o6=&o3=&o4=&s=jump&i=2&h=100000000000000000000000#c), which represents the more figurative notion of jumping (e.g. a jump in attendance). The hypernym of the synset "jump, leap" is "increase", since "jump, leap" is-an "increase".  Of course, there are other ways to "increase" something, for example, through "augmentation."

Synsets may include not just words, but also what are known as [collocations](http://en.wikipedia.org/wiki/Collocation). You can think of these as single words that occur next to each other so often that they are considered a single word, e.g. [nasal_decongestant](http://wordnetweb.princeton.edu/perl/webwn?s=nasal+decongestant+&sub=Search+WordNet&o2=&o0=1&o8=1&o1=1&o7=&o5=&o9=&o6=&o3=&o4=&h=). To avoid ambiguity, we will represent the constitutent words of collocations as being separated with an underscore \_ instead of the usual convention in English of separating them with spaces.For simplicity, we will refer to collocations as simply "words" throughout this document. 

A synset may be a hyponym of multiple synsets. Foe example, "actifed" is a hyponym of both "antihistamine" and "nasal\_decongestant", since it is both of these things.

If you're curious, you can browse the Wordnet database by [using the web interface](http://wordnetweb.princeton.edu/perl/webwn?o2=&o0=1&o8=1&o1=1&o7=&o5=&o9=&o6=&o3=&o4=&r=1&s=sturgeon&i=3&h=1000#c), though this is not necessary for this project.

The WordNet digraph 
-----------

To store the WordNet dataset, you'll need to use at least two data structures. One of these will be a data structure we haven't discussed in class yet called a Digraph (short for Directed Graph). 

A small subgraph of the WordNet Digraph is illustrated below. In our copy of the dataset (and in the graph below), words that belong to the same synset are separated by spaces (not commas as in the figure above), and collocations use underscores instead of spaces (e.g. "car_pool" instead of "car pool", same as the figure above). 

![WordNet](wordnet-fig2.png "WordNet")

A graph consists of a set of V vertices and E edges (drawn as arrows above) between vertices. For example, in the graph above, V = 25 and E = 24, and the synset "increase" points at "jump leap". 

Your first task in this assignment is to convert the provided synsets.txt and hypernyms.txt files into a graph. We'll be using a class from the ```edu.princeton.cs.algs4``` package called Digraph (which you can import with ```edu.princeton.cs.algs4.Digraph```. You can think of this class as having only a constructor and an addEdge method.

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

    means that the the synset 79537 ("viceroy vicereine") has two hyponyms: 38611 ("exarch") and 9007 ("Khedive"), representing that exarchs and Khedives are both types of viceroys. The synsets are obtained from the corresponding lines in the file synsets.txt:

        79537,viceroy vicereine,governor of a country or province who rules...
        38611,exarch,a viceroy who governed a large province in the Roman Empire
        9007,Khedive,one of the Turkish viceroys who ruled Egypt between...

Tips on Developing the WordNet Class
--------

Check out WordNetDemo.java in the demos folder. This provides examples of how the WordNet class should behave. Naturally it won't compile or run since you haven't written WordNet, but you should make sure to understand the expected behavior of this demo class before doing anything else.

Once you've checked out the demo, you'll want to pick the ADTs that you want to use to support the desired oeprations. This will be soemwhat similar to what you did in the week 6 discussion, namely using somewhat unfamiliar ADTs to solve a real world problem. It wouldn't be a bad idea to review the week 6 discussion worksheet before proceeding.

You're allowed to discuss designs with other students, but we request that you do not post exact instance variable declarations on Piazza (e.g. if you decide you want to use a List\<TreeSet\<Integer\>\>, please don't post this variable on Piazza). We want everyone to give this some real thought, though sharing ideas (or even instance variables) will not be considered plagiarism. As always, cite any help that you receive from others.

To see the exact API that you must follow for WordNet, see the [WordNet javadocs](javadocs/index.html?ngordnet/WordNet.html). Make sure to take advantage of the MethodSignatures file provided with the skeleton. **You may not add additional public or protected methods. You may add additional package protected or private methods as you please.**

See the p1/examples/GraphDemo.java file for an example of using the Digraph class. Since this example is part of the p1.examples package, you'll need to compile it from inside the p1/examples folder, and you'll need to run it with the command ```java p1.examples.GraphDemo```. See HW4 for more on compiling and running code that intearcts with packages.

Almost all of the work is going to be in the constructor. It is very easy to go down the wrong path while writing the constructor. You should not be afraid to scrap your work if your original design does not work out. Before you even begin writing your constructor, we suggest writing out what your ADTs should look like after reading in the files synsets11.txt and hyponyms11.txt. You might also consider what methods of these ADTs you'll need to call in order to support the isNoun() and hyponyms() methods.

Your initial tests should use the small data files `synsets11.txt` and `hyponyms11.txt`. Make sure your code works on these files before moving on to the full dataset, as the large files are hard to read through manually and take relatively long to load.

When you're done, you'll be able to programatically ask for the hyponyms of a word. For example, if you use `synsets.txt` and `hyponyms.txt`, hyponyms("animal") should return a huge set including: ankylosaurus, dinosaur, tenpounder, porcupine, chinchilla, bufflehead, adjutant, blindworm, nightwalker, mutant, ... 

2: Intro to the NGrams Dataset
=====

The [Google Ngram dataset](http://storage.googleapis.com/books/ngrams/books/datasetsv2.html) provides ~3 terabytes of information about the historical frequencies of all observed words and phrases in English (or more precisely all observed [kgrams](http://en.wikipedia.org/wiki/N-gram)). Google provides the Google Ngram Viewer on the web, allowing users to visualize the relative historical popularity of words and phrases.

Our next task will be to allow for the visualization of this historical data, but it'll be a bit more involved. Ultimately, we'll combine this dataset with the WordNet dataset to be able to ask new and interesting questions that I don't think have ever been asked before this assignment was created (cool!). 

See the [project 1 slides](not yet available) for a top-down view of the NGramMap system.

3: TimeSeries
=====

**Do not start this part of the project until you have completed HW5 and also have a good grasp of the methods developed in the Generics lecture. It is not a bad idea to do the [lecture 14 hardMode exercise](https://github.com/Berkeley-CS61B/lectureCode/tree/master/lec14) before beginning this part of the project.**

In HW5, we built some basic collections from scratch. Now we'll build a more sophisticated datatype known as a TimeSeries. A TimeSeries will be a special purpose extension of the existing TreeMap class where the key type parameter is always Integer, and the value type parameter is something that extends Number. Each key will correspond to a year, and each value a numerical data point for that year.

For example, the following code would create a TimeSeries<Double> and associate the number 3.6 with 1992 and 9.2 with 1993.

        TimeSeries<Double> ts = new TimeSeries<Double>();
        ts.put(1992, 3.6);
        ts.put(1993, 9.2);

The TimeSeries class will also provide additional utility methods:

 - years(): Returns all years as a Collection\<Number\>
 - data(): Returns all data as a Collection\<Number\>
 - plus(TimeSeries x): Returns the yearwise sum of x and this.
 - dividedBy(TimeSeries x): Returns the yearwise quotient of this and x.

See the MethodSignatures file provided in the skeleton for the exact class definition and signatures that you'll need to get started. See the TimeSeriesDemo class for a more thorough example of the behavior of the class. See the [TimeSeries javadocs](javadocs/index.html?ngordnet/TimeSeries.html) for a more detailed technical specification of your class.

As with everything in this assignment you should not create additional public or protected methods. 

Warning: It is very easy to run into issues with generics. Compile frequently. Do not dare write more than one of these methods at a time.

4: YearlyRecord
=====

**Before beginning this class, you should review problem 3a on the week 6 discussion worksheet. Make sure you know how to solve problem 3a before attemping to design your YearlyRecord implementation. **

While the TimeSeries type will be handy for representing historical data, we will sometimes want to consider the data regarding all words for an entire year. See the [YearlyRecord javadocs](javadocs/index.html?ngordnet/YearlyRecord.html).

For example, the following code would create a YearlyRecord and record that the word "quayside" appeared 95 times, the word "surrogate" appeared 340 times, and the word "merchantman" appeared 181 times. 

        YearlyRecord yr = new YearlyRecord();
        yr.put("quayside", 95);        
        yr.put("surrogate", 340);
        yr.put("merchantman", 181);    

The YearlyRecord class will also provide utility methods to make data analysis and plotting easier:

 - count(String word): Returns the count of word in this year.
 - size(): Returns the number of words recorded this year.
 - words(): Returns all words in ascending order of count.
 - counts(): Returns all words in ascending order of count.
 - rank(String word): Gives the rank of word, with 1 being the most popular word.

This one will be a bit more involved than TimeSeries. The rank, size, and count methods must all be very fast, no matter how many words are in the database. Specifically, their runtime must be measurably independent of the number of entries in the YearlyRecord. That means no looping, recursion, or similar. You can achieve this through judicious use of the right data structures.

**The basics autograder will cover up through this point in the project. Your project 1 bonus point will depend on how many AG tests you have completed by 3/6.**

5: NGramMap (part 1)
=====

The [NGramMap (javadocs)](javadocs/index.html?ngordnet/NGramMap.html) type will provide various convenient methods for interacting with Google's NGrams dataset. 

Most significantly, the NGramMap class will provides methods to look up aTimeSeries for a given word or the YearlyRecord for a given year. For example, we might request the relative popularity of the word "fish" since the year 1850 until 1933. 

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

You may wonder why one file is tab separated and the other is comma separated. I didn't do it, Google did. Luckily it'll be easy to handle.

6: Plotter (part 1)
=====

Note to testers: Everything below this point is pre-alpha. Nonetheless, let me know if you spot anything of note.

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
