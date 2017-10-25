package algorithm;

import factory.TypeTypeFactory;
import metric.Pair;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.util.*;

/**
 * Created by lenovo on 2017/7/14.
 */
public class QueryTest {
    private static Map<String, Pair> queryTypeMap= TypeTypeFactory.getQueryTypeMap();

    public static void main(String[] args) {
        /*
        String query="guitar";
        String user="137248";
        double latitude=0.7;
        double longitude=0.7;
        double alpha=0.5;
        double gamma=0.2;
        Queue<QNode> queue;
        Scanner scanner=new Scanner(System.in);
        Pair pair;
        double beta=0.5;
        while (true){
            pair=queryTypeMap.get(query);
            System.out.println("=========Input Query:"+query+"\t"
                    +pair.getQuery()+"\t"+pair.getType()+"\t"+"=======================");
            queue=Query.baseline(alpha,query,null);
            output(queue,"Query");

            queue=QueryUser.queryUser(alpha,gamma,query,user);
            output(queue,"query+user");

            queue=QueryLocation.queryLocation(alpha,beta,query,latitude,longitude,null);
            output(queue,"query+Location");

            queue=QueryUserLocation.queryUserLocation(alpha,beta,gamma,query,user,latitude,longitude);
            output(queue,"query+user+location");

            System.out.println("input query");
            query=scanner.nextLine();
            System.out.println("input user");
            user=scanner.nextLine();
            System.out.println("input latitude");
            latitude=Double.parseDouble(scanner.nextLine());
            System.out.println("input longitude");
            longitude=Double.parseDouble(scanner.nextLine());


        }
        */
    }

    public static void output(Queue<QNode> queue,String type){
        int limit=5,i=0;
        String query;
        Pair pair;
        System.out.println("------------------"+type+"------------------------------------");
        while(!queue.isEmpty() && i<5){
            ++i;
            query=queue.poll().getContent();
            pair=queryTypeMap.get(query);
            System.out.println(query+"\t"+pair.getQuery()+"\t"+pair.getType());
        }
    }
}
