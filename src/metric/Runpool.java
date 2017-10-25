package metric;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lenovo on 2017/7/6.
 */
public class Runpool {
    //private static String output="D:\\LocKey\\AOL\\query\\queryType.txt";
    //private static String input="D:\\LocKey\\AOL\\query\\querySet.txt";
    private static String input="D:\\LocKey\\AOL\\query\\queryType.txt";
    private static String output="D:\\LocKey\\AOL\\query\\queryTypeType.txt";
    private static final int THREAD_COUNT=10;

    public static void main(String[] args) {
       // Set<String> querySet=ClassfierPool.getQuerySet();
        //Set<String> prev=ClassfierPool.getProQuerySet();
        Set<Pair> querySet=FurtherClassifier.getQuerySet(input);
        Set<Pair> prev=FurtherClassifier.getQuerySet(output);

        querySet.removeAll(prev);

        ExecutorService pool= Executors.newFixedThreadPool(THREAD_COUNT);

        System.out.println("prv size:"+prev.size());
        System.out.println("querySet Size:"+querySet.size());
        int limit=1_000,i=0;
        BufferedWriter writer=null;
        try{
            writer=new BufferedWriter(new FileWriter(output,true));
            for(Pair pair:querySet){
                Runnable task=new RunClassifier(writer,pair.getQuery(),pair.getType());
                pool.submit(task);
                ++i;
                if(i>limit)
                    break;
            }
        }catch (IOException e){

        }

        pool.shutdown();
        while (!pool.isTerminated())
            ;

        if(writer!=null){
            try{
                writer.close();
            }catch (IOException e){

            }
        }
    }
}
