package metric;

import java.io.*;
import java.net.Socket;

/**
 * Created by lenovo on 2017/7/6.
 */
public class ExtractOperation extends Thread{
    private static String XMLRequest=ClassfierPool.getXMLFIle();

    private String query;
    private String type;


    public ExtractOperation(String query) {
        this.query = query;
        type=null;
    }

    public ExtractOperation(String query,String type){
        this.query=query;
        this.type=type;
    }

    public String execute(){
        String request,response,category;
        StringBuilder result=new StringBuilder();
        request=XMLRequest.replace("Memory",query);
        if(type==null){
            response=sendRequest(request);
            if(response==null){
                return null;
            }
            category=parseStringResponse(response);

            result.append(query+"\t"+category);
        }else {
            String req="uClassify_"+type+" Topics";
            //第二次请求
            request=request.replace("uClassify_Topics",req);
            response=sendRequest(request);
            //服务器错误
            if (response==null){
                return null;
            }
            category=parseStringResponse(response);
            result.append(query+"\t"+type+"\t"+category);
        }
        return result.toString();
    }



    private String sendRequest(String request){
        StringBuilder sb=new StringBuilder();
        Socket socket=null;
        try{
            socket=new Socket("localhost",54441);
            byte[] bytes=request.getBytes("UTF-8");
            OutputStream os=socket.getOutputStream();
            os.write(bytes);
            os.flush();
            socket.shutdownOutput();

            BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            while ((line=reader.readLine())!=null){
                sb.append(line+"\n");
            }
            socket.close();
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
        return sb.toString();
    }

    private String parseStringResponse(String response){
        String[] re=response.split("\n");
        String classifier="";
        double score=-1.0;
        String temp="";
        double tempScore;
        int i=0,cbgn,cend;
        int sbgn=0,send=0;
        try{
            for(i=6;i<re.length;++i){
                if(!re[i].contains("p=")){
                    break;
                }
                cbgn=re[i].indexOf("\"");
                cend=re[i].lastIndexOf(" ");
                temp=re[i].substring(cbgn+1,cend-1);

                sbgn=re[i].lastIndexOf("=");
                send=re[i].lastIndexOf("\"");

                try{
                    tempScore=Double.parseDouble(re[i].substring(sbgn+2,send));
                    if(tempScore>score){
                        score=tempScore;
                        classifier=temp;
                    }
                }catch (NumberFormatException e){
                    return null;
                }
            }
            return classifier;
        }catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
            return null;
        }catch (NullPointerException e){
            e.printStackTrace();
            return null;
        }
    }
}
