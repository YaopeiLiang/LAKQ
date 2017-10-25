package domainSimilarity.content;

import domainSimilarity.domain.Pool;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toSet;

/**
 * Created by lenovo on 2017/7/26.
 */
public class Test {
    public static void main(String[] args) {
        String str = "123abc你好efc\n:dadada";

        String reg = "[^\\x00-\\x7F]";

        Pattern pat = Pattern.compile(reg);

        Matcher mat=pat.matcher(str);

        String repickStr = mat.replaceAll("");

        str=str.replaceAll(reg, "");
        System.out.println("str:"+str);
        System.out.println("去中文后:"+repickStr);
    }
    public static Set<String> getURLSet(){
        String path="D:\\LocKey\\AOL\\domain\\local\\localSet.txt";
        Set<String> set=Pool.getDomain(path);
        return set.parallelStream().map(str->{
            str=str.replace("://","").replace("/","我")
                    .replace("?","他");
            return  str+".txt";
        }).collect(toSet());
    }

    public static Set<String> getFileSet(){
        Set<String> set=new HashSet<>();
        String path="D:\\LocKey\\AOL\\domain\\local\\local";
        try(DirectoryStream<Path> stream=
                    Files.newDirectoryStream(Paths.get(path))){
            for(Path entry:stream){
                //set.add(entry.getFileName().toString());
                if(entry.toFile().length()<=1024){
                    String name=entry.toFile().toString();
                    set.add(name);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return set;
    }
}
