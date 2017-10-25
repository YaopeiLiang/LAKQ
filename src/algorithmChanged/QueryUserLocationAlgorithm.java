package algorithmChanged;

import algorithm.QNode;
import algorithm.QueryLocation;
import algorithm.QueryUser;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by lenovo on 2017/9/13.
 */
public class QueryUserLocationAlgorithm {
    public static Queue<QNode> queryUserLocation(double alpha, double gama, String query, String user,
                                                 double latitude, double longitude){
        PriorityQueue<QNode> queue= QueryUserAlgotirhm.getInitUserQueue(alpha,gama,query,user);
        return QueryLocationAlgorithm.queryLocationAlgorithm(alpha,query,latitude,longitude,queue);
    }
}
