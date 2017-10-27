package general;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by lenovo on 2017/10/27.
 */
public interface ListReader<T> {
    List<T> readList(BufferedReader reader) throws IOException;
}
