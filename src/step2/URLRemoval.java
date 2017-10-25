package step2;

import general.DataFilter;
import general.DataOperation;
import porter.QueryFilter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lenovo on 2017/7/2.
 */
public class URLRemoval {
    private static String input="D:\\LocKey\\AOL\\data";
    private static String filterInput="D:\\LocKey\\AOL\\domain\\domainCor";
    private static String output="D:\\LocKey\\AOL\\step1\\";
    private static String successPath="D:\\LocKey\\AOL\\domain\\total\\success.txt";
    public static void main(String[] args) {

       urlRemove();
    }

    public static void urlRemove(){
        String combinePath="D:\\LocKey\\AOL\\domain\\total\\combine.txt";
        Set<String> urlSet=DataOperation.singleDataGetter(combinePath);
        DataOperation.filterData(input,output,urlSet,(BufferedReader reader, BufferedWriter writer,Set<String> remnant)->{
            String line,query,url;
            int user;
            while((line=reader.readLine())!=null){
                if(line.startsWith("AnonID"))
                    continue;
                String[] re=line.split("\t");
                //没有url
                if(re.length<5){
                    continue;
                }else if(remnant.contains(re[4])==false){
                    continue;
                }else{
                    try{
                        user=Integer.parseInt(re[0]);
                        query= QueryFilter.filterString(re[1]);
                        if(query.length()<=2)
                            continue;
                        url=re[4];
                        writer.write(user+"\t"+query+"\t"+url+"\n");
                    }catch (NumberFormatException e){
                        System.out.println(line);
                    }
                }
            }
        });
    }
}
