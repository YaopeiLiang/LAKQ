package porter;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;

/**
 * Created by Peter on 2017/6/10 0010.
 */
public class QueryFilter {
    private static Analyzer analyzer=new QueryAnalyzer();


    public static String filterString(String text){
        StringBuilder result=new StringBuilder();
        TokenStream tokenStream=analyzer.tokenStream("content",text);
        CharTermAttribute term=tokenStream.addAttribute(CharTermAttribute.class);
        try{
            tokenStream.reset();
            while(tokenStream.incrementToken()){
                result.append(term.toString()+" ");
            }
            tokenStream.end();
            tokenStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return result.toString().trim();
    }
}
