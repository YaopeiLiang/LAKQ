package similarity;

import step0.Location;

/**
 * Created by lenovo on 2017/9/8.
 */
public class QueryUserLocationDecorator {

    private String query;
    private String user;
    private Location location;

    public QueryUserLocationDecorator(String query,String user,  Location location) {
        this.query = query;
        this.user = user;
        this.location = location;
    }

    @Override
    public String toString(){
        return user+","+query+","+location.getLatitude()+","+location.getLongitude();
    }

    public String getUser() {
        return user;
    }

    public String getQuery() {
        return query;
    }

    public Location getLocation() {
        return location;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
