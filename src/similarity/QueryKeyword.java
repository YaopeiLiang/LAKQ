package similarity;

import algorithm.QNode;
import factory.TypeTypeFactory;
import metric.Pair;
import step0.Location;

import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Created by lenovo on 2017/9/8.
 */
public class QueryKeyword {

    //private static int queryLimit=10;
    private static Map<String, Pair> queryCategoryMap= TypeTypeFactory.getQueryTypeMap();

    public static void main(String[] args) {
        double gama=0.1;
        int queryLimit=10;
        calculateQueryKeyword(gama,queryLimit);
    }

    public static void calculateQueryKeyword(double gama,int queryLimit){
        List<String> queryList=NearbyDocumentNumber.getQueryList(queryLimit);
        double simpleQuery=0,userQuery=0.0,locQuery=0,userLocQuery=0;
        //测试的查询词个数
        int size=queryList.size();

        for(String query:queryList){
            /*
            Location location=NearbyDocumentNumber.getLocation(query);
            String user=NearbyDocumentNumber.getUser(query);

            Queue<QNode> queue=NearbyDocumentNumber.simpleQuery(query);
            simpleQuery+=getScore(queue,query);

            Queue<QNode> queue1=NearbyDocumentNumber.queryUser(query,user,gama);
            userQuery+=getScore(queue1,query);


            Queue<QNode> queue2=NearbyDocumentNumber.queryLocation(query,location);
            locQuery+=getScore(queue2,query);

            Queue<QNode> queue3=NearbyDocumentNumber.queryUserLocation(query,user,location,gama);
            userLocQuery+=getScore(queue3,query);
            */
        }
        System.out.println("gama="+gama);
        System.out.println("------Simple Query-------:"+simpleQuery/size);
        System.out.println("-------userQuery---------:"+userQuery/size);
        System.out.println("-------Loc Query---------:"+locQuery/size);
        System.out.println("-------userLocQuery------:"+userLocQuery/size);
    }

    //只取推荐查询词的第一个
    public static double getScore(Queue<QNode> queue,String query){
        //只取推荐查询词的第一个
        int limit=1;
        int i=0;
        double result=0.0;
        Pair queryPair=queryCategoryMap.get(query);
        String queryPart1=queryPair.getQuery();
        String queryPart2=queryPair.getType();
        while (i<limit&&!queue.isEmpty()){
            String content=queue.poll().getContent();
            if(content.equals(query)){

            }else{
                double temp=0;
                Pair pair=queryCategoryMap.get(content);
                //第一类别
                if(pair.getQuery().equals(queryPart1)){
                    temp=0.33;
                }
                //第二类别
                if(pair.getType().equals(queryPart2)){
                    temp=0.66;
                }
                result+=temp;
                i++;
            }
        }
        //return result/limit;
        return result;
    }
}
