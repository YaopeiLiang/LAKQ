package step;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

/**
 * Created by lenovo on 2017/7/14.
 */
public interface StepMap<T,R> {
    void mapFilter(BufferedReader reader, Map<T,R>map)throws IOException;
}
