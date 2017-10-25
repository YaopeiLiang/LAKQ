package metric2;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;


/**
 * Created by lenovo on 2017/7/17.
 */
public class Triple<T extends Comparable,R extends Comparable,U> {
    public static void main(String[] args) {
        List<Triple> list=new ArrayList<>();
        list.add(new Triple(1,2,3));
        Triple triple=list.get(0);

    }

    private T first;
    private R second;
    private U thrid;

    public Triple(T first, R second, U thrid) {
        this.first = first;
        this.second = second;
        this.thrid = thrid;
    }

    public T getFirst() {
        return first;
    }

    public R getSecond() {
        return second;
    }

    public U getThrid() {
        return thrid;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public void setSecond(R second) {
        this.second = second;
    }

    public void setThrid(U thrid) {
        this.thrid = thrid;
    }
}
