package ngordnet;
import java.util.Collection;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.NavigableSet;

public class TimeSeries<T extends Number> extends TreeMap<Integer, T> {   
    // private TreeMap<Integer, T> Timeseries;

    /** Constructs a new empty TimeSeries. */
    public TimeSeries() {
        super();
    }

    /** Returns the years in which this time series is valid. Doesn't really
      * need to be a NavigableSet. This is a private method and you don't have 
      * to implement it if you don't want to. */
    private NavigableSet<Integer> validYears(int startYear, int endYear) {
        return null;
    }

    /** Creates a copy of TS, but only between STARTYEAR and ENDYEAR. 
     * inclusive of both end points. */
    public TimeSeries(TimeSeries<T> ts, int startYear, int endYear) {

    }

    /** Creates a copy of TS. */
    public TimeSeries(TimeSeries<T> ts) {

    }

    /** Returns the quotient of this time series divided by the relevant value in ts.
      * If ts is missing a key in this time series, return an IllegalArgumentException. */
    public TimeSeries<Double> dividedBy(TimeSeries<? extends Number> ts) {
        TimeSeries<Double> divide = new TimeSeries<Double>();
        for (int year : this.keySet()) {
            Number thisVal = this.get(year);
            Number tsVal = ts.get(year);
            if (thisVal == null || tsVal == null || tsVal == 0) {
                throw new IllegalArgumentException();
            }
            divide.put(year, thisVal.doubleValue() / tsVal.doubleValue());
        }
        return divide;
    }

    /** Returns the sum of this time series with the given ts. The result is a 
      * a Double time series (for simplicity). */
    public TimeSeries<Double> plus(TimeSeries<? extends Number> ts) {
        TimeSeries<Double> sum = new TimeSeries<Double>();
        for (int year : this.keySet()) {
            Number thisVal = this.get(year);
            Number tsVal = ts.get(year);
            if (thisVal == null) {
                sum.put(year, tsVal.doubleValue());
            }
            else if (tsVal == null) {
                sum.put(year, thisVal.doubleValue());
            }
            else {
                sum.put(year, thisVal.doubleValue() + tsVal.doubleValue());
            }
            
        }
        return sum;
    }

    /** Returns all years for this time series (in any order). */
    public Collection<Number> years() {
        return new ArrayList<Number>(keySet());
    }

    /** Returns all data for this time series (in any order). */
    public Collection<Number> data() {
        return new ArrayList<Number>(values());
    }
    
}
