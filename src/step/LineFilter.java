package step;

import java.util.Set;

/**
 * Created by lenovo on 2017/7/14.
 */
public interface LineFilter {
    boolean lineFilter(String line, Set<String> set);
}
