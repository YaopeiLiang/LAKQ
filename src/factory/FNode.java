package factory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2017/7/14.
 */
public class FNode implements Cloneable{

    private int totalClick;
    private Map<String,NNode> edge;

    public FNode(int totalClick){
        this.totalClick=totalClick;
        edge=new HashMap<>();
    }

    public void addEdge(String item,int click,double weight){
        NNode node=edge.get(item);
        if(node==null){
            node=new NNode(click,weight);
        }
        edge.put(item,node);
    }

    public void addFNode(FNode node){
        this.edge.putAll(node.getEdge());
        /*
        for(Map.Entry<String,NNode> item:edge.entrySet()){
            String key=item.getKey();
            NNode itemValue=item.getValue();
            double weight=itemValue.getWeight();
            weight/=2;
            itemValue.setWeight(weight);

            edge.put(key,itemValue);
        }
        */
    }

    public int getTotalClick() {
        return totalClick;
    }

    public Map<String, NNode> getEdge() {
        return edge;
    }

    @Override
    public FNode clone(){
        FNode result=new FNode(this.totalClick);
        Map<String,NNode> map=new HashMap<>(this.edge);
        result.edge=map;
        return result;
    }

    @Override
    public String toString(){
        StringBuilder builder=new StringBuilder();
        builder.append(totalClick);
        double total=0;
        for(Map.Entry<String,NNode> item:edge.entrySet()){
            NNode node=item.getValue();
            double weight=node.getWeight();
            total+=weight;
            builder.append("["+item.getKey()+","+weight+"] ");
        }
        builder.append(" [totalWeight:"+total+"]");
        return builder.toString();
    }
}
