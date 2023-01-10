package ngordnet.ngrams;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/** An object that provides utility methods for making queries on the
 *  Google NGrams dataset (or a subset thereof).
 *
 *  An NGramMap stores pertinent data from a "words file" and a "counts
 *  file". It is not a map in the strict sense, but it does provide additional
 *  functionality.
 *
 *  @author Josh Hug
 */
public class NGramMap {
    /** Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME. */
    HashMap<String, TimeSeries> wordsMap;
    TimeSeries countsMap;
    public NGramMap(String wordsFilename, String countsFilename) throws FileNotFoundException {
        // pass the path to the file as a parameter
        File wordsFile = new File(wordsFilename);
        Scanner sc = new Scanner(wordsFile);

        wordsMap = new HashMap<>();
        while (sc.hasNextLine()) {
            String s =  sc.nextLine();
            String[] arr = s.split("\t");
            TimeSeries ts;
            if(wordsMap.containsKey(arr[0])){
                ts = wordsMap.get(arr[0]);
            }
            else{
                ts = new TimeSeries();

            }
            ts.put( Integer.parseInt(arr[1]), Double.parseDouble(arr[2]));
            wordsMap.put(arr[0], ts);
        }
        File countsFile = new File(countsFilename);
        sc = new Scanner(countsFile);
        countsMap = new TimeSeries();
        while (sc.hasNextLine()) {
            String s =  sc.nextLine();
            String[] arr = s.split(",");
            countsMap.put(Integer.parseInt(arr[0]), Double.parseDouble(arr[1]));
        }


    }

    /** Provides the history of WORD. The returned TimeSeries should be a copy,
     *  not a link to this NGramMap's TimeSeries. In other words, changes made
     *  to the object returned by this function should not also affect the
     *  NGramMap. This is also known as a "defensive copy". */
    public TimeSeries countHistory(String word) {
        if(wordsMap.containsKey(word)){
            TimeSeries ts = wordsMap.get(word);
            TimeSeries hist = new TimeSeries();
            hist.putAll(ts);
            return hist;
        }
        return null;
    }

    /** Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     *  returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other words,
     *  changes made to the object returned by this function should not also affect the
     *  NGramMap. This is also known as a "defensive copy". */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        if(wordsMap.containsKey(word)){
            TimeSeries ts = wordsMap.get(word);
            return new TimeSeries(ts, startYear, endYear);
        }
        return null;
    }

    /** Returns a defensive copy of the total number of words recorded per year in all volumes. */
    public TimeSeries totalCountHistory() {
        TimeSeries ts = new TimeSeries();
        ts.putAll(countsMap);
        return ts;
    }

    /** Provides a TimeSeries containing the relative frequency per year of WORD compared to
     *  all words recorded in that year. */
    public TimeSeries weightHistory(String word) {
          return wordsMap.get(word).dividedBy(countsMap);
    }

    /** Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     *  and ENDYEAR, inclusive of both ends. */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries ts = new TimeSeries(wordsMap.get(word), startYear, endYear);
        return ts.dividedBy(countsMap);
    }

    /** Returns the summed relative frequency per year of all words in WORDS. */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries ts =  new TimeSeries();
        for(String word: words){
            ts = ts.plus(wordsMap.get(word));
        }
        return ts.dividedBy(countsMap);
    }

    /** Provides the summed relative frequency per year of all words in WORDS
     *  between STARTYEAR and ENDYEAR, inclusive of both ends. If a word does not exist in
     *  this time frame, ignore it rather than throwing an exception. */
    public TimeSeries summedWeightHistory(Collection<String> words, int startYear, int endYear) {
        TimeSeries ts =  new TimeSeries();
        for(String word: words){
            TimeSeries w = new TimeSeries(wordsMap.get(word), startYear, endYear);
            ts = ts.plus(w);
        }
        return ts.dividedBy(countsMap);

    }

    public static void main(String[] args) throws FileNotFoundException {
        NGramMap n = new NGramMap("data/ngrams/very_short.csv", "data/ngrams/total_counts.csv");
        List<String> words = new ArrayList<>();
        words.add("request");
        words.add("airport");
        TimeSeries hist = n.summedWeightHistory(words);
        hist.years();
    }

}
