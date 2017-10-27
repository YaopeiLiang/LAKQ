package general;

import metric2.MapMapDataCollector;
import metric2.MapmapDtatPrinter;
import metric2.PNode;

import java.io.*;
import java.util.*;

/**
 * Created by lenovo on 2017/7/2.
 */
public class DataOperation {

    public static <T> List<T> getListData(String path,ListReader<T> listReader){
        try(BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(path)))){
            return listReader.readList(reader);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;

    }

    public static <T> Set<T> getFilterSet(String input,DataFilter<T> dataFilter){
        try(BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(input)))){
            return dataFilter.filterSet(reader);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public static Set<String> getCombinedDataSet(String input,DataSetGetter dataSetGetter){
        Set<String> result=new HashSet<>();
        File[] files=new File(input).listFiles();
        for(File file:files){
            System.out.println(file);
            try(BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(file)))){
                dataSetGetter.getDataSet(reader,result);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return result;
    }

    public static <T,R,U> Map<T,Map<R,U>> mapMapDataGetter(String input, MapMapDataCollector collector){
        Map<T,Map<R,U>> map=new HashMap<>();
        File[] files=new File(input).listFiles();
        for(File file:files){
            try(BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(file)))){
                collector.collect(reader,map);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return map;
    }

    public static <T,R,U> void mapMapDataPrinter(String output, Map<String,Map<String, PNode>>data,
                                                 MapmapDtatPrinter printer){
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(output))){
            printer.print(writer,data);
        }catch (IOException e){

        }
    }

    public static <T> void filterData(String input,String output,Set<T> remnant,DataPrinter<T> dataPrinter){
        File[] files=new File(input).listFiles();
        int counter=1;
        for(File file:files){
            System.out.println(file);

            StringBuilder fileName=new StringBuilder();
            fileName.append(output).append(counter++).append(".txt");
            System.out.println(fileName.toString());

            BufferedWriter writer=null;
            try(BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(file)))){
                writer=new BufferedWriter(new FileWriter(fileName.toString()));
                dataPrinter.printData(reader,writer,remnant);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                if(writer!=null){
                    try{
                        writer.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static <T> void singleDataPrinter(String output,Set<T> set){
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(output))){
            for(T e:set){
                writer.write(e+"\n");
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static  Set<String> singleDataGetter(String input){
        Set<String> result=new HashSet<>();
        try(BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(input)))){
            String line;
            while((line=reader.readLine())!=null){
                result.add(line);
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }

    public static <T,R> Map<T,R> loadData(String input, DataLoader<T,R> dataLoader){
        try(BufferedReader reader=new BufferedReader(new InputStreamReader(new FileInputStream(input)))){
            return dataLoader.loadData(reader);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public static <T,R> void printMapData(String output,Map<T,R> data,MapDataPrinter<T,R> dataPrinter){
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(output))){
            dataPrinter.printMapData(writer,data);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static <T> void printListData(String path,List<T> data,ListDataPrinter<T> dataPrinter){
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(path))){
            dataPrinter.listDataPrinter(writer,data);
        }catch (IOException e){
            e.printStackTrace();
        }
    }



    /*
    public static <T> void printData(String output,Set<T> remnant,DataPrinter<T> dataPrinter){
        try(BufferedWriter writer=new BufferedWriter(new FileWriter(output))){
            dataPrinter.printData(writer,remnant);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    */
}
