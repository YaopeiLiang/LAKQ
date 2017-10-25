package general;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by lenovo on 2017/7/17.
 */
public interface ListDataPrinter<T> {
    void listDataPrinter(BufferedWriter writer, List<T> list)throws IOException;
}
