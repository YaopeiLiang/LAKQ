package step0;

/**
 * Created by lenovo on 2017/6/29.
 */
public class Location {
    private double latitude;
    private double longitude;

    public Location(double latitude,double longitude){
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public int hashCode(){
        return String.valueOf(latitude).hashCode()+String.valueOf(longitude).hashCode();
    }

    @Override
    public boolean equals(Object object){
        Location loc=(Location)object;
        return latitude==loc.latitude
                &&longitude==loc.longitude;
    }
}
