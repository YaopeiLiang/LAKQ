package general;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Set;

/**
 * Created by lenovo on 2017/7/5.
 */
public interface DataSetGetter {
     void getDataSet(BufferedReader reader,Set<String> set) throws IOException;
}
