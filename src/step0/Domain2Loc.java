package step0;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2017/6/29.
 */
public class Domain2Loc {
    private static String input="D:\\LocKey\\smoothLoc2Cor";
    private static String domainInput="D:\\LocKey\\AOL\\domain\\domainCity";
    private static String output="D:\\LocKey\\AOL\\domain\\domainCor";

    public static void main(String[] args) {
        printUrlCity();
    }

    public static void printUrlCity(){
        Map<String,String> domainCity=getDomain();
        Map<String,Location> cityCor=getCityCor();
        String domain,city;
        Location loc;
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(output))){
            for(Map.Entry<String,String>dc:domainCity.entrySet()){
               domain=dc.getKey();
               city=dc.getValue();
               loc=cityCor.get(city);
               writer.write(domain+"\t"+loc.getLatitude()+"\t"+loc.getLongitude()+"\n");
            }
        }catch (IOException e){

        }
    }

    public static Map<String,String> getDomain(){
        Map<String,String>domainCity=new HashMap<>();
        String line;
        try(BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(domainInput)))){
            while((line=reader.readLine())!=null){
                String[] re=line.split("\t");
                domainCity.put(re[0],re[1]);
            }
        }catch(FileNotFoundException e){

        }catch (IOException e){

        }
        return domainCity;
    }



    public static Map<String,Location> getCityCor(){
        Map<String,Location> cityCor=new HashMap<>();
        String line,city;
        double latitude,longitude;
        try(BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(input)))){
            while ((line=reader.readLine())!=null){
                String[] re=line.split("\t");
                Location loc=new Location(Double.parseDouble(re[1]),Double.parseDouble(re[2]));
                cityCor.put(re[0],loc);
            }
        }catch(FileNotFoundException e){

        }catch (IOException e){

        }
        return cityCor;
    }
}
