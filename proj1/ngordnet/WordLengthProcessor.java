package ngordnet;

public class WordLengthProcessor implements YearlyRecordProcessor {

    public double process(YearlyRecord yearlyRecord) {
        double totalCounts = 0;
        double totalWordsLen = 0;
        for (Number count : yearlyRecord.counts()) {
            totalCounts += count.doubleValue();
        }
        for (String word : yearlyRecord.words()) {
            totalWordsLen += word.length() * yearlyRecord.count(word);
        }
        return totalWordsLen / totalCounts;
    }
}
