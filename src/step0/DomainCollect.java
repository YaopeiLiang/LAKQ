package step0;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by lenovo on 2017/6/29.
 */
public class DomainCollect {
    private static String input="D:\\LocKey\\smoothUrl2Loc";
    private static String  output="D:\\LocKey\\AOL\\domain\\domainCity";

    public static void main(String[] args) {
        printDomainCity();
    }

    public static void printDomainCity(){
        Map<String,String> domainCity=getDomain();
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(output))){
            System.out.println("print");
            for(Map.Entry<String,String> dc:domainCity.entrySet()){
                writer.write(dc.getKey()+"\t"+dc.getValue()+"\n");
            }
        }catch(IOException e){

        }
    }



    public static Map<String,String> getDomain(){
        Map<String,String> domainCity=new HashMap<>();

        String line="";
        try(BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(input)))){
                String url,city="";
                int score=0;
                while((line=reader.readLine())!=null){
                    String[] re=line.split("\t");
                    //域名不合法或者后面没有地理信息
                    if(re.length==1){
                        continue;
                    }
                    url=re[0];
                    Map<String,Integer> cityScore=new HashMap<>();
                    for(int i=1;i<re.length;i+=2){
                       //城市分数
                        try{
                            score=Integer.parseInt(re[i+1]);
                        }catch (NumberFormatException e){
                            System.out.println(line);
                        }

                       //过去的分数
                        Integer prevScore=cityScore.get(re[i]);
                        //同名城市
                        if(prevScore!=null){
                            score+=prevScore;
                        }

                        cityScore.put(re[i],score);
                    }
                    score=-1;
                    //获得得分最大的城市
                    for(Map.Entry<String,Integer>cs:cityScore.entrySet()){
                        int s=cs.getValue();
                        if(s>score){
                            score=s;
                            city=cs.getKey();
                        }
                    }
                    domainCity.put(url,city);
                }
        }catch(FileNotFoundException e){

        }catch (IOException e){

        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return domainCity;
    }


    public static Set<String> getIlleaglDomain() {
        Set<String> illeaglDomain=new HashSet<>();
        int counter=0;
        int count=0;
        try(BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(input)))){
                String line;
                while((line=reader.readLine())!=null){

                    String[] re=line.split("\t");
                    if(re.length%2==0){
                        illeaglDomain.add(line);
                        System.out.println(line);
                    }
                }

        }catch(FileNotFoundException e){

        }catch (IOException e){

        }
        System.out.println("Illegal domain size:"+illeaglDomain.size());
        return illeaglDomain;
    }
}
