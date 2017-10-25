package general;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Set;

/**
 * Created by lenovo on 2017/7/2.
 */
public interface DataPrinter<T> {
    void printData(BufferedReader reader,BufferedWriter writer, Set<T> remnant)throws IOException;
}
