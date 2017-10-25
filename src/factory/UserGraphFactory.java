package factory;

import java.util.Map;

/**
 * Created by lenovo on 2017/7/14.
 */
public class UserGraphFactory {
    private static final String input="";
    private static Map<String,FNode> map=null;

    public static Map<String,FNode> getUserMap(){
        if(map==null){
            map= GraphFactory.getMap(input);
        }
        return map;
    }
}
