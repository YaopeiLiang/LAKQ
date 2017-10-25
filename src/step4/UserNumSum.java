package step4;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lenovo on 2017/10/18.
 */
public class UserNumSum {
    public static void main(String[] args) {
        String path="D:\\LocKey\\AOL\\step4\\";
        getUser(path);
    }
    public static void getUser(String path){
        Set<String> set=new HashSet<>();
        File[] files=new File(path).listFiles();
        for(File file:files){
            try(BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(file)))){
                String line;
                while ((line= reader.readLine())!=null){
                    String[] re=line.split("\t");
                    set.add(re[0]);
                }
            }catch (FileNotFoundException e){

            }catch (IOException e){

            }
        }
        System.out.println(set.size());
    }
}
