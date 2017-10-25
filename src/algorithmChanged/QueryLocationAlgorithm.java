package algorithmChanged;

import algorithm.ActiveNodeComparatot;
import algorithm.QNode;
import algorithm.Query;
import algorithm.RetainedNodeComparator;
import factory.DomainLocationFactory;
import factory.FNode;
import factory.GraphFactory;
import factory.NNode;
import step0.Location;

import java.awt.peer.FramePeer;
import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * Created by lenovo on 2017/9/11.
 */
public class QueryLocationAlgorithm {


    public static Queue<QNode> queryLocationAlgorithm(double alpha, String query,
                                             double latitude, double longitude, PriorityQueue<QNode> Q){
        Map<String, FNode> query2DomainMap= GraphFactory.getQuery2DomainMap();
        Map<String,FNode> domain2QueryMap= GraphFactory.getDomain2QueryMap();
        Map<String,FNode> query2UserMap=GraphFactory.getQuery2UserMap();
        Map<String,FNode> user2QueryMap= GraphFactory.getUser2QueryMap();

        Map<String, Location> domainLocation= DomainLocationFactory.getMap();
        Map<String,Double> cacehd_min_dist_domain2Query=new HashMap<>();

        double AINK=1,r_ink,a_ink;
        double esu=1e-5;
        double beta=0.5;
        QNode top;

        if(Q==null){
            Q=new PriorityQueue<>(new ActiveNodeComparatot());

            FNode node=query2DomainMap.get(query).clone();
            FNode fNode1=query2UserMap.get(query).clone();
            node.addFNode(fNode1);
            //System.out.println(node.getEdge().keySet());
            QNode qNode=new QNode(1,0,1,query,node);
            Q.add(qNode);
        }

        PriorityQueue<QNode> C=new PriorityQueue<>(new RetainedNodeComparator());

        while (!Q.isEmpty() && Q.peek().getA_ink()>esu){
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
                    Query.CRemoval(C,top);
                }
                C.add(top);
            }

            //query节点
            if(top.getTag()==1){

                a_ink=top.getA_ink()/2;

                for(Map.Entry<String, NNode> node:top.getNode().getEdge().entrySet()){

                    String content=node.getKey();
                    double weight=node.getValue().getWeight();
                    if(domain2QueryMap.containsKey(content)){

                        double distance=getDistanceFromQuery2Domain(top,latitude,longitude,content,domainLocation);
                        weight=beta*weight+(1-beta)*distance;
                        a_ink=a_ink*disratio*weight;
                        FNode fNode=domain2QueryMap.get(content);

                        QNode qNode=new QNode(a_ink,0,2,content,fNode);
                        if(Q.contains(qNode)){
                            Query.QRemoval(Q,qNode);
                        }
                        Q.add(qNode);
                    }
                    if(user2QueryMap.keySet().contains(content)){

                        a_ink=top.getA_ink()*disratio*weight;
                        FNode fNode=user2QueryMap.get(content);
                        QNode qNode=new QNode(a_ink,0,3,content,fNode);
                        if(Q.contains(qNode)){
                            Query.QRemoval(Q,qNode);
                        }
                        Q.add(qNode);
                    }
                }
                //domain节点
            }else if(top.getTag()==2){
                for(Map.Entry<String,NNode> queryNode:top.getNode().getEdge().entrySet()){

                    String content=queryNode.getKey();
                    double weight=queryNode.getValue().getWeight();
                    double minDist;
                    if(cacehd_min_dist_domain2Query.containsKey(content)){
                        minDist=cacehd_min_dist_domain2Query.get(content);
                    }else{
                        minDist=getMinDistanceFromDomian2Query(content,latitude,longitude,
                                domainLocation,query2DomainMap);
                        cacehd_min_dist_domain2Query.put(content,minDist);
                    }
                    weight=beta*weight+(1-beta)*minDist;
                    a_ink=top.getA_ink()*disratio*weight;
                    FNode fNode=null;
                    fNode=query2DomainMap.get(content);

                    QNode qNode=new QNode(a_ink,0,1,content,fNode);
                    if(Q.contains(qNode)){
                        Query.QRemoval(Q,qNode);
                    }
                    Q.add(qNode);
                }
            }else if(top.getTag()==3){
                for(Map.Entry<String,NNode> node:top.getNode().getEdge().entrySet()){
                        String content=node.getKey();
                        double weight=node.getValue().getWeight();
                        a_ink=top.getA_ink()*disratio*weight;
                        FNode fNode;
                        fNode=query2UserMap.get(content);

                        QNode qNode=new QNode(a_ink,0,1,content,fNode);
                        if(Q.contains(qNode)){
                            Query.QRemoval(Q,qNode);
                        }
                        Q.add(qNode);
                }
            }
        }
        return C;
    }

    //注意要归一化
    public static double getMinDistanceFromDomian2Query(String content,double latitude,double longitude,
                                                        Map<String,Location> domainLocation,
                                                        Map<String,FNode> query2Domain){
        double dist=Double.MAX_VALUE;
        double sum=0;
        FNode node=query2Domain.get(content);
        for(String domain:node.getEdge().keySet()){

            Location location=domainLocation.get(domain);
            double dist_temp=Math.sqrt(Math.pow((latitude-location.getLatitude()), 2)
                    +Math.pow((longitude-location.getLatitude()), 2));
            sum+=dist_temp;
            if(dist_temp<dist)
                dist=dist_temp;
        }
        return dist/sum;
    }

    //取domain到query最小距离，可能有错
    public static List<Double> getDistanceFromQuery2Domain(QNode node,double latitude,double longitude,
                                                            Map<String,Location> domainLocation){
        List<Double> result=new ArrayList<>();
        Set<String> domains=node.getNode().getEdge().keySet();
        double sumDist=0.0;
        for(String domain:domains){
            Location location=domainLocation.get(domain);
            double lat_dif=latitude-location.getLatitude();
            double lon_dif=longitude-location.getLongitude();
            double dist=Math.sqrt(Math.pow(lat_dif, 2)+Math.pow(lon_dif, 2));
            result.add(dist);
            sumDist+=dist;
        }
        final double sum=sumDist;
        result.stream().map(distance->distance/sum).collect(toList());
        return result;
    }


    public static double getDistanceFromQuery2Domain(QNode node,double latitude,double longitude,String domain,
                                                     Map<String,Location> domainLocation){
        Set<String> domains=node.getNode().getEdge().keySet();
        double sumDist=0.0;
        double distance=0;
        for(String url:domains){
            Location location=domainLocation.get(domain);
            double lat_dif=latitude-location.getLatitude();
            double lon_dif=longitude-location.getLongitude();
            double dist=Math.sqrt(Math.pow(lat_dif, 2)+Math.pow(lon_dif, 2));
            sumDist+=dist;
            if(url.equals(domain)){
                distance=dist;
            }
        }

        return distance/sumDist;
    }

}
