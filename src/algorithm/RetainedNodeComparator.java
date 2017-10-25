package algorithm;

import java.util.Comparator;

/**
 * Created by lenovo on 2017/7/14.
 */
public class RetainedNodeComparator implements Comparator<QNode> {
    @Override
    public int compare(QNode o1, QNode o2) {
        double ink1=o1.getR_ink();
        double ink2=o2.getR_ink();
        if(ink1<ink2){
            return 1;
        }else if(ink1>ink2){
            return -1;
        }else
            return 0;
    }
}
