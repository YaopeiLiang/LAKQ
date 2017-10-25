package domainSimilarity.domain;

import general.DataOperation;
import general.DataSetGetter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

/**
 * Created by lenovo on 2017/7/19.
 */
public class DomainSetGetter {

    public static void main(String[] args){

        String input="D:\\LocKey\\AOL\\step4\\";
        String output="D:\\LocKey\\AOL\\domain\\local\\need.txt";
        Set<String> set=getDomainSet(input);
        outputDomainSet(output,set);

    }
    public static void outputDomainSet(String output,Set<String> set){
        DataOperation.singleDataPrinter(output,set);
    }

    public static Set<String> getDomainSet(String input){
        DataSetGetter dataSetGetter=(BufferedReader reader,Set<String> set)->{
            String line;
            while ((line=reader.readLine())!=null){
                String[] re=line.split("\t");
                set.add(re[2]);
            }
        };
        return DataOperation.getCombinedDataSet(input,dataSetGetter);
    }
}
