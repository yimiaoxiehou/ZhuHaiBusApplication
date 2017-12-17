package top.yimiaohome.zhuhai_busapplication.Entity;

/**
 * Created by yimia on 2017/12/17.
 */
public class Line {

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
