package top.yimiaohome.zhuhai_busapplication;

/**
 * Created by yimia on 2017/12/15.
 */

public class Bus {

    private String BusNumber;
    private String CurrentStation;
    private int LastPosition;

    Bus(String BusNumber, String CurrentStation, int LastPosition){
        this.BusNumber=BusNumber;
        this.CurrentStation=CurrentStation;
        this.LastPosition=LastPosition;
    }

    public void setBusNumber(String busNumber) {
        BusNumber = busNumber;
    }

    public void setCurrentStation(String currentStation) {
        CurrentStation = currentStation;
    }

    public void setLastPosition(int lastPosition) {
        LastPosition = lastPosition;
    }

    public String getBusNumber() {
        return BusNumber;
    }

    public String getCurrentStation() {
        return CurrentStation;
    }

    public int getLastPosition() {
        return LastPosition;
    }
}
class Line {

    private String Id;
    private String Name;
    private String LineNumber;

    Line(String Id,String Name,String LineNumber){
        this.Id=Id;
        this.Name=Name;
        this.LineNumber=LineNumber;
    }

    public String getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getLineNumber() {
        return LineNumber;
    }

    public void setId(String id) {
        Id = id;
    }

    public void setLineNumber(String lineNumber) {
        LineNumber = lineNumber;
    }

    public void setName(String name) {
        Name = name;
    }
}
class Station{

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