package similarity;

import algorithm.QNode;
import general.DataOperation;
import general.ListDataPrinter;
import step0.Location;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by lenovo on 2017/9/8.
 */
public class Lazy {
    public static void main(String[] args) {
        String path="D:\\LocKey\\AOL\\queryList.txt";
       // printQueryList(queryLimit,path);
        int num=5;
        List<QueryUserLocationDecorator> queryList=loadQueryList(path).stream().limit(100).collect(Collectors.toList());
        Set<String> set=queryList.stream().map(d->d.getQuery()).collect(Collectors.toSet());
        System.out.println("set:"+set.size());
        lazy(queryList,num);
    }

    public static void lazy(List<QueryUserLocationDecorator> queryList,int num){
        double[] gamas={0.1,0.3,0.5,0.7,0.9};
        double[] alphas={0.2,0.35,0.5,0.65,0.8};
        double[] betas={0,0.25,0.5,0.75,1};
        double[] esus={1e-6,0.000_005,1e-5,0.000_05,1e-4};
        //double gama=0.3;
        double alpha=0.5;
        double beta=0.5;
        double esu=1e-5;
        for(int i=0;i<num;++i){
            double gama=gamas[i];
            //double alpha=alphas[i];
           // double beta=betas[i];
            //double esu=esus[i];
            int size=queryList.size();

            double simpleNum=0.0,userNum=0.0,locNum=0.0,userLocNum=0.0;
            double simpleSim=0.0,userSim=0.0,locSim=0.0,userLocSim=0.0;
            double simpleType=0.0,userType=0.0,locType=0.0,userLocType=0.0;

            for(QueryUserLocationDecorator item:queryList){
                String query=item.getQuery();
                String user=item.getUser();
                Location location=item.getLocation();
                double latitude=location.getLatitude(),longitude=location.getLongitude();
                List<String> queryDocList=NearbyDocumentNumber.getQueryDocList(query);


                /*
                Queue<QNode> queue=NearbyDocumentNumber.simpleQuery(query,alpha);
                //Queue<QNode> queue=NearbyDocumentNumber.simpleQueryAlgorithn(query);
                simpleNum+=NearbyDocumentNumber.show(queue,query,"Simple Query",location);
                simpleSim+=NearbyDocumentSimilarity.calSimilarity(queue,query,queryDocList,
                            "Simple Query",latitude,longitude);
                simpleType+=QueryKeyword.getScore(queue,query);

                Queue<QNode> queue2=NearbyDocumentNumber.queryUser(query,user,gama,
                            alpha,beta);
                //Queue<QNode> queue2=NearbyDocumentNumber.queryUserAlgorithm(query,user,gama);
                userNum+=NearbyDocumentNumber.show(queue2,query,"User",location);
                userSim+=NearbyDocumentSimilarity.calSimilarity(queue2,query,queryDocList,"User",
                            latitude,longitude);
                userType+=QueryKeyword.getScore(queue2,query);
                */


               // Queue<QNode> queue1=NearbyDocumentNumber.queryLocation(query,location,alpha,beta,esu);
                Queue<QNode> queue1=NearbyDocumentNumber.queryLocationAlgorithm(query,location);
                locNum+=NearbyDocumentNumber.show(queue1,query,"Location",location);
                locSim+=NearbyDocumentSimilarity.calSimilarity(queue1,query,queryDocList,"Location",
                        latitude,longitude);
                locType+=QueryKeyword.getScore(queue1,query);

                Queue<QNode> queue3=NearbyDocumentNumber.queryUserLocation(query,user,location,gama,
                        alpha,beta,esu);
                //Queue<QNode> queue3=NearbyDocumentNumber.queryUserLocationAlgorithm(query,user,location,gama);
                userLocNum+=NearbyDocumentNumber.show(queue3,query,"User Location",location);
                userLocSim+=NearbyDocumentSimilarity.calSimilarity(queue3,query,queryDocList,"User Location",
                        latitude,longitude);
                userLocType+=QueryKeyword.getScore(queue3,query);
            }
            System.out.println("esu="+esu);
            /*
            System.out.println("------Simple Query Number-------:"+simpleNum/size);
            System.out.println("-------userQuery Number---------:"+userNum/size);
            */
            System.out.println("-------Loc Query  Number---------:"+locNum/size);
            System.out.println("-------userLocQuery  Number------:"+userLocNum/size);

            System.out.println("esu="+esu);
            /*
            System.out.println("------Simple Query Similarity-------:"+simpleSim/size);
            System.out.println("-------userQuery Similarity---------:"+userSim/size);
            */
            System.out.println("-------Loc Query  Similarity---------:"+locSim/size);
            System.out.println("-------userLocQuery  Similarity------:"+userLocSim/size);

            System.out.println("esu="+esu);
            /*
            System.out.println("------Simple Query Type-------:"+simpleType/size);
            System.out.println("-------userQuery Type---------:"+userType/size);
            */
            System.out.println("-------Loc Query  Type---------:"+locType/size);
            System.out.println("-------userLocQuery  Typer------:"+userLocType/size);
        }
    }


    public static List<QueryUserLocationDecorator> loadQueryList(String path){
        List<QueryUserLocationDecorator> result=new ArrayList<>();
        try{
            BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(path)));
            String line;
            while ((line=reader.readLine())!=null){
                String[] re=line.split("\t");
                String query=re[0];
                String user=re[1];
                Location location=new Location(Double.parseDouble(re[2]),
                        Double.parseDouble(re[3]));
                QueryUserLocationDecorator item=new QueryUserLocationDecorator(query,user,location);
                result.add(item);
            }
        }catch (IOException e){

        }
        return result;
    }

    public static void printQueryList(int queryLimit,String path){
        List<String> queryList=NearbyDocumentNumber.getQueryList(queryLimit);
        ListDataPrinter<String> printer=(BufferedWriter writer, List<String> list)->{

           for(String query:list){
               String user=NearbyDocumentNumber.getUser(query);
               Location location=NearbyDocumentNumber.getLocation(query);
               writer.write(query+"\t"+user+"\t"+location.getLatitude()+"\t"
                           +location.getLatitude()+"\n");
           }
        };
        DataOperation.printListData(path,queryList,printer);
    }

}
