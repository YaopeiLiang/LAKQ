package domainSimilarity.index;


import domainSimilarity.beans.Doc;
import domainSimilarity.index.MyAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.codecs.TermVectorsFormat;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import porter.QueryAnalyzer;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/7/20.
 */
public class URLSearcher {

    private static Sort sort=new Sort(
            new SortField("body", SortField.Type.SCORE)
    );

    public static void main(String[] args) {
        String field="body";
        int limit=76429;
        List<Doc> list=urlSearcher("car",10,field) ;
        System.out.println(list.size());
        //list.forEach(System.out::println);
    }

    //
    public static List<Doc> urlSearcher(String text,int limit,String field){

        String indexPath="D:\\LocKey\\AOL\\domain\\local\\newIndex";
        List<String> domainList=new ArrayList<>();
        List<Doc> result=new ArrayList<>();
        Analyzer analyzer=new EnglishAnalyzer();

        try{
            Directory directory= FSDirectory.open(Paths.get(indexPath));
            DirectoryReader directoryReader=DirectoryReader.open(directory);
            IndexSearcher indexSearcher=new IndexSearcher(directoryReader);

            indexSearcher.setSimilarity(new BM25Similarity());
            QueryParser parser=new QueryParser(field,analyzer);
            Query query=parser.parse(text);
            ScoreDoc[] hits=indexSearcher.search(query,limit,sort).scoreDocs;

            for(int i=0;i<hits.length && i<limit;++i){

                Document document=indexSearcher.doc(hits[i].doc);
                //System.out.println(document.get("body"));
                Doc doc=new Doc(document.get("url"),document.get("body"));
                result.add(doc);
               // System.out.println(doc.getUrl());
                /*
                System.out.println("doc:"+document.get("body"));
                String url=document.getField("url").toString();
                int index=url.indexOf("http");
                url=url.substring(index,url.length()-1);
                domainList.add(url);
                */
            }
            directoryReader.close();
            directory.close();
        }catch (IOException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
       // return domainList;
    }
}
