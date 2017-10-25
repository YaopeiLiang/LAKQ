package porter;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LowerCaseTokenizer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;


/**
 * Created by lenovo on 2017/6/14.
 */
public class QueryAnalyzer extends Analyzer{

   @Override
    protected TokenStreamComponents createComponents(String filedName){
       Tokenizer source=new LowerCaseTokenizer();
       TokenStream result=new StopFilter(source, StopAnalyzer.ENGLISH_STOP_WORDS_SET);
       result=new PorterStemFilter(result);
       return new TokenStreamComponents(source,result);
   }

    public static void main(String[] args) throws IOException{
        QueryAnalyzer analyzer=new QueryAnalyzer();
        String text="abc_cab the fucks boys.123 www.google.com ab in sh";
        TokenStream ts=analyzer.tokenStream("filed",text);
        CharTermAttribute term=ts.addAttribute(CharTermAttribute.class);

        StringBuilder builder=new StringBuilder();
        ts.reset();
        while(ts.incrementToken()){
            builder.append(term.toString()+" ");
        }
        ts.end();
        ts.close();
        System.out.println(builder);
    }
}
