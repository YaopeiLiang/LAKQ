package algorithm;

import factory.FNode;
import factory.GraphFactory;

import java.util.Map;

/**
 * Created by lenovo on 2017/7/18.
 */
public class Check {
    public static void main(String[] args) {
        String user="3675165";
        Map<String, FNode> map= GraphFactory.getUserMap();
        GraphFactory.clearUserMap();
        FNode node=map.get(user);
        System.out.println(node.getEdge().size());
    }
}
