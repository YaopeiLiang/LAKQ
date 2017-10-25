package domainSimilarity.domain;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by lenovo on 2017/7/20.
 */
public class DomainCleaner {
    private static String domianSetPath="D:\\LocKey\\AOL\\domain\\domainSet";
    private static String fromPath="D:\\LocKey\\AOL\\domain\\cleanDomainContentPorter\\";
    private static String badToPath="D:\\LocKey\\AOL\\domain\\badFile";

    public static void main(String[] args) {
        clean();
    }

    public static void clean(){
        Set<String> set=getUsefulSet();
        File[] files=new File(fromPath).listFiles();
        List<File> fileList= Arrays.asList(files);

       Set<String> totalSet= fileList.parallelStream().map(file->{

           String name=file.getName();
            String url=name.replace("-",".");
            if(url.contains("http")){
                url="http://"+url.substring(4);
            }else if(url.contains("https")){
                url="https://"+url.substring(5);
            }
            return url;
        }).collect(Collectors.toSet());

       if(totalSet.containsAll(set)){
           System.out.println("yes");
       }else{
           set.removeAll(totalSet);
           try{
               BufferedWriter writer=new BufferedWriter(new FileWriter(badToPath));
               for(String s:set){
                   writer.write(s+"\n");
               }
               writer.close();
           }catch (IOException e){

           }
           System.out.println("shit!"+ set.size());
       }
    }



    public static Set<String> getUsefulSet(){
        return Pool.getDomain(domianSetPath);
    }
}
