package general;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Set;

/**
 * Created by lenovo on 2017/7/2.
 */
public interface DataFilter<T> {
    Set<T> filterSet(BufferedReader reader) throws IOException;
}
