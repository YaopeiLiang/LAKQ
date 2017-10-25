package similarity;

import algorithm.QNode;
import domainSimilarity.beans.Doc;
import domainSimilarity.index.URLSearcher;
import factory.DomainLocationFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.queries.mlt.MoreLikeThis;
import org.apache.lucene.queries.mlt.MoreLikeThisQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.CharsRefBuilder;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import step0.Location;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by lenovo on 2017/8/3.
 */
public class NearbyDocumentSimilarity {
    private static Map<String, Location> urlLocationMap= DomainLocationFactory.getMap();

    private static int limit=76429;

    private static String field="body";
    private static int docLimit=10;
    //private static int retriveLimit=5;
    //计算的分母
    private static double denominator=calculateCommonDenominator();

    //只取推荐查询的第一次
    //计算文档相似度
    public static double calSimilarity(Queue<QNode> queue,
                                       String query,List<String> queryDocList,String type,
                                       double latitude,double longitude){
        int i=0;
        double sumcos=0,cos;
        int retriveLimit=1;
        while (i<retriveLimit && !queue.isEmpty()){
            String suggestQuery=queue.poll().getContent();
            //返回相同的query
            if(suggestQuery.equals(query)){
                continue;
            }else {
                List<Doc> docList= URLSearcher.urlSearcher(suggestQuery,limit,field);

                //去掉欧氏距离大于0.1的点
                List<Doc> filterList=docList.stream().filter(doc->{
                    Location location=urlLocationMap.get(doc.getUrl());
                    double lon=location.getLongitude()-longitude;
                    double lat=location.getLatitude()-latitude;
                    double dist=Math.sqrt(lon*lon+lat*lat);
                    if(dist>0.1){
                        return false;
                    }else{
                        return true;
                    }
                }).collect(Collectors.toList());

                List<String> suggestDocList=filterList.stream().map(doc->doc.getBody()).collect(Collectors.toList());

                cos=calListSim(suggestDocList,queryDocList);
                //System.out.println(cos+"---"+query+"----"+type);
                sumcos+=cos;
                //跳出循环
                break;
            }
        }
        //double value=sumcos/i;
        //System.out.println(value+"---"+query+"----"+type+"------,miss:"+miss);
        return sumcos;
    }

    private static double calListSim(List<String> documentList1,List<String> documentList2){
        double cos=0,sim;
        int i,size1=documentList1.size(),size2=documentList2.size();
       // System.out.println("doc1 size:"+documentList1.size()+",doc2 size;"+documentList2.size());
        for(i=0;i<docLimit&&i<size1&&i<size2;++i){
            String doc1=documentList1.get(i);
            String doc2=documentList2.get(i);
            //计算文档相似度
            sim=getDocCos(doc1,doc2);
            if(i==0){
                cos+=sim;
            }else{
                sim=sim/Math.log10(i+1);
                cos+=sim;
            }
        }

        return cos/denominator;
    }

    private static double getDocCos(String docment1,String docment2){
        String[] doc1=docment1.split(" ");
        String[] doc2=docment2.split(" ");

        Map<String,Double> docFreq1=new HashMap<>();
        Map<String,Double> docFreq2=new HashMap<>();

        putIntoMap(doc1,docFreq1);
        putIntoMap(doc2,docFreq2);

        double sim=calDocSimilarity(docFreq1,docFreq2);
        double fen1=getDocFreq(docFreq1);
        double fen2=getDocFreq(docFreq2);
        sim=sim/(fen1*fen2);
        return sim;
    }

    public static double calculateCommonDenominator(){
        double denominator=1.0;
        for(double i=2;i<=10;++i){
            denominator+=(1/Math.log10(i));
        }
        return denominator;
    }


    public static double getDocFreq(Map<String,Double> map){
        double sum=0.0;
        for(Map.Entry<String,Double> item:map.entrySet()){
            double value=item.getValue();
            sum+=Math.pow(value,2);
        }
        return Math.sqrt(sum);
    }


    private static double calDocSimilarity(Map<String,Double> map1,Map<String,Double> map2){
        double sim=0.0;
        for(Map.Entry<String,Double> item:map1.entrySet()){
            String key=item.getKey();
            Double value=item.getValue();
            Double value2=map2.get(key);

            if(value2!=null){
                sim+=value*value2;
            }
        }
        return sim;
    }

    private static void putIntoMap(String[] doc,Map<String,Double> map){
        for(String s:doc){
            Double freq=map.get(s);
            if(freq==null){
                map.put(s,1.0);
            }else{
                freq+=1;
               map.put(s,freq);
            }
        }
    }
}
