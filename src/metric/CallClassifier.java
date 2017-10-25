package metric;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.Callable;

/**
 * Created by lenovo on 2017/7/5.
 */
public class CallClassifier implements Callable<String>{
    private static String XMLRequest=ClassfierPool.getXMLFIle();


    private String query;

    public CallClassifier(String query) {
        this.query = query;
    }

    @Override
    public String call(){
       ExtractOperation operation=new ExtractOperation(query);
       return operation.execute();
    }


}
