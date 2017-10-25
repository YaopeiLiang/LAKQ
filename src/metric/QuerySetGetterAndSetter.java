package metric;

import general.DataOperation;

import java.io.BufferedReader;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lenovo on 2017/7/5.
 */
public class QuerySetGetterAndSetter {
    private static String input="D:\\LocKey\\AOL\\step4\\";
    private static String output="D:\\LocKey\\AOL\\query\\querySet.txt";

    public static void main(String[] args) {
        Set<String> set=getQuerySet();
        printQuerySet(output,set);
    }
    public static void printQuerySet(String output,Set<String> set){
        DataOperation.singleDataPrinter(output,set);
    }

    public static Set<String> getQuerySet(){
        Set<String> querySet= DataOperation.getCombinedDataSet(input,(BufferedReader reader,Set<String> set)->{
           String line;
           while((line=reader.readLine())!=null){
               String[] re=line.split("\t");
               set.add(re[1]);
           }
        });
        System.out.println(querySet.size());
        return querySet;
    }
}
