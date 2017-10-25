package metric2;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Created by lenovo on 2017/7/17.
 */
public interface MapmapDtatPrinter<T,R,U> {
    void print(BufferedWriter writer, Map<T,Map<R,U>> map) throws IOException;
}
