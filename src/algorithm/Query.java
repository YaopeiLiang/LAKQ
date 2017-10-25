package algorithm;

import factory.FNode;
import factory.GraphFactory;
import factory.NNode;

import java.util.*;

/**
 * Created by lenovo on 2017/7/14.
 */
public class Query {

    public static Queue<QNode> baseline(double alpha,String query, PriorityQueue<QNode> Q){
        Map<String, FNode> query2DomainMap= GraphFactory.getQuery2DomainMap();
        Map<String,FNode> domain2QueryMap= GraphFactory.getDomain2QueryMap();
        FNode fNode=null;
        QNode top=null;

        double AINK=1,r_ink,a_ink;
        double esu=1e-5;
        if(Q==null){
            Q=new PriorityQueue<>(new ActiveNodeComparatot());
            fNode=query2DomainMap.get(query);
            if(fNode==null){
                return null;
            }
            QNode node=new QNode(1,0,1,query,fNode);
            Q.add(node);
        }

        PriorityQueue<QNode> C=new PriorityQueue<>(new RetainedNodeComparator());


        while(!Q.isEmpty() && Q.peek().getA_ink()>esu){
            top=Q.poll();

            if(C.size()>=6){
                List<QNode> list=new ArrayList<>();
                int i;
                for(i=0;i<4;++i){
                    list.add(C.poll());
                }
                QNode fifth=C.poll();
                QNode sixth=C.poll();
                if(fifth.getR_ink()>sixth.getR_ink()+AINK){
                    break;
                }
                C.add(sixth);
                C.add(fifth);
                for(i=0;i<list.size();++i){
                    C.add(list.get(i));
                }
            }

            double disratio=1;
            //top is a keyword query node
            if(top.getTag()==1){
                disratio=1-alpha;
                r_ink=top.getR_ink()+top.getA_ink()*alpha;
                top.setR_ink(r_ink);
                AINK=AINK-top.getA_ink()*alpha;
                if(C.contains(top)){
                    CRemoval(C,top);
                }
                C.add(top);
            }
            //与top链接的每一个点
            for(Map.Entry<String, NNode> v:top.getNode().getEdge().entrySet()){
                String cotent=v.getKey();
                NNode node=v.getValue();
                double weight=node.getWeight();
                a_ink=top.getA_ink()*disratio*weight;

                QNode qNode=null;
                FNode ffNode=null;
                int tag=-1;
                if(top.getTag()==1){
                    ffNode=domain2QueryMap.get(cotent);
                    tag=2;
                }else if(top.getTag()==2){
                    ffNode=query2DomainMap.get(cotent);
                    tag=1;
                }

                qNode=new QNode(a_ink,0,tag,cotent,ffNode);
                if(Q.contains(qNode)){
                    QRemoval(Q,qNode);
                }
                Q.add(qNode);
            }
        }
        //返回的是C
        return C;
    }

    public static void CRemoval(PriorityQueue<QNode> C,QNode top){
        Iterator<QNode> itor=C.iterator();
        while (itor.hasNext()){
            QNode node=itor.next();
            if(node.equals(top)){
                double r_ink=top.getR_ink()+node.getR_ink();
                top.setR_ink(r_ink);
                break;
            }
        }
        C.remove(top);
    }

    public static void QRemoval(PriorityQueue<QNode> Q,QNode qNode){
        Iterator<QNode> itor=Q.iterator();
        while (itor.hasNext()){
            QNode node=itor.next();
            if(node.equals(qNode)){
                double a_ink=qNode.getA_ink()+node.getA_ink();
                qNode.setR_ink(a_ink);
                break;
            }
        }
        Q.remove(qNode);
    }
}
