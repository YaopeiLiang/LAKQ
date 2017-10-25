package graph;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Created by lenovo on 2017/7/13.
 */
public class UserCollect {
    private static final String input="D:\\LocKey\\AOL\\step4";

    public static void main(String[] args) {
        makeUser2QueryMap();
        makeQuery2UserMap();
        makeQuery2DomainMap();
        makeDomain2QueryMap();
        makeUser2DomainMap();
    }

    public static void makeUser2DomainMap(){
         Map<String,GNode> user2Domain=getUser2DomainMap();
         String output="D:\\LocKey\\AOL\\graph\\user2domain.txt";
          print(output,user2Domain);
    }

    public static void makeDomain2QueryMap(){
         Map<String,GNode> map=getDomainMap();
         String output="D:\\LocKey\\AOL\\graph\\domain2query.txt";
          print(output,map);
    }

    public static void makeQuery2DomainMap(){
          Map<String,GNode> map=getQuery2DomainMap();
          String output="D:\\LocKey\\AOL\\graph\\query2domain.txt";
          print(output,map);
    }

    public static void makeQuery2UserMap(){
          Map<String,GNode> map=getQuery2UserMap();
          String output="D:\\LocKey\\AOL\\graph\\query2user.txt";
          print(output,map);
    }

    public static void makeUser2QueryMap(){
        Map<String,GNode> map=getUserMap();
        String output="D:\\LocKey\\AOL\\graph\\user2query.txt";
        print(output,map);
    }



    public static Map<String,GNode> getUser2DomainMap(){
        MapChoice choice=(String[]re,Map<String,GNode> map)->{
            GNode node=map.get(re[0]);
            if(node==null){
                node=new GNode();
            }
            node.addEdge(re[2]);
            map.put(re[0],node);
        };
        return getMap(choice);
    }


    public static void print(String output,Map<String,GNode> map){
        String query;
        Integer freq;
        int click;
        double weight;
        try(BufferedWriter writer=new BufferedWriter(new BufferedWriter(new FileWriter(output)))){
            for(Map.Entry<String,GNode> item:map.entrySet()){
                String key=item.getKey();
                GNode node=item.getValue();
                click=node.getTotalClick();
                writer.write(key+"\t"+click+"\t");

                Map<String,Integer> edges=node.getEdges();
                for(Map.Entry<String,Integer> edge:edges.entrySet()){
                    query=edge.getKey();
                    freq=edge.getValue();
                    weight=(double)freq/click;
                    writer.write(query+"\t"+freq+"\t"+weight+"\t");
                }
                writer.write("\n");
            }
        }catch (IOException e){

        }
    }


    public static Map<String,GNode> getUserMap(){
        Map<String,GNode> result=getMap((String[] re,Map<String,GNode> map)->{
            String user,query;
            user=re[0];
            query=re[1];
            GNode node=map.get(user);
            if(node==null){
                node=new GNode();
            }
            node.addEdge(query);
            map.put(user,node);
        });
        return result;
    }
    public static Map<String,GNode> getQuery2UserMap(){
        Map<String,GNode> result=getMap((String[] re,Map<String,GNode> map)->{
            String user,query;
            user=re[0];
            query=re[1];
            GNode node=map.get(query);
            if(node==null){
                node=new GNode();
            }
            node.addEdge(user);
            map.put(query,node);
        });
        return result;
    }

    public static Map<String,GNode> getDomainMap(){
        Map<String,GNode> result=getMap((String[] re,Map<String,GNode> map)->{
            String query=re[1],domain=re[2];
            GNode node=map.get(domain);
            if(node==null){
                node=new GNode();
            }
            node.addEdge(query);
            map.put(domain,node);
        });
        return result;
    }


    public static Map<String,GNode> getQuery2DomainMap(){
        Map<String,GNode> result=getMap((String[]re,Map<String,GNode> map)->{
            String domain=re[2],query=re[1];
            GNode node=map.get(query);
            if(node==null){
                node=new GNode();
            }
            node.addEdge(domain);
            map.put(query,node);
        });
        return result;
    }

    private static Map<String,GNode> getMap(MapChoice choice){
      Map<String,GNode> result=GraphOperation.getGraph(input,(BufferedReader reader,Map<String,GNode> gmap)->{
           String line;
           while ((line=reader.readLine())!=null){
               String []re=line.split("\t");
               choice.choose(re,gmap);
           }
        });
      return result;
    }


}
