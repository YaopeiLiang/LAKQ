package domainSimilarity.domain;

import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.CompletionException;

/**
 * Created by lenovo on 2017/7/19.
 */
public class ContentDownloader implements Runnable{

    public static void main(String[] args) throws IOException{
       // Files.createDirectory(Paths.get(path));
        String url="http://agebb.missouri.edu";
        ContentDownloader downloader=new ContentDownloader(url);
        downloader.run();
    }

    //private static String destPath="D:\\LocKey\\AOL\\domain\\rawFiles\\";
    private static String destPath="D:\\LocKey\\AOL\\domain\\local\\left\\";
    private String domain;
    private BufferedWriter successfulWriter;
    private BufferedWriter failedWriter;

    public ContentDownloader(String domain){
        this.domain=domain;
    }

    public ContentDownloader(String domain,BufferedWriter successfulWriter,BufferedWriter failedWriter){
        this.domain=domain;
        this.successfulWriter=successfulWriter;
        this.failedWriter=failedWriter;
    }

    @Override
    public void run(){
        download();
    }

    public String download(){
        String content=getHtml();
        writeContent(content);
        return null;
    }

    public String getHtml(){
        if(domain==null){
            return null;
        }
        try{
            //Document html= Jsoup.connect(domain).get();
            Document html= Jsoup.connect(domain).timeout(500_000).get();
            return html.text();
        }catch (IllegalArgumentException |CompletionException e){
            System.out.println(domain);
            return null;
        }catch (IOException e){
            return null;
        }
    }

    public void writeContent(String content){
        if(content!=null){
            String name=domain.replace("://","").replace("/","我")
                    .replace("?","他")
                    .replace(":","你");
            String output=destPath+name+".txt";
            System.out.println(output);
            try(BufferedWriter writer=new BufferedWriter(new FileWriter(output))){
                //记录已经下载的文档
                if(successfulWriter!=null){
                    successfulWriter.write(domain+"\n");
                }
                writer.write(domain+"\n");
                writer.write(content);
                writer.flush();
            }catch (IOException e){
                e.printStackTrace();
            }
        }else {
            try{
                if(failedWriter!=null){
                    failedWriter.write(domain+"\n");
                }
                //System.out.println("cannot resolve:"+domain);
            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }



    @Override
    public int hashCode(){
        return domain.hashCode();
    }

    @Override
    public boolean equals(Object object){
        ContentDownloader downloader=(ContentDownloader)object;
        return domain.equals(downloader.domain);
    }


}
