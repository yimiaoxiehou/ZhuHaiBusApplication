package top.yimiaohome.zhuhai_busapplication.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import top.yimiaohome.zhuhai_busapplication.Adapter.BusAdapter;
import top.yimiaohome.zhuhai_busapplication.Database.MyDataBasesHelper;
import top.yimiaohome.zhuhai_busapplication.Entity.Line;
import top.yimiaohome.zhuhai_busapplication.Network.HttpGetServerData;
import top.yimiaohome.zhuhai_busapplication.R;

public class RealTimeBusQueryActivity extends AppCompatActivity {
    String TAG = "RealTimeBusQueryActivity";
    private Button lineQueryBTN;
    private EditText lineNumberET;
    private Context context;
    private Line queryLine;
    private ListView runningBusAndStationLV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realtime_bus_query);
        context=getBaseContext();
        lineNumberET = (EditText) findViewById(R.id.line_number_et);
        lineQueryBTN = (Button) findViewById(R.id.line_query_btn);
        runningBusAndStationLV = (ListView) findViewById(R.id.running_bus_and_station_lv);
        lineQueryBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ArrayList result = HttpGetServerData.GetBusListOnRoadWithStation(queryLine.getLineNumber(), queryLine.getFromStation());
                ArrayList result = HttpGetServerData.GetBusListOnRoadWithStation("8路","拱北口岸总站");
                if (result!=null){
                    BusAdapter busAdapter = new BusAdapter(context);
                    BusAdapter.setTextIdList(result);
                    runningBusAndStationLV.setAdapter(busAdapter);
                }
                else {
                    Log.d(TAG, "onClick: runningBus is null");
                    Toast.makeText(context,"网络异常,请检测网络",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
