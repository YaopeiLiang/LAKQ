package domainSimilarity.index;

import domainSimilarity.content.Test;
import domainSimilarity.domain.ContentDownloader;
import domainSimilarity.domain.Pool;
import general.DataOperation;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

/**
 * Created by lenovo on 2017/7/26.
 */
public class Left {
    public static void main(String[] args) {
        action();
    }

    public static void  collect(){

    }



    public static void action(){
        Set<String> emptySet=getEmptySet();
        System.out.println("left size:"+emptySet.size());
        Set<String> urlSet=emptySet.parallelStream()
                .map(str->{
                    Path path= Paths.get(str);
                    String url=null;
                    try(BufferedReader reader=Files.newBufferedReader(path)){
                        url=reader.readLine();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    return url;
                }).collect(toSet());

        Set<ContentDownloader> mission=urlSet.stream()
                .map(str->{
                    return new ContentDownloader(str);
                }).collect(toSet());
        Pool.downloadContent(mission,null,null);
    }

    public static Set<String> getEmptySet(){
        String path="D:\\LocKey\\AOL\\domain\\local\\emptySet.txt";
        Set<String> set=DataOperation.singleDataGetter(path);
        return set;
    }

    public static void printSet(Set<String> emptySet){
        String path="D:\\LocKey\\AOL\\domain\\local\\emptySet.txt";
        DataOperation.singleDataPrinter(path,emptySet);
    }

    public static  Set<String> collectEmptySet(){
        Set<java.lang.String> result=Test.getFileSet();
        System.out.println(" 1KB result size:"+result.size());
        Set<String> emptySet=result.parallelStream()
                .filter(str->{
                    try(BufferedReader reader
                                = new BufferedReader(
                                        new InputStreamReader(new FileInputStream(str)))){
                        String line=null;
                        //都
                        reader.readLine();
                        line=reader.readLine();
                        reader.close();
                        if(line==null){
                            return true;
                        }else {
                            return false;
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                        return false;
                    }
                }).collect(toSet());
        return emptySet;
    }
}
