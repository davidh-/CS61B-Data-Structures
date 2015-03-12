package ngordnet;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import edu.princeton.cs.introcs.In;

public class NGramMap {
    private HashMap<String, HashSet<Integer>> wordAndYear;
    private HashMap<Integer, YearlyRecord> allYearlyRecords;
    private TimeSeries<Long> timeSeries;

    /** Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME. */
    public NGramMap(String wordsFilename, String countsFilename) {
        wordAndYear = new HashMap<String, HashSet<Integer>>();
        allYearlyRecords = new HashMap<Integer, YearlyRecord>();

        In inWords = new In(wordsFilename);
        while (inWords.hasNextLine()) {
            String[] curLineSplit = inWords.readLine().split("\\s+");
            String curWord = curLineSplit[0];
            int curYear = Integer.parseInt(curLineSplit[1]);
            int curCount = Integer.parseInt(curLineSplit[2]);

            HashSet<Integer> curHashSet;
            if (wordAndYear.containsKey(curWord)) {
                curHashSet = wordAndYear.get(curWord);
                curHashSet.add(curYear);
            } else {
                curHashSet = new HashSet<Integer>();
                curHashSet.add(curYear);
                wordAndYear.put(curWord, curHashSet);
            }

            YearlyRecord curYearlyRecord;
            if (allYearlyRecords.containsKey(curYear)) {
                curYearlyRecord = allYearlyRecords.get(curYear);
            } else {
                curYearlyRecord = new YearlyRecord();
            }
            curYearlyRecord.put(curWord, curCount);
            allYearlyRecords.put(curYear, curYearlyRecord);

        }

        timeSeries = new TimeSeries<Long>();
        In inCounts = new In(countsFilename);
        while (inCounts.hasNextLine()) {
            String[] curLineSplit = inCounts.readLine().split(",");
            Integer curYear = Integer.parseInt(curLineSplit[0]);
            Long curTotalNumWords = Long.parseLong(curLineSplit[1]);
            timeSeries.put(curYear, curTotalNumWords);
        }
    }
    /** Returns the absolute count of WORD in the given YEAR. If the word
      * did not appear in the given year, return 0. */
    public int countInYear(String word, int year) {
        return allYearlyRecords.get(year).count(word);
    }
    /** Returns a defensive copy of the YearlyRecord of YEAR. */
    public YearlyRecord getRecord(int year) {
        YearlyRecord record = allYearlyRecords.get(year);
        YearlyRecord defensiveCopy = new YearlyRecord();
        String[] words = record.words().toArray(new String[record.words().size()]);
        Integer[] counts = record.counts().toArray(new Integer[record.counts().size()]);
        for (int i = 0; i < words.length; i++) {
            defensiveCopy.put(words[i], record.count(words[i]));
        }
        return defensiveCopy;
    }
    /** Returns the total number of words recorded in all volumes. */
    public TimeSeries<Long> totalCountHistory() {
        return timeSeries;
    }
    /** Provides the history of WORD between STARTYEAR and ENDYEAR. */
    public TimeSeries<Integer> countHistory(String word, int startYear, int endYear) {
        HashSet<Integer> yearsOfWord = wordAndYear.get(word);
        TimeSeries<Integer> historyOfWord = new TimeSeries<Integer>();
        for (int year : yearsOfWord) {
            if (year >= startYear && year <= endYear || (startYear == 0 && endYear == 0)) {
                YearlyRecord curYearlyRecord = allYearlyRecords.get(year);
                int curCount = curYearlyRecord.count(word);
                historyOfWord.put(year, curCount);
            }
        }
        return historyOfWord;
    }
    /** Provides a defensive copy of the history of WORD. */
    public TimeSeries<Integer> countHistory(String word) {
        return countHistory(word, 0, 0);
    }
    /** Provides the relative frequency of WORD between STARTYEAR and ENDYEAR. */
    public TimeSeries<Double> weightHistory(String word, int startYear, int endYear) {
        TimeSeries<Integer> countHistory = countHistory(word, startYear, endYear);
        TimeSeries<Long> totalNumWords = new TimeSeries<Long>();
        for (Number yearNum : countHistory.years()) {
            int year = yearNum.intValue();
            if (year >= startYear && year <= endYear || (startYear == 0 && endYear == 0)) {
                totalNumWords.put(year, timeSeries.get(year));
            }
        }
        return countHistory.dividedBy(totalNumWords);
    }
    /** Provides the relative frequency of WORD. */
    public TimeSeries<Double> weightHistory(String word) {
        return weightHistory(word, 0, 0);
    }
    /** Provides the summed relative frequency of all WORDS between
      * STARTYEAR and ENDYEAR. */
    public TimeSeries<Double> summedWeightHistory(Collection<String> words, 
                              int startYear, int endYear) {
        TimeSeries<Double> sum = new TimeSeries<Double>();
        for (String word : words) {
            if (wordAndYear.containsKey(word)) {
                sum = sum.plus(weightHistory(word, startYear, endYear));
            }
        }
        return sum;
    }
    /** Returns the summed relative frequency of all WORDS. */
    public TimeSeries<Double> summedWeightHistory(Collection<String> words) {
        return summedWeightHistory(words, 0, 0);
    }
    /** Provides processed history of all words between STARTYEAR and ENDYEAR as processed
      * by YRP. */
    public TimeSeries<Double> processedHistory(int startYear, int endYear,
                                               YearlyRecordProcessor yrp) {
        TimeSeries<Double> processedHistory = new TimeSeries<Double>();
        for (int year : allYearlyRecords.keySet()) {
            if (year >= startYear && year <= endYear || (startYear == 0 && endYear == 0)) {
                processedHistory.put(year, yrp.process(allYearlyRecords.get(year)));
            }
        }
        return processedHistory;
    }
    /** Provides processed history of all words ever as processed by YRP. */
    public TimeSeries<Double> processedHistory(YearlyRecordProcessor yrp) {
        return processedHistory(0, 0, yrp);
    }
}
