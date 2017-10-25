package statistics;

import general.DataOperation;
import general.DataSetGetter;
import step.LineFilter;

import java.io.BufferedReader;
import java.util.Set;

/**
 * Created by lenovo on 2017/7/14.
 */
public class Statistic {
    public static void main(String[] args) {
        String step1="D:\\LocKey\\AOL\\step1";
        Set<String> set1=getQuerySet(step1);
        System.out.println(set1.size());

        String step2="D:\\LocKey\\AOL\\step2";
        Set<String> set2=getQuerySet(step2);
        System.out.println(set2.size());
    }

    public static Set<String> getQuerySet(String input){
        return DataOperation.getCombinedDataSet(input,(BufferedReader reader,Set<String>set )->{
           String line;
           while ((line=reader.readLine())!=null){
               String[] re=line.split("\t");
               set.add(re[1]);
            }
        });
    }
}
