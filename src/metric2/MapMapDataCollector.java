package metric2;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

/**
 * Created by lenovo on 2017/7/17.
 */
public interface MapMapDataCollector<T,R,U> {
    void collect(BufferedReader reader, Map<T,Map<R,U>> map) throws IOException;
}
