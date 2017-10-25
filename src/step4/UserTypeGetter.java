package step4;

import factory.FNode;
import factory.GraphFactory;
import factory.TypeTypeFactory;
import metric.Pair;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by lenovo on 2017/10/11.
 */
public class UserTypeGetter {

    public static void main(String[] args) {
        String path="D:\\LocKey\\AOL\\userTypeFreq.txt";
        Map<String,Map<String,Integer>> map=getMap();
        printData(path,map);
    }



    public static void printData(String path,Map<String,Map<String,Integer>> map){
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(path))){
            List<UserType> list=new ArrayList<>();
            for(Map.Entry<String,Map<String,Integer>> item:map.entrySet()){
                String user=item.getKey();
                Map<String,Integer> typeFreqMap=item.getValue();
                UserType userType=new UserType(user,typeFreqMap);
                list.add(userType);
            }
            int[] stat=new int[10];
            list.sort((UserType u1,UserType u2)->{
                int c1=u1.getCount();
                int c2=u2.getCount();
                if(c1>c2){
                    return -1;
                }else if(c1<c2){
                    return 1;
                }else{
                    return 0;
                }
            });
            for(UserType item:list){
                String user=item.getUser();
                int count=item.getCount();
                Map<String,Integer> typeFreqMap=item.getMap();

                stat[count-1]+=1;
                writer.write(user+" "+count+" ");
                for(Map.Entry<String,Integer> typeFreq:typeFreqMap.entrySet()){
                    writer.write(typeFreq.getKey()+" "+typeFreq.getValue()+" ");
                }
                writer.write("\n");
            }
            for(int i=0;i<stat.length;++i){
                System.out.println((i+1)+":"+stat[i]);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static Map<String,Map<String,Integer>> getMap(){
        Map<String,Map<String,Integer>> userTypeMap=new HashMap<>();
        Map<String, FNode> user2queryMap= GraphFactory.getUser2QueryMap();
        Map<String, Pair> queryTypeMap= TypeTypeFactory.getQueryTypeMap();

        for(Map.Entry<String,FNode> item:user2queryMap.entrySet()){
            String user=item.getKey();
           Set<String> querySet=item.getValue().getEdge().keySet();
           querySet.stream().forEach(query->{
               String type=queryTypeMap.get(query).getQuery();
               Map<String,Integer> map=userTypeMap.get(user);
               if(map==null){
                   map=new HashMap<>();
                   map.put(type,1);
               }else{
                   if(map.containsKey(type)){
                       int freq=map.get(type);
                       freq+=1;
                       map.put(type,freq);
                   }else{
                       map.put(type,1);
                   }
               }
               userTypeMap.put(user,map);
           });
        }
        return userTypeMap;
    }

    private static class UserType{
        private String user;
        private  Map<String,Integer> map;
        private int count;
        public UserType(String user,Map<String,Integer> map){
            this.user=user;
            this.map=map;
            count=map.size();
        }

        public String getUser() {
            return user;
        }

        public Map<String, Integer> getMap() {
            return map;
        }

        public int getCount(){
            return count;
        }
    }
}
