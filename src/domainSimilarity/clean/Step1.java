package domainSimilarity.clean;

import general.DataOperation;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by lenovo on 2017/7/26.
 */
public class Step1 {
    public static void main(String[] args) {
        //clean();
        combineSet();
    }

    public static void combineSet(){
        String input="D:\\LocKey\\AOL\\domain\\total";
        String output="D:\\LocKey\\AOL\\domain\\total\\combine.txt";
        Path path=Paths.get(input);
        HashSet<String> result=new HashSet<>();
        try(DirectoryStream<Path> stream=Files.newDirectoryStream(path)){
            for(Path entry:stream){
                String name=entry.toFile().toString();
                if(name.contains("trueSet")){
                    Set<String> set=DataOperation.singleDataGetter(name);
                    result.addAll(set);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        DataOperation.singleDataPrinter(output,result);
    }

    public static void clean(){
        String input="D:\\LocKey\\AOL\\domain\\total\\real3";
        String output="D:\\LocKey\\AOL\\domain\\total\\trueSet3.txt";
        File[] files=new File(input).listFiles();
        List<File> fileList=Arrays.asList(files);
        Set<String> trueSet=new HashSet<>();
        List<File> emptyList=fileList.parallelStream()
                .filter(file->{
                    try(BufferedReader reader=new BufferedReader
                            (new InputStreamReader(new FileInputStream(file)))){
                        String url=reader.readLine();
                        String line=reader.readLine();
                        if(line==null){
                            return true;
                        }else{
                            trueSet.add(url);
                            return false;
                        }

                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    return false;
                }).collect(Collectors.toList());
        System.out.println("empty Size:"+emptyList.size());
        emptyList.parallelStream().map(file -> {
            file.delete();
            return null;
        }).collect(Collectors.toList());

        DataOperation.singleDataPrinter(output,trueSet);

    }
}
