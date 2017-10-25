package step;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by lenovo on 2017/7/14.
 */
public class StepOperation {

    public static void printFiles(String input,String output,
                                  Set<String> set,LineFilter filter){
        File[] files=new File(input).listFiles();
        int counter=1;
        String line;
        for(File file:files){
            BufferedWriter writer=null;
            String name=output+counter+".txt";
            counter+=1;

            try(BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(file)))){
                System.out.println(file);
                System.out.println(name);
                writer=new BufferedWriter(new FileWriter(name));
                //过滤文本
                while((line=reader.readLine())!=null){
                    if(filter.lineFilter(line,set)){
                        writer.write(line+"\n");
                    }
                }
                writer.close();
            }catch (FileNotFoundException e){

            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }


    public static<T,R>  Set<T> getSetFilter(Map<T,Set<R>> map,int valve){
        Set<T> set=new HashSet<>();
        for(Map.Entry<T,Set<R>> item:map.entrySet()){
           T key=item.getKey();
           Set<R> val=item.getValue();
           //大于阈值加入结果
           if(val.size()>=valve){
               set.add(key);
           }
        }
        return set;
    }

    public static<T,R> Map<T,R> getFilterMap(String input,StepMap<T,R> filter){
        Map<T,R> map=new HashMap<>();
        File[] files=new File(input).listFiles();
        for(File file:files){
            try(BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(file)))){
                filter.mapFilter(reader,map);
            }catch (FileNotFoundException e){

            }catch (IOException e){

            }
        }
        return map;
    }
}
