package factory;

import step0.Location;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2017/7/14.
 */
public class DomainLocationFactory {
    private static String input="D:\\LocKey\\AOL\\domain\\normalizedDomainCor";

    private static Map<String, Location> map=null;

    public static Map<String,Location> getMap(){
        if(map==null){
            map=new HashMap<>();
            String line,domain;
            double latittude,longitude;
            try(BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(input)))){
                while ((line=reader.readLine())!=null){
                    String[] re=line.split("\t");
                    domain=re[0];
                    latittude=Double.parseDouble(re[1]);
                    longitude=Double.parseDouble(re[2]);
                    Location loc=new Location(latittude,longitude);
                    map.put(domain,loc);
                }
            }catch (FileNotFoundException e){

            }catch (IOException e){

            }
        }
        return map;
    }
}
