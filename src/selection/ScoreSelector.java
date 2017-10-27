package selection;

import general.DataOperation;
import general.ListDataPrinter;
import general.ListReader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/10/27.
 */
public class ScoreSelector {
    public static void main(String[] args) {
        //String input="D:\\LocKey\\AOL\\userTypeFreq.txt";
        //String output="D:\\LocKey\\AOL\\usrScore.txt";
        String input="D:\\LocKey\\AOL\\graph\\query2domain.txt";
        String output="D:\\LocKey\\AOL\\queryScore1.txt";
        ScoreSelector selector=new ScoreSelector();
      //  ListReader<ItemScore> usr=selector.getUserSelector();
        ListReader<ItemScore> query=selector.getQuerySelector();
        List<ItemScore> list=selector.getList(input,query);
        selector.ptintList(output,list);
    }

    public ListReader<ItemScore> getQuerySelector(){
        ListReader<ItemScore> listReader=(BufferedReader reader)->{
            List<ItemScore> result=new ArrayList<>();
            String line,query;
            double P,logP,sum;
            while ((line=reader.readLine())!=null){
                String[] re=line.split("\t");
                query=re[0];
                sum=Double.parseDouble(re[1]);
                double score=0;
                for(int i=3;i<re.length;i+=3){
                    P=Double.parseDouble(re[i])/sum;
                    logP=Math.log10(P)/Math.log10(2);
                    score+=(-P*logP);
                }
                result.add(new ItemScore(query,score));
            }
            return result;
        };
        return listReader;
    }

    public ListReader<ItemScore> getUserSelector(){
        ListReader<ItemScore> listReader=(BufferedReader reader)->{
            List<ItemScore> result=new ArrayList<>();
            String line,usr;
            double P,logP;
            while ((line=reader.readLine())!=null){
                String[] re=line.split(" ");
                usr=re[0];
                double score=0;
                int sum=0;
                for(int i=3;i<re.length;i+=2){
                    sum+=Integer.parseInt(re[i]);
                }
                for(int i=3;i<re.length;i+=2){
                    P=Integer.parseInt(re[i])/(double)sum;
                    logP=Math.log10(P)/Math.log10(2);
                    score+=(-P*logP);
                }
                result.add(new ItemScore(usr,score));
            }
            return result;
        };
        return listReader;
    }



    public <T> List<T> getList(String path,ListReader<T> listReader){
        return DataOperation.getListData(path,listReader);
    }



    public  void ptintList(String path,List<ItemScore> list){
        ListDataPrinter<ItemScore> dataPrinter=(BufferedWriter writer, List<ItemScore> lst)->{
            for(ItemScore e:lst){
                //writer.write(e.getItem()+"\t"+e.getScore()+"\n");
                writer.write(e.getScore()+"\n");
            }
        };
        DataOperation.printListData(path,list,dataPrinter);
    }

}
