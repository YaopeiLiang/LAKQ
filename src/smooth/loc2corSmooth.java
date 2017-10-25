package smooth;

import java.io.*;

/**
 * Created by lenovo on 2017/6/30.
 */
public class loc2corSmooth {
    public static void main(String[] args) {
        String input="D:\\LocKey\\loc2cor";
        String output="D:\\LocKey\\smoothLoc2Cor";
        String line="";
        char ch;
        BufferedWriter writer=null;
        try(BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(input)))){
            writer=new BufferedWriter(new FileWriter(output));
            while((line=reader.readLine())!=null){
                ch=line.charAt(line.length()-1);
                if(Character.isDigit(ch)==true){
                    writer.write(line+"\n");
                }else{
                    StringBuilder sb=new StringBuilder();
                    while(Character.isDigit(ch)==false){
                        System.out.println(line);
                        sb.append(line);
                        line=reader.readLine();
                        if(line.isEmpty())
                            continue;
                        ch=line.charAt(line.length()-1);
                    }
                    sb.append(line);
                    writer.write(sb.toString()+"\n");
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
