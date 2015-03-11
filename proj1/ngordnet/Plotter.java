package ngordnet;
import com.xeiam.xchart.Chart;
import com.xeiam.xchart.QuickChart;
import com.xeiam.xchart.SwingWrapper;
import com.xeiam.xchart.StyleManager.ChartTheme;
import com.xeiam.xchart.ChartBuilder;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/** Utility class for generating plots. */
public class Plotter {
    /** Creates a plot of the TimeSeries TS. Labels the graph with the
      * given TITLE, XLABEL, YLABEL, and LEGEND. */
    public static void plotTS(TimeSeries<? extends Number> ts, String title, 
                              String xlabel, String ylabel, String legend) {
        Collection<Number> years = ts.years();
        Collection<Number> counts = ts.data();

        // Create Chart
        Chart chart = QuickChart.getChart(title, ylabel, xlabel, legend, years, counts);

        // Show it
        new SwingWrapper(chart).displayChart(); 
    }

    /** Creates a plot of the absolute word counts for WORD from STARTYEAR
      * to ENDYEAR, using NGM as a data source. */
    public static void plotCountHistory(NGramMap ngm, String word, 
                                      int startYear, int endYear) {
        TimeSeries<Integer> countHistory = ngm.countHistory(word, startYear, endYear);
        plotTS(countHistory, "Popularity", "year", "count", word);
    }

    /** Creates a plot of the normalized weight counts for WORD from STARTYEAR
      * to ENDYEAR, using NGM as a data source. */
    public static void plotWeightHistory(NGramMap ngm, String word, 
                                       int startYear, int endYear) {
        TimeSeries<Double> normalizedHistory = ngm.weightHistory(word, startYear, endYear);
        plotTS(normalizedHistory, "Popularity", "year", "count", word);
    }

    /** Creates a plot of the processed history from STARTYEAR to ENDYEAR, using
      * NGM as a data source, and the YRP as a yearly record processor. */
    public static void plotProcessedHistory(NGramMap ngm, int startYear, int endYear,
                                            YearlyRecordProcessor yrp) {
        TimeSeries<Double> processedHistory = ngm.processedHistory(startYear, endYear, yrp);
        plotTS(processedHistory, "Word Length", "year", "avg. length", "word length");
    }

    /** Creates a plot of the total normalized count of every word that is a hyponym
      * of CATEGORYLABEL from STARTYEAR to ENDYEAR using NGM and WN as data sources. */
    public static void plotCategoryWeights(NGramMap ngm, WordNet wn, String categoryLabel,
                                            int startYear, int endYear) {
        Collection<String> words = wn.hyponyms(categoryLabel);
        TimeSeries<Double> summedWeightHistory = ngm.summedWeightHistory(words, startYear, endYear);
        plotTS(summedWeightHistory, "Popularity", "year", "weight", categoryLabel);
    }

    /** Creates overlaid category weight plots for each category label in CATEGORYLABELS
      * from STARTYEAR to ENDYEAR using NGM and WN as data sources. */
    public static void plotCategoryWeights(NGramMap ngm, WordNet wn, String[] categoryLabels,
                                            int startYear, int endYear) {
        Chart chart = new ChartBuilder().width(800).height(600).xAxisTitle("years").yAxisTitle("data").build();
        for (String categoryLabel : categoryLabels) {
            Collection<String> words = wn.hyponyms(categoryLabel);
            TimeSeries<Double> curCategoryWeight = ngm.summedWeightHistory(words, startYear, endYear);
            chart.addSeries(categoryLabel, curCategoryWeight.years(), curCategoryWeight.data());
        }
        // Show it
        new SwingWrapper(chart).displayChart();
    }

    /** Makes a plot showing overlaid individual normalized count for every word in WORDS
      * from STARTYEAR to ENDYEAR using NGM as a data source. */
    public static void plotAllWords(NGramMap ngm, String[] words, int startYear, int endYear) {
        Chart chart = new ChartBuilder().width(800).height(600).xAxisTitle("years").yAxisTitle("data").build();
        for (String word : words) {
            TimeSeries<Double> curNormalizedCount = ngm.weightHistory(word, startYear, endYear);
            chart.addSeries(word, curNormalizedCount.years(), curNormalizedCount.data());
        }
        // Show it
        new SwingWrapper(chart).displayChart();
    }

    /** Returns the numbers from max to 1, inclusive in decreasing order. 
      * Private, so you don't have to implement if you don't want to. */
    private static Collection<Number> downRange(int max) {
        HashSet<Number> numbers = new HashSet<Number>();
        for (int i = max; i > 0 ; i--) {
            numbers.add(i);
        }
        return numbers;
    }

    /** Plots the normalized count of every word against the rank of every word on a
      * log-log plot. Uses data from YEAR, using NGM as a data source. */
    public static void plotZipfsLaw(NGramMap ngm, int year) {
        YearlyRecord yearlyRecord = ngm.getRecord(year);
        Collection<Number> counts = yearlyRecord.counts();
        Collection<Number> ranks = downRange(counts.size());

        Chart chart = new ChartBuilder().width(800).height(600).xAxisTitle("rank").yAxisTitle("count").build();
        chart.getStyleManager().setYAxisLogarithmic(true);
        chart.getStyleManager().setXAxisLogarithmic(true);

        chart.addSeries("zipdf", ranks, counts);

        // Show it
        new SwingWrapper(chart).displayChart();
    }
}
