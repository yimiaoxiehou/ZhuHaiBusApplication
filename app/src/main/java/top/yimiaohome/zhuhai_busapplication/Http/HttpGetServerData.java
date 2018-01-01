package top.yimiaohome.zhuhai_busapplication.Http;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import okhttp3.HttpUrl;
import top.yimiaohome.zhuhai_busapplication.Entity.Bus;
import top.yimiaohome.zhuhai_busapplication.Entity.Line;
import top.yimiaohome.zhuhai_busapplication.Entity.Station;
/**
 * Created by yimia on 2017/12/15.
 */

public class HttpGetServerData {

    static final String TAG = "HttpGetServerData";
    static final String HOST = "www.zhbuswx.com";
    static final String SCHEME = "http";
    static final String PATH = "Handlers/BusQuery.ashx";

    public static ArrayList<Bus> GetBusListOnRoad(String lineName, String fromStation){

        //ArrayList<Bus> runningBus = new ArrayList<>();
        HttpUrl url = new HttpUrl.Builder()
                .scheme(SCHEME)
                .host(HOST)
                .addPathSegments(PATH)
                .addQueryParameter("handlerName", "GetBusListOnRoad")
                .addQueryParameter("lineName", lineName)
                .addQueryParameter("fromStation", fromStation)
                .build();

        try {
            String result = new HttpGetTask().execute(url).get();
            if (result!=null) {
                Log.d(TAG, "queryRunningBus: response is " + result);
                Gson gson = new Gson();
                JsonParser parser = new JsonParser();
                JsonObject row = parser.parse(result).getAsJsonObject();
                int flag = gson.fromJson(row.getAsJsonPrimitive("flag"), Integer.class);
                Log.d(TAG, "queryRunningBus: flag is " + flag);
                if (flag == 1002) {
                    JsonArray data = row.getAsJsonArray("data");
                    Log.d(TAG, "queryRunningBus: jsonArray is " + data.toString());
                    ArrayList<Bus> runningBus = gson.fromJson(data, new TypeToken<ArrayList<Bus>>() {
                    }.getType());
                    Log.d(TAG, "queryRunningBus: response bus data is " + data.toString());
                    return runningBus;
                }
                else
                    return null;
            }else{
                Log.d(TAG, "queryRunningBus: error response is null or response failed.");
                return null;
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static ArrayList<Line> GetLineListByLineName(String key){

        HttpUrl url = new HttpUrl.Builder()
                .scheme(SCHEME)
                .host(HOST)
                .addPathSegments(PATH)
                .addQueryParameter("handlerName", "GetLineListByLineName")
                .addQueryParameter("key", key)
                .build();
        try {
            String result = new HttpGetTask().execute(url).get();
            if (result!=null) {
                Gson gson = new Gson();
                JsonParser parser = new JsonParser();
                JsonObject row = parser.parse(result).getAsJsonObject();
                int flag = gson.fromJson(row.getAsJsonPrimitive("flag"), Integer.class);
                if (flag == 1002) {
                    JsonArray data = row.getAsJsonArray("data");
                    ArrayList<Line> lines = gson.fromJson(data, new TypeToken<ArrayList<Line>>() {
                    }.getType());
                    Log.d(TAG, "queryLines: response line data is " + data.toString());

                    return lines;
                }else
                    return null;
            }else {
                Log.d(TAG, "queryLines: error response is null or response failed.");
                return null;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Station> GetStationList(String lineId){

        HttpUrl url = new HttpUrl.Builder()
                .scheme(SCHEME)
                .host(HOST)
                .addPathSegments(PATH)
                .addQueryParameter("handlerName", "GetStationList")
                .addQueryParameter("lineId", lineId)
                .build();
        try {
            String result = new HttpGetTask().execute(url).get();
            if (result!=null) {
                Gson gson = new Gson();
                JsonParser parser = new JsonParser();
                JsonObject row = parser.parse(result).getAsJsonObject();
                int flag = gson.fromJson(row.getAsJsonPrimitive("flag"), Integer.class);
                if (flag == 1002) {
                    JsonArray data = row.getAsJsonArray("data");
                    ArrayList<Station> stations = gson.fromJson(data, new TypeToken<ArrayList<Station>>() {
                    }.getType());
                    Log.d(TAG, " GetStationList: response line data is " + data.toString());
                    return stations;
                }else
                    return null;
            }else {
                Log.d(TAG, " GetStationList: error response is null or response failed.");
                return null;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList GetBusListOnRoadWithStation(String lineName, String fromStation){
        ArrayList<Bus> runningBus = HttpGetServerData.GetBusListOnRoad(lineName, fromStation);
        ArrayList<Line> lines = HttpGetServerData.GetLineListByLineName(lineName);
        ArrayList result = new ArrayList();
        if (lines==null){
            return null;
        }else if (runningBus == null){
            return lines;
        }else{
            lines.forEach(line -> {
                if (line.getFromStation().equals(fromStation)) {
                    ArrayList<Station> stations = HttpGetServerData.GetStationList(line.getId());
                    if (stations != null) {
                        ArrayList<String> busCurrentIn = new ArrayList<>();
                        runningBus.forEach(bus -> {
                            busCurrentIn.add(bus.getCurrentStation());
                        });
                        stations.forEach(station -> {
                            result.add(station);
                            while (busCurrentIn.contains(station.getName())) {
                                result.add(runningBus.get(busCurrentIn.indexOf(station.getName())));
                                busCurrentIn.set(busCurrentIn.indexOf(station.getName()), "");
                            }
                        });
                    } else
                        return;
                }
            });
            return result;
        }
    }
}
