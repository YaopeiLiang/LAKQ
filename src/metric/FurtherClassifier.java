package metric;

import general.DataOperation;

import java.io.BufferedReader;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lenovo on 2017/7/16.
 */
public class FurtherClassifier {
    public static Set<Pair> getQuerySet(String input){
        return DataOperation.getFilterSet(input,(BufferedReader reader)->{
           Set<Pair> result=new HashSet<>();
           String line;
           while((line=reader.readLine())!=null){
               String[] re=line.split("\t");
               if(re.length<2){
                   System.out.println(line);
                   continue;
               }
               Pair pair=new Pair(re[0],re[1]);
               result.add(pair);
           }
           return result;
        });
    }
}
