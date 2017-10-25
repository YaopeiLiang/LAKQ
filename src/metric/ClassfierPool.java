package metric;

import general.DataOperation;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

/**
 * Created by lenovo on 2017/7/5.
 */
public class ClassfierPool {
    private static final int THREAD_COUNT=600;

    private static String xmlRequest=getXMLFIle();
    private static String output="D:\\LocKey\\AOL\\query\\queryType.txt";
    private static String input="D:\\LocKey\\AOL\\query\\querySet.txt";
    private static String proInput="D:\\LocKey\\AOL\\query\\pro.txt";


    public static void main(String[] args) {
        Set<String> querys=getQuerySet();
        Set<String> proQuery=getProQuerySet();

        System.out.println("proQuery size:"+proQuery.size());
        System.out.println("query Size:"+querys.size());

        querys.removeAll(proQuery);
        int limit=3000;
        int i=0;
        int quota=1000;
        int serverCounter=0;
        BufferedWriter writer=null;
        ExecutorService pool= Executors.newFixedThreadPool(THREAD_COUNT);
        Future<String>[] futures=new Future[quota];
        try{
            writer=new BufferedWriter(new FileWriter(output,true));

            for(String query:querys){
                Callable<String> task=new CallClassifier(query);
                serverCounter++;
                futures[i++]=pool.submit(task);
                if(i>=quota){
                    i=0;
                    for(int j=0;j<quota;++j){
                        try{
                            String result=futures[j].get();
                            System.out.println(result);
                            if(result!=null){
                                String[] re=result.split("\t");
                                if(re.length==3){
                                    writer.write(result+"\n");
                                }
                            }
                        }catch (ExecutionException e){
                            e.printStackTrace();
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }

                    }
                }
                if(serverCounter>limit)
                    break;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        pool.shutdown();
        while(!pool.isTerminated()){

        }

        if(writer!=null){
            try{
                writer.flush();
                writer.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    public static Set<String> getQuerySet(){
        return DataOperation.singleDataGetter(input);
    }

    public static Set<String> getProQuerySet(){
      return DataOperation.getFilterSet(output,(BufferedReader reader)->{
          Set<String> result=new HashSet<>();
          String line;
          while((line=reader.readLine())!=null){
              String[] re=line.split("\t");
              result.add(re[0]);
          }
          return result;
      });
    }



    public static String getXMLFIle(){
        StringBuilder sb=new StringBuilder();
        String path="src//metric//NewFile.xml";
        try {
            BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(path)));
            String line;
            while((line=reader.readLine())!=null){
                sb.append(line);
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sb.toString();
    }
}
