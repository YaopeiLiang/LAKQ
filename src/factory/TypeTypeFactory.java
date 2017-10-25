package factory;

import general.DataLoader;
import general.DataOperation;
import metric.Pair;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2017/7/17.
 */
public class TypeTypeFactory {

    private static Map<String,Pair> queryTypeMap=null;
    private static String input="D:\\LocKey\\AOL\\query\\queryTypeType.txt";

    public static void main(String[] args) {
        getQueryTypeMap();
    }

    public static Map<String,Pair> getQueryTypeMap(){
        if(queryTypeMap==null){
            DataLoader<String,Pair> dataLoader=(BufferedReader reader)->{
              Map<String,Pair> result=new HashMap<>();
              String line,query;
              while((line=reader.readLine())!=null){
                  String[] re=line.split("\t");
                  if(re.length!=3){
                      System.out.println(line);
                  }
                  else{
                      result.put(re[0],new Pair(re[1],re[2]));
                  }
              }
              return result;
            };
            queryTypeMap=DataOperation.loadData(input,dataLoader);
        }
        return queryTypeMap;
    }
}
