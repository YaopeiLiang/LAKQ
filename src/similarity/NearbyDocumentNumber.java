package similarity;

import algorithm.*;
import algorithmChanged.QueryAlgorithn;
import algorithmChanged.QueryLocationAlgorithm;
import algorithmChanged.QueryUserAlgotirhm;
import algorithmChanged.QueryUserLocationAlgorithm;
import domainSimilarity.beans.Doc;
import domainSimilarity.index.URLSearcher;
import factory.DomainLocationFactory;
import factory.FNode;
import factory.GraphFactory;
import step0.Location;

import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * Created by lenovo on 2017/8/2.
 */
public class NearbyDocumentNumber {
    private static Map<String, Location> urlLocationMap= DomainLocationFactory.getMap();
    private static Map<String, FNode> query2UrlMap=GraphFactory.getQuery2DomainMap();
    private static Map<String,FNode> query2UserMap=GraphFactory.getQuery2UserMap();
    private static int limit=76429;
   // private static int queryLimit=100;
    private static String field="body";
    private static double alpha=0.5;


    public static void main(String[] args) {
        double gama=0.5;
        int queryLimit=5;
        //calDocumentSimilarity(gama,queryLimit);
        calNumber(gama,queryLimit);
    }

    public static void calDocumentSimilarity(double gama,int queryLimit){
        /*
        double alpha=0.5;
        double beta=0.5;
        //随机获得query列表
        List<String> queryList=getQueryList(queryLimit);
        double simpleQuery=0,userQuery=0.0,locQuery=0,userLocQuery=0;
        int size=queryList.size();
        for(String query:queryList){
            Location location=getLocation(query);
            double latitude=location.getLatitude();
            double longitude=location.getLongitude();

            String user=getUser(query);
            List<String> queryDocList=getQueryDocList(query);

            Queue<QNode> queue=simpleQuery(query,alpha);
            simpleQuery+=NearbyDocumentSimilarity.calSimilarity(queue,query,queryDocList,"simple Query",
                        latitude,longitude);

            Queue<QNode> queue1=queryUser(query,user,gama,alpha,beta);
            userQuery+=NearbyDocumentSimilarity.calSimilarity(queue1,query,queryDocList,"User Query",
                    latitude,longitude);


            Queue<QNode> queue2=queryLocation(query,location,alpha,beta);
            locQuery+=NearbyDocumentSimilarity.calSimilarity(queue2,query,queryDocList,"Location Query",
                    latitude,longitude);

            Queue<QNode> queue3=queryUserLocation(query,user,location,gama,alpha,beta);
            userLocQuery+=NearbyDocumentSimilarity.calSimilarity(queue3,query,queryDocList,
                    "User Location Query", latitude,longitude);
        }
        System.out.println("gama="+gama);
        System.out.println("In sum,Simple Query:"+simpleQuery/size);
        System.out.println("In sum,User Query:"+userQuery/size);
        System.out.println("In sum,Location Query:"+locQuery/size);
        System.out.println("In sum,userLocQuery Query:"+userLocQuery/size);
        */
    }


    //计算Query个数
    public static void calNumber(double gama,int queryLimit){
        double alpha=0.5;
        double beta=0.5;
        String path="D:\\LocKey\\AOL\\queryList.txt";
        //随机获得query列表
        List<String> queryList=getQueryList(queryLimit);
        List<QueryUserLocationDecorator> list=Lazy.loadQueryList(path).stream().limit(queryLimit).collect(toList());
        double simpleQuery=0,userQuery=0.0,locQuery=0,userLocQuery=0;
        double simpleQueryAlg=0.0,userQueryAlg=0;
        System.out.println(list.size());
        //测试的查询词个数
        int size=queryList.size();
        for(QueryUserLocationDecorator item:list){
       // for(String query:queryList){
            //Location location=getLocation(query);
            //String user=getUser(query);

            Location location=item.getLocation();
            String query=item.getQuery();
            String user=item.getUser();
            Queue<QNode> queue=simpleQuery(query,alpha);
            //System.out.println("queue:"+queue.size());
            simpleQuery+=show(queue,query,"simple Query",location);

            Queue<QNode> queueAlg=simpleQueryAlgorithn(query);
            simpleQueryAlg+=show(queueAlg,query,"Simple Query ALg",location);

            Queue<QNode> queue1=queryUser(query,user,gama,alpha,beta);
            userQuery+=show(queue1,query,"User Query",location);


            Queue<QNode> queue1Alg=queryUserAlgorithm(query,user,gama);
            userQueryAlg+=show(queue1Alg,query,"User Query ALG",location);
            /*
            Queue<QNode> queue2=queryLocation(query,location);
            locQuery+=show(queue2,query,"Location Query",location);

            Queue<QNode> queue3=queryUserLocation(query,user,location,gama);
            userLocQuery+=show(queue3,query,"User Location Query",location);
            */
        }
        System.out.println("gama="+gama);
        System.out.println("------Simple Query-------:"+simpleQuery/size);
        System.out.println("------Simple Query alg-------:"+simpleQueryAlg/size);
        System.out.println("-------userQuery---------:"+userQuery/size);
        System.out.println("-------userQuery---------:"+userQueryAlg/size);
        System.out.println("-------Loc Query---------:"+locQuery/size);
        System.out.println("-------userLocQuery------:"+userLocQuery/size);
    }


