package step4;

import step.StepOperation;

import java.io.BufferedReader;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by lenovo on 2017/7/14.
 */
public class UserFreqRemoval {
    private static String input="D:\\LocKey\\AOL\\step3";
    private static String output="D:\\LocKey\\AOL\\step4\\";

    public static void main(String[] args) {
        Set<String> set=getUserFreqSet();
        output(set);

    }
    public static void output(Set<String> set){
        StepOperation.printFiles(input,output,set,(String line,Set<String> filterSet)->{
            String[] re=line.split("\t");
            //是否包含user
            return filterSet.contains(re[0]);
        });
    }

    public static Set<String> getUserFreqSet(){
        Map<String,Set<String>> map=getUserFreqMap(input);
        //>=valve
        return StepOperation.getSetFilter(map,3);
    }

    public static Map<String,Set<String>> getUserFreqMap(String input){
        Map<String,Set<String>>result= StepOperation.getFilterMap(input,(BufferedReader reader, Map<String,Set<String>> map)->{
            String line,user,query;
            while ((line=reader.readLine())!=null){
                String[] re=line.split("\t");
                user=re[0];
                query=re[1];

                Set<String> querySet=map.get(user);
                if(querySet==null){
                    querySet=new HashSet<>();
                }
                querySet.add(query);

                map.put(user,querySet);
            }
        });
        return result;
    }
}
