package graph;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2017/7/13.
 */
public class GraphOperation {


    public static  Map<String,GNode> getGraph(String input, GraphCollector collector){
        Map<String,GNode> map=new HashMap<>();
        File[] files=new File(input).listFiles();
        for(File file:files){
            System.out.println(file);
            try(BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(file)))){
                collector.collect(reader,map);
            }catch (FileNotFoundException e){

            }catch (IOException e){

            }
        }
        return map;
    }
}
