package ngordnet.ngrams;

import edu.princeton.cs.algs4.In;

import java.util.*;

/** An object for mapping a year number (e.g. 1996) to numerical data. Provides
 *  utility methods useful for data analysis.
 *  @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {
    /** Constructs a new empty TimeSeries. */
    public TimeSeries() {
        super();

    }

    /** Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     *  inclusive of both end points. */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        SortedMap map = ts.subMap(startYear,true, endYear, true);
        putAll(map);
    }

    /** Returns all years for this TimeSeries (in any order). */
    public List<Integer> years() {
         return new ArrayList<>(keySet());
    }

    /** Returns all data for this TimeSeries (in any order).
     *  Must be in the same order as years(). */
    public List<Double> data() {
        return new ArrayList<>(values());
    }

    /** Returns the yearwise sum of this TimeSeries with the given TS. In other words, for
     *  each year, sum the data from this TimeSeries with the data from TS. Should return a
     *  new TimeSeries (does not modify this TimeSeries). */
    public TimeSeries plus(TimeSeries ts) {
        List < Map < Integer, Double > > listOfMaps = List.of( this , ts );
        TimeSeries mergedMap = new TimeSeries();
        listOfMaps.forEach(
                map -> map.forEach( ( k , v ) -> mergedMap.merge( k , v , Double :: sum ) )
        );
        return mergedMap;
    }

     /** Returns the quotient of the value for each year this TimeSeries divided by the
      *  value for the same year in TS. If TS is missing a year that exists in this TimeSeries,
      *  throw an IllegalArgumentException. If TS has a year that is not in this TimeSeries, ignore it.
      *  Should return a new TimeSeries (does not modify this TimeSeries). */
     public TimeSeries dividedBy(TimeSeries ts) {
         TimeSeries divided = new TimeSeries();
         for(Map.Entry<Integer, Double> entry: this.entrySet()){
             Integer key = entry.getKey();
             Double value = entry.getValue();
             if(!this.containsKey(key)){
                 throw new IllegalArgumentException();
             }
             value = value/ts.get(key);
             divided.put(key, value);
         }
        return divided;
    }
}
