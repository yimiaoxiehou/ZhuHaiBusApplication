package top.yimiaohome.zhuhai_busapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity";
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.detail);
        ArrayList<Bus> runningBus = new ArrayList<>();
        ArrayList<Line> lines = new ArrayList<>();
        try {
            runningBus = HttpGetServerDatas.queryRunningBus("GetBusListOnRoad",
                    "8路", "拱北口岸总站");
            Log.d(TAG, "onCreate: first bus is :" + runningBus.get(0).getBusNumber());
            lines = HttpGetServerDatas.queryLines("GetLineListByLineName",
                    "8路");
            Log.d(TAG, "onCreate: first line id is :" + lines.get(0).getId());
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
            Log.d(TAG, "onCreate: exection "+runningBus.toString());
        }
        /*
        Gson gson = new Gson();
        String in = "{\"flag\":1002,\"data\":" +
                "[{\"BusNumber\":\"粤C28337\",\"CurrentStation\":\"供水总公司\",\"LastPosition\":\"8\"}," +
                "{\"BusNumber\":\"粤C28495\",\"CurrentStation\":\"白石南、银石雅园南\",\"LastPosition\":\"8\"}," +
                "{\"BusNumber\":\"粤C28402\",\"CurrentStation\":\"招商花园城南\",\"LastPosition\":\"8\"}," +
                "{\"BusNumber\":\"粤C28312\",\"CurrentStation\":\"香洲区府\",\"LastPosition\":\"8\"}," +
                "{\"BusNumber\":\"粤C28375\",\"CurrentStation\":\"人民医院\",\"LastPosition\":\"8\"}," +
                "{\"BusNumber\":\"粤C28843\",\"CurrentStation\":\"百货公司\",\"LastPosition\":\"5\"}," +
                "{\"BusNumber\":\"粤C28347\",\"CurrentStation\":\"翠香中\",\"LastPosition\":\"8\"}," +
                "{\"BusNumber\":\"粤C18679\",\"CurrentStation\":\"香洲\",\"LastPosition\":\"8\"}]}";

        JsonParser parser = new JsonParser();
        JsonObject row = parser.parse(in).getAsJsonObject();
        JsonArray data = row.getAsJsonArray("data");
        runningBus = gson.fromJson(data,new TypeToken<ArrayList<Bus>>(){}.getType());
        for(Bus bus : runningBus){
            Log.d(TAG, "onCreate: "+bus.getBusNumber()+"-"+bus.getCurrentStation());
        }
        */
    }
}
