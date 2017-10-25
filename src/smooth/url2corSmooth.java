package smooth;

import java.io.*;
import java.util.Stack;

/**
 * Created by lenovo on 2017/6/29.
 */
public class url2corSmooth {

    public static void main(String[] args) {
        int counter=0;

        String input="D:\\LocKey\\url2loc";
        String output="D:\\LocKey\\smoothUrl2Loc";
        BufferedWriter writer=null;
        String line="";

        try(BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(input)))){
            writer=new BufferedWriter(new FileWriter(output));

            while ((line=reader.readLine())!=null){
                char ch=line.charAt(line.length()-1);
                String[] re=line.split("\t");

                if(re.length==1){
                    writer.write(line+"\n");
                } else if(Character.isDigit(ch)){
                    writer.write(line+"\n");
                }else{
                    StringBuilder sb=new StringBuilder();

                    while(Character.isDigit(ch)==false){
                        sb.append(line);
                        System.out.println("prev:"+line);
                       line=reader.readLine();
                       if(line.isEmpty()){
                           continue;
                       }
                        System.out.println("next"+line);
                       ch=line.charAt(line.length()-1);
                    }
                    if(line.startsWith("http")==false)
                        sb.append(line);
                    System.out.println("combine:"+sb.toString());
                    writer.write(sb.toString()+"\n");
                    if(line.startsWith("http")){
                        System.out.println("shit:"+line);
                        writer.write(line+"\n");
                    }
                }
            }
        }catch(FileNotFoundException e){

        }catch (IOException e){

        }finally {
            if(writer!=null){
                try{
                    writer.close();
                }catch (IOException e){

                }
            }
        }
    }
}
