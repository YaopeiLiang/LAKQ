package domainSimilarity.index;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import porter.QueryAnalyzer;

import javax.xml.soap.Text;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by lenovo on 2017/7/20.
 */
public class IndexMaker {


    public static void main(String[] args) {

        makeIndex();
    }
    public static void makeIndex(){
        String input_dir="D:\\LocKey\\AOL\\domain\\local\\newNeed";
        //String input_dir="D:\\LocKey\\AOL\\domain\\local\\newNeed";
        String output="D:\\LocKey\\AOL\\domain\\local\\newIndex";
        String name="";
       // Analyzer analyzer=new SmartChineseAnalyzer();
       // Analyzer analyzer=new QueryAnalyzer();
        //Analyzer analyzer=new StandardAnalyzer();
        Analyzer analyzer=new EnglishAnalyzer();
        try{
            Directory directory= FSDirectory.open(Paths.get(output));
            IndexWriterConfig indexWriterConfig=new IndexWriterConfig(analyzer);
            indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            IndexWriter indexWriter=new IndexWriter(directory,indexWriterConfig);

            File[] files=new File(input_dir).listFiles();
            int i=0;
            for(File file:files){
                i++;
                if(i>100)
                    break;
                name=file.getName();
                String url="";
                StringBuilder builder=new StringBuilder();
                try(BufferedReader reader= new BufferedReader(new InputStreamReader(new FileInputStream(file)))){
                    url=reader.readLine();
                    String line;
                    while((line=reader.readLine())!=null){
                        builder.append(line);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }

                Document document=new Document();
                document.add(new Field("url",url, TextField.TYPE_STORED));
                document.add(new Field("body",builder.toString(),TextField.TYPE_STORED));
                indexWriter.addDocument(document);
            }

            //关闭操作
            indexWriter.close();
            directory.close();
        }catch (IOException e){

        }catch (IllegalArgumentException e){
            System.out.println(name);
            e.printStackTrace();
        }

    }
}
