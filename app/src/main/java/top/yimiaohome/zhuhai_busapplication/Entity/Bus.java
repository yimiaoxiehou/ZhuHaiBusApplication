package top.yimiaohome.zhuhai_busapplication.Entity;

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

