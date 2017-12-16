package top.yimiaohome.zhuhai_busapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity";
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.detail);
        ArrayList<Bus> runningBus;
        ArrayList<Line> lines;
        try {
            runningBus = HttpGetServerDatas.queryRunningBus("GetBusListOnRoad",
                    "8路", "拱北口岸总站");
            Log.d(TAG, "onCreate: first bus is :" + runningBus.get(0).getBusNumber());
            lines = HttpGetServerDatas.queryLines("GetLineListByLineName",
                    "8路");
            Log.d(TAG, "onCreate: first line id is :" + lines.get(0).getId());
        } catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }
}
