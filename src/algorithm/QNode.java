package algorithm;

import factory.FNode;

/**
 * Created by lenovo on 2017/7/14.
 */
public class QNode {
    private double a_ink;
    private double r_ink;
    private int tag;
    private String content;
    private FNode node;

    public QNode(double a_ink, double r_ink, int tag,  String content,FNode node) {
        this.a_ink = a_ink;
        this.r_ink = r_ink;
        this.tag = tag;
        this.content=content;
        this.node = node;
    }

    @Override
    public boolean equals(Object obj){
        QNode o=(QNode)obj;
        return content.equals(o.content);
    }

    public String getContent() {
        return content;
    }

    public double getA_ink() {
        return a_ink;
    }

    public double getR_ink() {
        return r_ink;
    }

    public int getTag() {
        return tag;
    }

    public FNode getNode() {
        return node;
    }

    public void setA_ink(double a_ink) {
        this.a_ink = a_ink;
    }

    public void setR_ink(double r_ink) {
        this.r_ink = r_ink;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public void setNode(FNode node) {
        this.node = node;
    }
}
