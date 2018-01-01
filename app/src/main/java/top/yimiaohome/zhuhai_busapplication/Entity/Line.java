package top.yimiaohome.zhuhai_busapplication.Entity;

/**
 * Created by yimia on 2017/12/17.
 */
public class Line{

    private String Id;
    private String FromStation;
    private String ToStation;
    public Line(){}
    public Line(String Id, String FromStation, String ToStation){
        this.Id=Id;
        this.FromStation=FromStation;
        this.ToStation=ToStation;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getFromStation() {
        return FromStation;
    }

    public void setFromStation(String fromStation) {
        this.FromStation = fromStation;
    }

    public String getToStation() {
        return ToStation;
    }

    public void setToStation(String ToStation) {
        this.ToStation = ToStation;
    }

    public String toString(){
        return "Line: "+Id+" FirstStation: "+FromStation+" ToStation: "+ToStation;
    }
}
