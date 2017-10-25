package metric2;

import general.DataOperation;
import general.ListDataPrinter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by lenovo on 2017/7/17.
 */
public class Probablity {
    private static String input="D:\\LocKey\\AOL\\step4";
    private static String output="D:\\LocKey\\AOL\\query\\queryUserNumeric2";

    public static void main(String[] args) {
        Map<String,Map<String,PNode>> map=getMap();
        print(map);
    }


    public static void print(Map<String,Map<String,PNode>> data){
        List<Triple<String,String,Double>> list=new ArrayList<>();
        String user,query;
        for(Map.Entry<String,Map<String,PNode>>item:data.entrySet()){
            user=item.getKey();
            Map<String,PNode> queryMap=item.getValue();
            for(Map.Entry<String,PNode> element:queryMap.entrySet()){
                query=element.getKey();
                PNode node=element.getValue();
                int clickCount=node.getClickCount();
                int size=node.getDomainSize();

                if(clickCount==1 || size==1){
                    continue;
                }

                double val=0.0;
                Map<String,Integer> domains=node.getDomainMap();
                for(Map.Entry<String,Integer> m:domains.entrySet()){
                    int count=m.getValue();
                    double weight=(double)count/clickCount;
                    weight=weight*(Math.log(weight)/Math.log((double) 2));
                    val+=weight;
                }
                double log= (Math.log((double) size)/Math.log((double)2));
                val/=log;

                if (val != 0) {
                    val=-val;
                }
                list.add(new Triple(user,query,val));
            }
        }
        list.sort(Comparator.comparing(Triple::getThrid));
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(output))){
            for(int i=list.size()-1;i>=0;i--){
                Triple node=list.get(i);
                writer.write(node.getFirst()+"\t"+node.getSecond()+"\t"
                        +node.getThrid()+"\n");
            }
        }catch (IOException e){

        }

    }

    public static Map<String,Map<String,PNode>> getMap(){
        MapMapDataCollector<String,String,PNode> collector=(BufferedReader reader,Map<String,Map<String,PNode>> map)->{
            String line,user,query,domain;
            while ((line=reader.readLine())!=null){
                String[] re=line.split("\t");
                user=re[0];query=re[1];domain=re[2];

                Map<String,PNode> queryMap=map.get(user);
                if(queryMap==null){
                    queryMap=new HashMap<>();
                }
                PNode node=queryMap.get(query);
                if(node==null){
                    node=new PNode();
                }
                node.add(domain);
                queryMap.put(query,node);
                map.put(user,queryMap);
            }
        };
        Map<String,Map<String,PNode>> result= DataOperation.mapMapDataGetter(input,collector);
        return result;
    }
}
