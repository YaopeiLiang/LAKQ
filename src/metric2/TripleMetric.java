package metric2;

import factory.FNode;
import factory.GraphFactory;
import general.DataOperation;
import general.DataSetGetter;
import general.MapDataPrinter;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * Created by lenovo on 2017/7/17.
 */
public class TripleMetric {
    private static String input="D:\\LocKey\\AOL\\step4";
    private static String output="D:\\LocKey\\AOL\\query\\queryUserNumeric";

    public static void main(String[] args) {
        Map<String,Map<String,List<String>>> map=getMap();
        proSet(map);
    }

    public static void proSet(Map<String,Map<String,List<String>>> map){
        List<Triple<String,String,Integer>> list=new ArrayList<>();

        String user,query,domain;
        for(Map.Entry<String,Map<String,List<String>>> item:map.entrySet()){
            user=item.getKey();
            Map<String,List<String>> innerMap=item.getValue();
            for(Map.Entry<String,List<String>> element:innerMap.entrySet()){
                query=element.getKey();
                List<String> domainList=element.getValue().stream().distinct().collect(Collectors.toList());
                list.add(new Triple<>(user,query,domainList.size()));
            }
        }

        list.sort(Comparator.comparing(Triple::getThrid));


        try(BufferedWriter writer=new BufferedWriter(new FileWriter(output))){
            for(int i=list.size()-1;i>=0;i--){
                Triple triple=list.get(i);
                writer.write(triple.getFirst()+"\t"+triple.getSecond()+"\t"
                            +triple.getThrid()+"\n");
            }
        }catch (IOException e){

        }
    }

    public static Map<String,Map<String,List<String>>> getMap(){
        File[] files=new File(input).listFiles();
        Map<String,Map<String,List<String>>> map=new HashMap<>();
        String user,query,domain;

        for(File file:files){
            try(BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(file)))){
                String line;
                while ((line=reader.readLine())!=null){
                    String[] re=line.split("\t");
                   user=re[0];query=re[1];domain=re[2];

                   Map<String,List<String>> queryDomain=map.get(user);
                   if(queryDomain==null){
                       queryDomain=new HashMap<>();
                   }

                    List<String> domainList=queryDomain.get(query);
                    if(domainList==null){
                        domainList=new ArrayList<>();
                    }
                    domainList.add(domain);
                    queryDomain.put(query,domainList);
                    map.put(user,queryDomain);
                }
            }catch (IOException e){

            }
        }
        return map;
    }
}
