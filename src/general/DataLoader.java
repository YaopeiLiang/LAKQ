package general;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

/**
 * Created by lenovo on 2017/7/2.
 */
public interface DataLoader<T,R> {
    Map<T,R> loadData(BufferedReader reader) throws IOException;
}
