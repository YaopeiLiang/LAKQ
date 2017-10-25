package step0;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/7/14.
 */
public class DomainNormailzed {

    public static void main(String[] args) {
        String input="D:\\LocKey\\AOL\\domain\\domainCor";
        String output="D:\\LocKey\\AOL\\domain\\normalizedDomainCor";
        //getMaxMin(input);
        output(input,output);
    }

    public static void output(String input,String output){
        double lat_min=-90.0,
                lat_max=72.0,
                long_min=-176.787,
                long_max=178.0;
        double lat=lat_max-lat_min;
        double lon=long_max-long_min;
        double latitude,longitude;
        BufferedWriter writer=null;

        try(BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(input)))){
            writer=new BufferedWriter(new FileWriter(output));
            String line,domain;
            while ((line=reader.readLine())!=null){
                String[] re=line.split("\t");
                domain=re[0];
                latitude=Double.parseDouble(re[1]);
                longitude=Double.parseDouble(re[2]);

                latitude=(latitude-lat_min)/lat;
                longitude=(longitude-long_min)/lon;

                writer.write(domain+"\t"+latitude+"\t"+longitude+"\n");
            }
            writer.close();
        }catch (FileNotFoundException e){

        }catch (IOException e){

        }
    }

    public static List<Double> getMaxMin(String input){
        List<Double> result=new ArrayList<>();

        double lat_min=Double.MAX_VALUE,
                lat_max=Double.MIN_VALUE,
                long_min=Double.MAX_VALUE,
                long_max=Double.MIN_VALUE;

        double latitude,longitude;
        try(BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(input)))){
            String line;
            while ((line=reader.readLine())!=null){
                String[] re=line.split("\t");
                latitude=Double.parseDouble(re[1]);
                longitude=Double.parseDouble(re[2]);

                if(latitude<lat_min){
                    lat_min=latitude;
                }

                if(latitude>lat_max){
                    lat_max=latitude;
                }

                if(longitude<long_min){
                    long_min=longitude;
                }

                if(longitude>long_max){
                    long_max=longitude;
                }
            }
        }catch (FileNotFoundException e){

        }catch (IOException e){

        }
        System.out.println("min latitude:"+lat_min+",max latitude:"+lat_max);
        System.out.println("min longitude:"+long_min+",max longitude:"+long_max);
        result.add(lat_min);
        result.add(lat_max);
        result.add(long_min);
        result.add(long_max);
        return result;
    }
}