    public static List<String> getQueryDocList(String query){

        List<Doc> docList=URLSearcher.urlSearcher(query,limit,field);
        List<String> result=docList.stream().map(doc->doc.getBody()).collect(toList());
        return result;
    }

    public static List<String> getQueryList(int limit){
        List<String> queryList=query2UrlMap.keySet().stream().collect(toList());
        List<String> result=new ArrayList<>();
        Random random=new Random();
        int size=queryList.size();
        for(int i=0;i<limit;++i){
            int index=random.nextInt(size);
            result.add(queryList.get(index));
        }
        return result;
    }

    public static double show(Queue<QNode> queue,String query,String type,Location location){
        int time=0;
        long sum=0;
        long once;

        while (time<5 && !queue.isEmpty()){
            String suggestQuery=queue.poll().getContent();
            if(suggestQuery.equals(query)){

            }else{
                once=getNumber(suggestQuery,location.getLatitude(),location.getLongitude());
                sum+=once;
                //System.out.println(type+",once="+once);
                time++;
            }
        }
        //double ave=(double)sum/time;
        double ave=(double)sum/5;//
       // System.out.println(ave+"----"+query+"---"+type);
        return ave;
    }


    //获得与查询位置的欧式距离小于0.1的文档数量
    public static long getNumber(String query,double latitude,double longitude){

        List<Doc> docList=URLSearcher.urlSearcher(query,limit,field);

        List<Location> locations=docList.stream().map(doc->{
            return urlLocationMap.get(doc.getUrl());
        }).collect(toList());



       long count=locations.stream().filter(location -> {
            double lon=location.getLongitude()-longitude;
            double lat=location.getLatitude()-latitude;
            double dist=Math.sqrt(lon*lon+lat*lat);
            if(dist>0.1){
                return false;
            }else{
                return true;
            }
        }).count();
       return count;
    }

    public static Location getLocation(String query){
        FNode node=query2UrlMap.get(query);
        List<String> urlList=node.getEdge().keySet().stream().collect(toList());
        Random random=new Random();
        int rand=random.nextInt(urlList.size());
        String url=urlList.get(rand);
        return urlLocationMap.get(url);
    }

    public static String getUser(String query){
        FNode node=query2UserMap.get(query);
        List<String> userList=node.getEdge().keySet().stream().collect(toList());
        Random random=new Random();
        int rand=random.nextInt(userList.size());
        return userList.get(rand);
    }

    public static Queue<QNode> simpleQuery(String query,double alpha){
        return Query.baseline(alpha,query,null);
    }

    public static Queue<QNode> simpleQueryAlgorithn(String query){
        return QueryAlgorithn.baseline(alpha,query,null);
    }

    public static Queue<QNode> queryUser(String query,String user,double gama,double alpha,
                                double beta){
        return QueryUser.queryUser(alpha,gama,query,user);
    }

    public static Queue<QNode> queryUserAlgorithm(String query,String user,double gama){
        return QueryUserAlgotirhm.queryUserAlgorithm(alpha,gama,query,user);
    }

    public static Queue<QNode> queryLocation(String query,Location location,double alpha,
                                             double beta,double esu){
        return QueryLocation.queryLocation(alpha,beta,esu,query,location.getLatitude(),
                location.getLongitude(),null);
    }

    public static Queue<QNode> queryLocationAlgorithm(String query,Location location){
        return QueryLocationAlgorithm.queryLocationAlgorithm(alpha,query,location.getLatitude(),
                location.getLongitude(),null);
    }

    public static Queue<QNode> queryUserLocation(String query,String user,Location location,double gama,
                                                 double alpha,double beta,double esu){
        return QueryUserLocation.queryUserLocation(alpha,beta,esu,gama,query,user,
                location.getLatitude(),location.getLongitude());
    }

    public static Queue<QNode> queryUserLocationAlgorithm(String query,String user,Location location,double gama){
        return QueryUserLocationAlgorithm.queryUserLocation(alpha,gama,query,user,location.getLatitude(),
                location.getLongitude());
    }
}
