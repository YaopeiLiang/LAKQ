package factory;

/**
 * Created by lenovo on 2017/7/14.
 */
public class NNode {
    private int click;
    private double weight;

    public NNode(int click, double weight) {
        this.click = click;
        this.weight = weight;
    }

    public int getClick() {
        return click;
    }

    public double getWeight() {
        return weight;
    }

    public void setClick(int click) {
        this.click = click;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

}
