package step0;

import java.net.*;
import java.io.*;
import java.util.*;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.Location;

/**
 * Created by lenovo on 2017/6/29.
 */
public class GeoipDomain {





    public static void test() {

        String src_dir="D:\\Mission\\step0\\url_set.txt";
        String des_dir="D:\\Mission\\step0\\normalized_url.txt";
        Set<String> set=new HashSet<String>();

        BufferedReader br_reader;
        BufferedWriter writer;
        String url;
        String ip;

        File database=new File("D:\\Mission\\geo\\GeoLite2-City.mmdb");

        DatabaseReader reader;
        try {
            reader = new DatabaseReader.Builder(database).build();

            br_reader=new BufferedReader(new InputStreamReader(new FileInputStream(src_dir)));
            writer=new BufferedWriter(new FileWriter(des_dir));
            String line;

            while((line=br_reader.readLine())!=null){
                String[] re=line.split("\t");
                url=re[0];
                ip=re[1];

                InetAddress ipAddress=InetAddress.getByName(ip);
                try {
                    CityResponse response = reader.city(ipAddress);

                    if(response!=null){
                        Location location = response.getLocation();
                        if(location.getLatitude()!=null){
                            String content=url+"\t"+ip+"\t"+location.getLatitude()+"\t"+location.getLongitude()+"\n";
                            writer.write(content);
                            writer.flush();
                        }
                    }
                } catch (GeoIp2Exception e) {
                    // TODO Auto-generated catch block
                    //e.printStackTrace();
                }
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
