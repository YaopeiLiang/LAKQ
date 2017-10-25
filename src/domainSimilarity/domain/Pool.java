package domainSimilarity.domain;

import general.DataFilter;
import general.DataOperation;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * Created by lenovo on 2017/7/19.
 */
public class Pool {
    private static String failedPath="D:\\LocKey\\AOL\\domain\\details\\unreachableDomainSet.txt";
    private static String successfulPath="D:\\LocKey\\AOL\\domain\\details\\successfulDomainSet.txt";
    private static String domianSetPath="D:\\LocKey\\AOL\\domain\\domainSet.txt";

    private static String totalSetPath="D:\\LocKey\\AOL\\domain\\total\\totalDomainSet.txt";
    private static String success="D:\\LocKey\\AOL\\domain\\total\\success.txt";
    private static int failedValve=8;
    private static int THREAD_COUNT=8500;


    public static void main(String[] args) {

        Set<String> domainSet=getDomain(totalSetPath);
        System.out.println("Domain Set,before:"+domainSet.size());

        Set<String> failedSet=getFailedSet(failedPath);
        System.out.println("Failed Set:"+failedSet.size());
        domainSet.removeAll(failedSet);

        Set<String> successfulSet=getDomain(success);
        domainSet.removeAll(successfulSet);

        System.out.println("size of set to be processed:"+ domainSet.size());
        try{
            BufferedWriter failedWriter=new BufferedWriter(new FileWriter(failedPath,true));
            BufferedWriter successfulWriter=new BufferedWriter(new FileWriter(success,true));

            Set<ContentDownloader> contentDownloaderSet=domainSet.stream()
                    .map(domain->{
                        return new ContentDownloader(domain,successfulWriter,failedWriter);
                    }).collect(Collectors.toSet());

            downloadContent(contentDownloaderSet,successfulWriter,failedWriter);

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static List<String> downloadContent(Set<ContentDownloader> downloaders,
                                               BufferedWriter successfulWriter,
                                               BufferedWriter failedWriter){

        ExecutorService executor=Executors.newFixedThreadPool(THREAD_COUNT);

        List<CompletableFuture<String>> list=downloaders.stream().map(
                downloader-> CompletableFuture.supplyAsync(
                        ()->downloader.download()
                ,executor)
        ).collect(Collectors.toList());
        executor.shutdown();

        List<String> result=list.stream().map(CompletableFuture::join).collect(Collectors.toList());

        while (!executor.isTerminated()){

        }
        try{
            if(successfulWriter!=null){
                successfulWriter.close();
            }
            if(failedWriter!=null){
                failedWriter.close();
            }

        }catch (IOException e){
            e.printStackTrace();
        }

        return result;
    }


    public static Set<String> getDomain(String input){
        DataFilter<String> dataFilter=(BufferedReader reader)->{
            Set<String> set=new HashSet<>();
            String line;
            while ((line=reader.readLine())!=null){
                set.add(line);
            }
            return set;
        };
        return DataOperation.getFilterSet(input,dataFilter);
    }

    public static Set<String> getFailedSet(String input){
        DataFilter<String> filter=(BufferedReader reader)->{
            String line;
            Map<String,Integer> map=new HashMap<>();
            while ((line=reader.readLine())!=null){
                if(map.containsKey(line)){
                    int val=map.get(line);
                    val+=1;
                    map.put(line,val);
                }else{
                    map.put(line,1);
                }
            }
            Set<String> set=new HashSet<>();
            for(Map.Entry<String,Integer> item:map.entrySet()){
                int val=item.getValue();
                if(val<failedValve){
                    set.add(item.getKey());
                }
            }
            return set;
        };
        return DataOperation.getFilterSet(input,filter);
    }
}
