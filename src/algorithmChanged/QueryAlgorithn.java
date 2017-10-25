package algorithmChanged;

import algorithm.ActiveNodeComparatot;
import algorithm.QNode;
import algorithm.RetainedNodeComparator;
import factory.FNode;
import factory.GraphFactory;
import factory.NNode;

import java.util.*;

/**
 * Created by lenovo on 2017/9/11.
 */
public class QueryAlgorithn {
    public static Queue<QNode> baseline(double alpha, String query, PriorityQueue<QNode> Q){
        Map<String, FNode> query2DomainMap= GraphFactory.getQuery2DomainMap();
        Map<String,FNode> domain2QueryMap= GraphFactory.getDomain2QueryMap();
        Map<String,FNode> query2UserMap=GraphFactory.getQuery2UserMap();
        Map<String,FNode> user2QueryMap=GraphFactory.getUser2QueryMap();

        FNode fNode=null;
        QNode top=null;

        double AINK=1,r_ink,a_ink;
        double esu=1e-5;
        if(Q==null){
            Q=new PriorityQueue<>(new ActiveNodeComparatot());
            fNode=query2DomainMap.get(query).clone();
            FNode fNode1=query2UserMap.get(query).clone();
            fNode.addFNode(fNode1);
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

            if(top.getTag()==1){

                a_ink=top.getA_ink()/2;


                for(Map.Entry<String,NNode> item:top.getNode().getEdge().entrySet()){
                    String content=item.getKey();

                    double weight=item.getValue().getWeight();
                    double ink=a_ink*disratio*weight;

                    //包含用户
                    if(user2QueryMap.keySet().contains(content)){

                        FNode fNode1=user2QueryMap.get(content);

                        QNode qNode=new QNode(ink,0,3,content,fNode1);
                        if(Q.contains(qNode)){
                            QRemoval(Q,qNode);
                        }
                        Q.add(qNode);
                    }
                    //包含URL
                    if(domain2QueryMap.keySet().contains(content)){
                        FNode fNode1=domain2QueryMap.get(content);
                        QNode qNode=new QNode(ink,0,2,content,fNode1);
                        if(Q.contains(qNode)){
                            QRemoval(Q,qNode);
                        }
                        Q.add(qNode);
                    }

                }
            }else if(top.getTag()==2 || top.getTag()==3){
                for(Map.Entry<String,NNode> item:top.getNode().getEdge().entrySet()){
                    int tag=top.getTag();
                    String content=item.getKey();
                    double weight=item.getValue().getWeight();
                    a_ink=top.getA_ink()*disratio*weight;
                    FNode fNode1;
                    if(tag==2){
                       fNode1=query2DomainMap.get(content);
                    }else{
                        fNode1=query2UserMap.get(content);
                    }
                    QNode qNode=new QNode(a_ink,0,1,content,fNode1);
                    if(Q.contains(qNode)){
                        QRemoval(Q,qNode);
                    }
                    Q.add(qNode);
                }
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
