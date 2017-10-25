package metric;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by lenovo on 2017/7/6.
 */
public class SequentialClassifier {
    private static String output="D:\\LocKey\\AOL\\query\\queryType.txt";

    public static void main(String[] args) {
        Set<String> querySet=ClassfierPool.getQuerySet();
        Set<String> prev=ClassfierPool.getProQuerySet();
        querySet.removeAll(prev);

        System.out.println("prv size:"+prev.size());
        System.out.println("querySet Size:"+querySet.size());
        int limit=10000,i=0;
        BufferedWriter writer=null;
        try{
            writer=new BufferedWriter(new FileWriter(output,true));
            for(String query:querySet){
                ExtractOperation operation=new ExtractOperation(query);
                i++;
                String result=operation.execute();
                if(result!=null){
                    writer.write(result+"\n");
                }
                if(i%10000==0){
                    try{
                        TimeUnit.SECONDS.sleep(1);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
                if(i>limit)
                    break;
            }
        }catch (IOException e){

        }finally {
            try{
                if(writer!=null)
                    writer.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
