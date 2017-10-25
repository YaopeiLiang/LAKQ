package graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

/**
 * Created by lenovo on 2017/7/13.
 */
public interface GraphCollector {
    void collect(BufferedReader reader,Map<String,GNode> map) throws IOException;
}
