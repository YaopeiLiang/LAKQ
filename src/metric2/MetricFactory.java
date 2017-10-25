package metric2;

import general.DataLoader;
import general.DataOperation;
import general.ListDataPrinter;
import general.MapDataPrinter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by lenovo on 2017/7/17.
 */
public class MetricFactory {
    private static String input="D:\\LocKey\\AOL\\graph\\query2domain.txt";
    private static String output="D:\\LocKey\\AOL\\query\\queryNumeric.txt";

    public static void main(String[] args) {
        List<Pair<String,Double>> list=getQueryMap();
        printQueryMap(list);
    }
    private static void printQueryMap(List<Pair<String,Double>> list){
        ListDataPrinter<Pair<String,Double>> dataPrinter=(BufferedWriter writer,List<Pair<String,Double>> data)->{
            data.sort(Comparator.comparing(Pair::getSecond));
            Pair<String,Double> pair=null;
            for(int i=data.size()-1;i>=0;i--){
                pair=data.get(i);
                writer.write(pair.getFirst()+"\t"+pair.getSecond()+"\n");
            }
        };
        DataOperation.printListData(output,list,dataPrinter);
    }

    private static List<Pair<String,Double>> getQueryMap(){
        Map<String,Double> result= DataOperation.loadData(input, (BufferedReader reader)->{
           Map<String,Double> map=new HashMap<>();
           String line,query;
           double val,prob,temp;
           int setNum;
           while ((line=reader.readLine())!=null){
               String[] re=line.split("\t");
               query=re[0];
               val=0;
               double size=Double.parseDouble(re[1]);
               for(int i=4;i<re.length;i+=3){
                   prob=Double.parseDouble(re[i]);
                   temp=Math.log(prob)/Math.log((double)2);
                   if(temp==0)
                       System.out.println(query+":"+prob+":"+temp);
                   prob=prob*temp;
                   val+=prob;
               }
               if(val!=0){
                   val=-val;
                   val/=size;
               }
               map.put(query,val);
           }
           return map;
        });

        List<Pair<String,Double>> list=new ArrayList<>();
        for(Map.Entry<String,Double> node:result.entrySet()){
            list.add(new Pair<>(node.getKey(),node.getValue()));
        }
        return list;
    }
}
