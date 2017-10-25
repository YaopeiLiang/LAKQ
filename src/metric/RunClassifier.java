package metric;

import java.io.*;
import java.net.Socket;

/**
 * Created by lenovo on 2017/7/5.
 */
public class RunClassifier implements Runnable{
    private static String XMLRequest=ClassfierPool.getXMLFIle();

    private BufferedWriter writer;
    private String query;
    private String type;


    public RunClassifier(BufferedWriter writer, String query) {
        this.writer = writer;
        this.query = query;
    }

    public RunClassifier(BufferedWriter writer,String query,String type){
        this.writer=writer;
        this.query=query;
        this.type=type;
    }

    @Override
    public void run(){
        ExtractOperation operation=null;
        if(type==null)
             operation=new ExtractOperation(query);
        else
            operation=new ExtractOperation(query,type);

        String result=operation.execute();
        if(result!=null){
            try{
                writer.write(result+"\n");
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

}
