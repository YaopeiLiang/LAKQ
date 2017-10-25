package metric2;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2017/7/17.
 */
public class PNode {
    private int clickCount;
    private Map<String,Integer> domainMap;

    public PNode() {
        clickCount=0;
        domainMap=new HashMap<>();
    }

    public void add(String domain){
        clickCount+=1;
        if(domainMap.containsKey(domain)){
            int count=domainMap.get(domain);
            count+=1;
            domainMap.put(domain,count);
        }else{
            domainMap.put(domain,1);
        }
    }

    public int getDomainSize(){
        return domainMap.keySet().size();
    }

    public int getClickCount() {
        return clickCount;
    }

    public Map<String, Integer> getDomainMap() {
        return domainMap;
    }
}
