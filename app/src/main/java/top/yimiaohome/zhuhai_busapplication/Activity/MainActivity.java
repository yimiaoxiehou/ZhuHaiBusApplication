package top.yimiaohome.zhuhai_busapplication.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.amap.api.maps.MapView;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.route.RouteSearch;
import java.util.ArrayList;
import java.util.List;

import top.yimiaohome.zhuhai_busapplication.Database.MyDataBasesHelper;
import top.yimiaohome.zhuhai_busapplication.Entity.Bus;
import top.yimiaohome.zhuhai_busapplication.Entity.Line;
import top.yimiaohome.zhuhai_busapplication.Entity.Station;
import top.yimiaohome.zhuhai_busapplication.Network.HttpGetServerData;
import top.yimiaohome.zhuhai_busapplication.R;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity";
    private MyDataBasesHelper myDataBasesHelper;
    private Button queryRunningBusBtn;
    private Button queryLinesBtn,openMapBtn;
    private TextView showInfoTv;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=getBaseContext();
        queryRunningBusBtn=(Button) findViewById(R.id.query_runningBus_btn);
        queryLinesBtn=(Button) findViewById(R.id.query_lines_btn);
        openMapBtn= (Button) findViewById(R.id.open_map_btn);
        showInfoTv=(TextView) findViewById(R.id.show_info_tv);
        queryRunningBusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Bus> runningBus = HttpGetServerData.GetBusListOnRoad("8路", "拱北口岸总站");
                if (runningBus!=null){
                    showInfoTv.setText("");
                    for(Bus bus : runningBus){
                        showInfoTv.setText(showInfoTv.getText()+"\n"+bus.getBusNumber());
                    }
                }
                else {
                    Log.d(TAG, "onClick: runningBus is null");
                }
            }
        });
        queryLinesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Line> lines = HttpGetServerData.GetLineListByLineName("8路");
                if (lines!=null){
                    showInfoTv.setText("");
                    for (Line line : lines){
                        showInfoTv.setText(showInfoTv.getText()+"\n"+line.getLineNumber());
                        ArrayList<Station> stations = HttpGetServerData.GetStationList(line.getId());
                        if (stations!=null){
                            for (Station station : stations){
                                showInfoTv.setText(showInfoTv.getText()+"\n"+station.getName());
                            }
                        }
                    }
                }
                else {
                    Log.d(TAG, "onClick: lines is null");
                }
            }
        });
        myDataBasesHelper=new MyDataBasesHelper(this,"Bus.db",null,1);
        myDataBasesHelper.getWritableDatabase();
        ArrayList result = HttpGetServerData.GetBusListOnRoadWithStation("8路", "拱北口岸总站");
        showInfoTv.setText("Start");
        result.forEach(r -> {
            if (r.getClass()==Bus.class){
                Bus tempBus = (Bus) r;
                showInfoTv.setText(showInfoTv.getText()+"("+tempBus.getBusNumber()+")");
            }
            else if (r.getClass()==Station.class){
                Station tempStation = (Station) r;
                showInfoTv.setText(showInfoTv.getText()+"-->"+tempStation.getName());
            }
        });
        showInfoTv.setText(showInfoTv.getText()+"-->End");
        openMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MapActivity.class);
                startActivity(intent);
            }
        });
    }


}
