package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {
    NGramMap m;
    public HistoryTextHandler(NGramMap map){
        m = map;
    }
    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        String response = "";
        for(String word: words){

            TimeSeries ts = m.countHistory(word, q.startYear(), q.endYear());
            response = response + word +": "+ ts.toString() +"\n";
        }
        return response;
    }
}
