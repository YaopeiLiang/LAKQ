package step4;

import com.sun.org.apache.regexp.internal.RE;
import factory.DomainLocationFactory;
import factory.FNode;
import factory.GraphFactory;
import factory.TypeTypeFactory;
import metric.Pair;
import step0.Location;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by lenovo on 2017/10/11.
 */
public class UserQueryGenerator {

    public static void main(String[] args) {
        String input="D:\\LocKey\\AOL\\userTypeFreq.txt";
        String output="D:\\LocKey\\AOL\\queryList.txt";
        int limit=100;

        List<UserFreq> userList=getUserList(input,limit);
        printData(output,userList);
    }

    public static void printData(String path,List<UserFreq> userList){
        Map<String, FNode> user2QueryMap= GraphFactory.getUser2QueryMap();
        Map<String,FNode> query2DomainMap=GraphFactory.getQuery2DomainMap();
        Map<String, Location> domainLocationMap= DomainLocationFactory.getMap();
        Map<String, Pair> typeMap= TypeTypeFactory.getQueryTypeMap();

        try(BufferedWriter writer=Files.newBufferedWriter(Paths.get(path))){
            for(UserFreq userFreq:userList){
                String user=userFreq.getUser();
                Set<String> typeFreqSet=userFreq.getTypeSet();
                String query=getQuery(user,user2QueryMap,typeMap,typeFreqSet);
                Location location=getLocation(query,query2DomainMap,domainLocationMap);
                writer.write(query+"\t"+user+"\t"+location.getLatitude()+"\t"+location.getLongitude()+"\n");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static String getQuery(String user,Map<String,FNode> user2QueryMap,
                                  Map<String,Pair> typeMap,
                                  Set<String> typeSet){
        List<String> queryList=user2QueryMap.get(user).getEdge().keySet().stream().collect(Collectors.toList());
        int querySize=queryList.size();
        Random random=new Random();
        int index;
        String query,type;
        Pair pair;

        do{
            index=random.nextInt(querySize);
            query=queryList.get(index);
            pair=typeMap.get(query);
            type=pair.getQuery();

        }while (!typeSet.contains(type));

        return query;
    }

    public static Location getLocation(String query,Map<String,FNode> query2DomainMap,
                                       Map<String, Location> domainLocationMap){
        List<String> domainList=query2DomainMap.get(query).getEdge().keySet().stream().collect(Collectors.toList());
        int domainSize=domainList.size();
        Random random=new Random();
        int index=random.nextInt(domainSize);
        String domain=domainList.get(index);
        Location location=domainLocationMap.get(domain);
        return location;
    }


    public static List<UserFreq> getUserList(String path,int limit){
        List<UserFreq> candidate=new ArrayList<>();
        List<UserFreq> result=new ArrayList<>();
        try(BufferedReader reader= Files.newBufferedReader(Paths.get(path))){
            String line;
            while ((line=reader.readLine())!=null){
                String [] re=line.split(" ");
                String user=re[0];
                int count=Integer.parseInt(re[1]);
                //取查询类别大于5的用户
                if(count<=5){
                    break;
                }

                UserFreq userFreq=new UserFreq(user);
                //取单个查询类别次数大于3的
                for(int i=2;i<re.length;i+=2){
                    String type=re[i];
                    int freq=Integer.parseInt(re[i+1]);
                    if(freq>3){
                        userFreq.addType(type);
                    }
                }
                Set<String> set=userFreq.getTypeSet();
                if(!set.isEmpty()){
                    candidate.add(userFreq);
                }

            }
        }catch (IOException e){
            e.printStackTrace();
        }
        Random random=new Random();
        int size=candidate.size();
        int index;
        for(int i=0;i<limit;++i){
            index=random.nextInt(size);
            result.add(candidate.get(index));
        }

        return result;
    }

    private static class UserFreq{
        private String user;
        private Set<String> typeSet;
        public UserFreq(String user){
            this.user=user;
            this.typeSet=new HashSet<>();
        }
        public void addType(String type){
            typeSet.add(type);
        }

        public String getUser() {
            return user;
        }

        public Set<String> getTypeSet() {
            return typeSet;
        }
    }
}
