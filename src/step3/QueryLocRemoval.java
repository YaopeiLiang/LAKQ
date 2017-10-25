package step3;

import factory.DomainLocationFactory;
import step.StepOperation;
import step0.Location;

import java.io.BufferedReader;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by lenovo on 2017/7/14.
 */
public class QueryLocRemoval {

    private static String input="D:\\LocKey\\AOL\\step2";
    private static String output="D:\\LocKey\\AOL\\step3\\";

    public static void main(String[] args) {
        Set<String> set=getFilterSet();
        print(set);
    }

    public static void print(Set<String> set){
        StepOperation.printFiles(input,output,set,(String line,Set<String> filterSet)->{
            String []re=line.split("\t");
            return filterSet.contains(re[1]);
        });
    }

    public static Set<String> getFilterSet(){
        Map<String,Set<Location>> map=getFilterMap(input);
        return StepOperation.getSetFilter(map,2);
    }

    public static Map<String,Set<Location>> getFilterMap(String input){
        Map<String,Set<Location>> result=StepOperation.getFilterMap(input,(BufferedReader reader,Map<String,Set<Location>> map)->{
            Map<String,Location> domainLoc=DomainLocationFactory.getMap();

            String line,query,domain;
            while ((line=reader.readLine())!=null){
                String[] re=line.split("\t");
                query=re[1];
                domain=re[2];
                //获得domain对应的位置
                Location loc=domainLoc.get(domain);

                Set<Location> locationSet=map.get(query);
                if(locationSet==null){
                    locationSet=new HashSet<>();
                }
                locationSet.add(loc);
                map.put(query,locationSet);
            }
        });
        return result;
    }
}
