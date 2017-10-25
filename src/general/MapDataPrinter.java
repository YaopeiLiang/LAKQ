package general;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Created by lenovo on 2017/7/17.
 */
public interface MapDataPrinter<T,R> {
    void printMapData(BufferedWriter writer,Map<T, R> map) throws IOException;
}
