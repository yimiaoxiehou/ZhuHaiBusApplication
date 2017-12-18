package top.yimiaohome.zhuhai_busapplication.Entity;

/**
 * Created by yimia on 2017/12/17.
 */
public class Line {

    private String Id;
    private String Name;
    private String LineNumber;
    private String FromStation;
    private String ToStation;

    Line(String Id,String Name,String LineNumber,String FromStation,String ToStation){
        this.Id=Id;
        this.Name=Name;
        this.LineNumber=LineNumber;
        this.FromStation=FromStation;
        this.ToStation=ToStation;
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

    public String getFromStation() {
        return FromStation;
    }

    public String getToStation() {
        return ToStation;
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

    public void setFromStation(String fromStation) {
        FromStation = fromStation;
    }

    public void setToStation(String toStation) {
        ToStation = toStation;
    }
}
