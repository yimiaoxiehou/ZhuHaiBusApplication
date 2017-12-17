package top.yimiaohome.zhuhai_busapplication.Entity;

/**
 * Created by yimia on 2017/12/17.
 */
public class Station{

    private String Id;
    private String Name;
    private double Lng;
    private double Lat;

    Station(String Id,String Name,double Lng,double Lat){
        this.Id=Id;
        this.Name=Name;
        this.Lng=Lng;
        this.Lat=Lat;
    }

    public String getName() {
        return Name;
    }

    public String getId() {
        return Id;
    }

    public double getLat() {
        return Lat;
    }

    public double getLng() {
        return Lng;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setId(String id) {
        Id = id;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public void setLng(double lng) {
        Lng = lng;
    }
}
