package algorithm;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by lenovo on 2017/7/16.
 */
public class QueryUserLocation {
    public static Queue<QNode> queryUserLocation(double alpha,double beta,double esu,double gama,String query,String user,
                                                 double latitude,double longitude){
        PriorityQueue<QNode> queue=QueryUser.getInitUserQueue(alpha,gama,query,user);
        return QueryLocation.queryLocation(alpha,beta,esu,query,latitude,longitude,queue);
    }
}
