package top.yimiaohome.zhuhai_busapplication;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import okhttp3.HttpUrl;
import okhttp3.Response;

/**
 * Created by yimia on 2017/12/15.
 */

public class HttpGetServerDatas {

    static final String TAG = "HttpGetServerDatas";
    static ArrayList<Bus> queryRunningBus(String handlerName, String lineName, String fromStation){

        //ArrayList<Bus> runningBus = new ArrayList<>();
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("www.zhbuswx.com")
                .addPathSegments("Handlers/BusQuery.ashx")
                .addQueryParameter("handlerName", handlerName)
                .addQueryParameter("lineName", lineName)
                .addQueryParameter("fromStation", fromStation)
                .build();

        try {
            Response response = new HttpGetTask().execute(url).get();
            String result = response.body().string();
            Log.d(TAG, "queryRunningBus: response is "+result);
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonObject row = parser.parse(result).getAsJsonObject();
            int flag = gson.fromJson(row.getAsJsonPrimitive("flag"),Integer.class);
            Log.d(TAG, "queryRunningBus: flag is "+flag);
            JsonArray data = row.getAsJsonArray("data");
            Log.d(TAG, "queryRunningBus: jsonarray is "+data.toString());
            ArrayList<Bus> runningBus = gson.fromJson(data,new TypeToken<ArrayList<Bus>>(){}.getType());
            Log.d(TAG, "queryRunningBus: response bus data is "+data.toString());

            return runningBus;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    static ArrayList<Line> queryLines(String handlerName,String key){

        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("www.zhbuswx.com")
                .addPathSegments("Handlers/BusQuery.ashx")
                .addQueryParameter("handlerName", handlerName)
                .addQueryParameter("key", key)
                .build();
        try {
            Response response = new HttpGetTask().execute(url).get();
            String result = response.body().string();
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonObject row = parser.parse(result).getAsJsonObject();
            int flag = gson.fromJson(row.getAsJsonPrimitive("flag"),Integer.class);
            JsonArray data = row.getAsJsonArray("data");
            ArrayList<Line> lines = gson.fromJson(data,new TypeToken<ArrayList<Line>>(){}.getType());
            Log.d(TAG, "queryLines: response line data is "+data.toString());

            return lines;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
