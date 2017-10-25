package domainSimilarity.content;

import porter.QueryFilter;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lenovo on 2017/7/20.
 */
public class DomainContentPorter {

    private static String reg = "[^\\x00-\\x7F]";
    private static String regCn="[\u4e00-\u9fa5]";
    private String fromFileName;
    private String toFileName;

    public DomainContentPorter(String fromFileName,String toFileName){
        this.fromFileName=fromFileName;
        this.toFileName=toFileName;
    }

    public String porterConvert(){
        BufferedWriter writer;
        BufferedReader reader;
        String domain,content;
        try{
            reader=new BufferedReader(new InputStreamReader(new FileInputStream(fromFileName)));
            writer=new BufferedWriter(new FileWriter(toFileName));

            domain=reader.readLine();
            writer.write(domain+"\n");

            while ((content=reader.readLine())!=null){

                Pattern pat = Pattern.compile(regCn);
                Matcher mat=pat.matcher(content);
                if(mat.find()){
                    System.out.println("含有中文"+fromFileName);
                    System.out.println(content);
                }

                content = content.replaceAll(reg, "");
                content= QueryFilter.filterString(content);
                writer.write(content+"\n");
            }
            reader.close();
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String fromName="D:\\LocKey\\AOL\\domain\\domainContent\\condomanhattan-com";
        String toName="D:\\LocKey\\AOL\\domain\\domainContentPorter\\condomanhattan-com";
        DomainContentPorter domainContentPorter=new DomainContentPorter(fromName,toName);
        domainContentPorter.porterConvert();

    }
}
