package step3;

import general.DataOperation;
import step.StepOperation;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by lenovo on 2017/7/2.
 */
public class QueryFreqRemoval {
    private static String input="D:\\LocKey\\AOL\\step1";
    private static String output="D:\\LocKey\\AOL\\step2\\";

    public static void main(String[] args) {
        Set<String> set=getQueryFreqSet();
        output(set);
    }

    public static void output(Set<String> set){
        StepOperation.printFiles(input,output,set,(String line,Set<String> filterSet)->{
            String[] re=line.split("\t");
            //是否包含query
            return filterSet.contains(re[1]);
        });
    }


    public static Set<String> getQueryFreqSet(){
        Map<String,Set<String>> map=getQueryFreqMap(input);
        //>=valve
        return StepOperation.getSetFilter(map,3);
    }

    public static Map<String,Set<String>> getQueryFreqMap(String input){
        Map<String,Set<String>>result= StepOperation.getFilterMap(input,(BufferedReader reader,Map<String,Set<String>> map)->{
            String line,user,query;
            while ((line=reader.readLine())!=null){
                String[] re=line.split("\t");
                user=re[0];
                query=re[1];
                Set<String> userSet=map.get(query);
                if(userSet==null){
                    userSet=new HashSet<>();
                }
                userSet.add(user);
                map.put(query,userSet);
            }
        });
        return result;
    }
}
