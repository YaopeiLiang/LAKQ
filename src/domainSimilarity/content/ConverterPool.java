package domainSimilarity.content;

import domainSimilarity.domain.Pool;
import general.DataOperation;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by lenovo on 2017/7/20.
 */
public class ConverterPool {

    public static void main(String[] args){
        convert();
    }

    public static void convert(){
        {
            String needPath="D:\\LocKey\\AOL\\domain\\local\\need.txt";
            String filePath="D:\\LocKey\\AOL\\domain\\total\\real4";
            String outputPath="D:\\LocKey\\AOL\\domain\\local\\newNeed\\";
            Set<String> needSet= DataOperation.singleDataGetter(needPath);
            needSet=needSet.parallelStream()
                    .map(str->{
                        str=str.replace("://","").replace("/","我")
                                .replace("?","他")
                                .replace(":","你");
                        return str+".txt";
                    }).collect(Collectors.toSet());

            Path path= Paths.get(filePath);
            List<DomainContentPorter> list=new ArrayList<>();
            int i=0;
            try(DirectoryStream<Path> stream= Files.newDirectoryStream(path)){
                for(Path entry:stream){
                    String name=entry.getFileName().toString();
                    if(needSet.contains(name)){
                        list.add(new DomainContentPorter(entry.toString(),
                                outputPath+name));
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }

            list.parallelStream()
                    .map(DomainContentPorter::porterConvert).collect(Collectors.toList());
        }
    }
}
