package factory;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2017/7/14.
 */
public class GraphFactory {

    private static  Map<String,FNode> userMap=null;
    private static  Map<String,FNode> query2UserMap=null;
    private static  Map<String,FNode> query2DomainMap=null;
    private static  Map<String,FNode> domain2QueryMap=null;
    private static Map<String,FNode> user2domainMap=null;
    private static Map<String,FNode> user2QueryMap=null;


    public static Map<String,FNode> getUser2QueryMap(){
        String input="D:\\LocKey\\AOL\\graph\\user2query.txt";
        if(user2QueryMap==null){
            user2QueryMap=getMap(input);
        }
        return user2QueryMap;
    }
    public static Map<String,FNode> getUser2DomainMap(){
        String input="D:\\LocKey\\AOL\\graph\\user2domain.txt";
        if(user2domainMap==null){
            user2domainMap=getMap(input);
        }
        return user2domainMap;
    }

    public static void clearUser2DomainMap(){
        user2domainMap=null;
    }



    public static Map<String,FNode> getUserMap(){
        String input="D:\\LocKey\\AOL\\graph\\user2query.txt";
        if(userMap==null){
            userMap=getMap(input);
        }
        return userMap;
    }

    public static void clearUserMap(){
        userMap=null;
    }

    public static Map<String, FNode> getQuery2UserMap() {
        String input="D:\\LocKey\\AOL\\graph\\query2user.txt";
        if(query2UserMap==null){
            query2UserMap=getMap(input);
        }
        return query2UserMap;
    }

    public static void clearQuery2UserMap(){
        query2UserMap=null;
    }

    public static Map<String, FNode> getQuery2DomainMap() {
        String input="D:\\LocKey\\AOL\\graph\\query2domain.txt";
        if(query2DomainMap==null){
            query2DomainMap=getMap(input);
        }
        return query2DomainMap;
    }

    public static void clearQuery2DomainMap(){
        query2DomainMap=null;
    }

    public static Map<String, FNode> getDomain2QueryMap() {
        String input="D:\\LocKey\\AOL\\graph\\domain2query.txt";
        if(domain2QueryMap==null){
            domain2QueryMap=getMap(input);
        }
        return domain2QueryMap;
    }

    public static void clearDomain2QueryMap(){
        domain2QueryMap=null;
    }

    public static Map<String,FNode> getMap(String input){
        Map<String,FNode> map=new HashMap<>();

        String line,id,appendex;
        int totalClick,click,i;
        double weight;

        try(BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(input)))){
                while ((line=reader.readLine())!=null){
                    String[] re=line.split("\t");
                    id=re[0];
                    totalClick=Integer.parseInt(re[1]);
                    FNode node=new FNode(totalClick);

                    for(i=2;i<re.length;i+=3){
                        appendex=re[i];
                        click=Integer.parseInt(re[i+1]);
                        weight=Double.parseDouble(re[i+2]);
                        node.addEdge(appendex,click,weight);
                    }
                    map.put(id,node);
                }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return map;
    }
}
