package domainSimilarity.full;

import general.DataFilter;
import general.DataOperation;
import general.DataSetGetter;

import java.io.BufferedReader;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lenovo on 2017/7/21.
 */
public class FullSetGetter {
    private static String srcPath="D:\\LocKey\\smoothUrl2Loc";
    private static String destPath="D:\\LocKey\\AOL\\fullDomain\\full.txt";


    public static void main(String[] args) {
        Set<String> set=getFullSet();
        DataOperation.singleDataPrinter(destPath,set);
    }

    public static Set<String> getFullSet(){
        DataFilter<String> filter=(BufferedReader reader)->{
            Set<String> set=new HashSet<>();
            String line;
            while ((line=reader.readLine())!=null){
                String[] re=line.split("\t");
                set.add(re[0]);
            }
            return set;
        };
        return DataOperation.getFilterSet(srcPath,filter);
    }
}
