package selection;

/**
 * Created by lenovo on 2017/10/27.
 */
public class ItemScore {
    private String item;
    private double score;

    public ItemScore(String item, double score) {
        this.item = item;
        this.score = score;
    }

    public String getItem() {
        return item;
    }

    public double getScore() {
        return score;
    }
}
