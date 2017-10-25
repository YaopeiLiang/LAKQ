package algorithmChanged;

import algorithm.ActiveNodeComparatot;
import algorithm.QNode;
import algorithm.Query;
import algorithm.QueryUser;
import factory.FNode;
import factory.GraphFactory;
import factory.TypeTypeFactory;
import metric.Pair;

import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by lenovo on 2017/9/11.
 */
public class QueryUserAlgotirhm {
    public static Queue<QNode> queryUserAlgorithm(double alpha, double gamma,
                                         String query, String user){
        PriorityQueue<QNode> queue= getInitUserQueue(alpha,gamma,query,user);
        return QueryAlgorithn.baseline(alpha,query,queue);
    }

    public static PriorityQueue<QNode> getInitUserQueue(double alpha,double gamma,
                                                        String query,String user){
            PriorityQueue<QNode> Q=new PriorityQueue<>(new ActiveNodeComparatot());
            Map<String, FNode> query2DomainMap= GraphFactory.getQuery2DomainMap();
            Map<String,FNode> user2QueryMap= GraphFactory.getUserMap();
            Map<String, Pair> typeTypeMap= TypeTypeFactory.getQueryTypeMap();
            String queryType=typeTypeMap.get(query).getQuery();

            //将gama的墨水放入队列中
            FNode fNode1=query2DomainMap.get(query);
            QNode node1=new QNode(gamma,0,1,query,fNode1);
            Q.add(node1);

            FNode fNode=user2QueryMap.get(user);

            long size=fNode.getEdge().keySet().stream().
                    filter(pastQuery->{
                        String type=typeTypeMap.get(pastQuery).getQuery();
                        if(type.equals(queryType) && (!query.equals(pastQuery))){
                            return true;
                        }else {
                            return false;
                        }
                    }).count();

            double ave=(1-gamma)/(double)size;

            for(String pastQuery:fNode.getEdge().keySet()){
                double a_ink=ave;
                String  pastType=typeTypeMap.get(pastQuery).getQuery();
                //要搜索的关键词在搜索记录中
                if(pastQuery.equals(query)){
                    continue;
                }
                //类别相等
                if(pastType.equals(queryType)){
                    fNode=query2DomainMap.get(pastQuery);
                    QNode node=new QNode(a_ink,0,1,pastQuery,fNode);
                    Q.add(node);
                }

            }
            return Q;

    }
}
