package metric;

/**
 * Created by lenovo on 2017/7/16.
 */
public class Pair {
    private String query;
    private String type;

    public Pair(String query, String type) {
        this.query = query;
        this.type = type;
    }

    @Override
    public boolean equals(Object obj){
        Pair pair=(Pair)obj;
        return pair.query.equals(query);
    }

    @Override
    public int hashCode(){
        return query.hashCode();
    }

    public String getQuery() {
        return query;
    }

    public String getType() {
        return type;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setType(String type) {
        this.type = type;
    }
}
