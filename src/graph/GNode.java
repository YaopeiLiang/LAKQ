package graph;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2017/7/13.
 */
public class GNode {
    private Map<String,Integer> edges;
    private int totalClick;

    public GNode(){
        edges=new HashMap<>();
        totalClick=0;
    }

    public void addEdge(String key){
        totalClick+=1;
        Integer count=edges.get(key);
        if(count==null){
            edges.put(key,1);
        }else{
            edges.put(key,count+1);
        }
    }

    public int getTotalClick() {
        return totalClick;
    }

    public Map<String, Integer> getEdges() {
        return edges;
    }
}
