package top.yimiaohome.zhuhai_busapplication.Activity;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import top.yimiaohome.zhuhai_busapplication.Adapter.BusAdapter;
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
    private Button queryLinesBtn;
    private TextView showInfoTv;
    private ListView list_bus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runningbus);
        BusAdapter busAdapter=new BusAdapter(this);
        ArrayList result=HttpGetServerData.GetBusListOnRoadWithStation("8路","拱北口岸总站");
        BusAdapter.setTextIdList(result);
        list_bus=(ListView) findViewById(R.id.list_bus);
        list_bus.setAdapter(busAdapter);
        Button btn=(Button) findViewById(R.id.returnbtn);
        Drawable drawable=getResources().getDrawable(R.drawable.returnbtn);
        drawable.setBounds(0,0,100,100);
        btn.setCompoundDrawables(drawable,null,null,null);
        /*queryRunningBusBtn=(Button) findViewById(R.id.query_runningBus_btn);
        queryLinesBtn=(Button) findViewById(R.id.query_lines_btn);
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
        result.forEach(r -> {   //fot(Object r : result):
            if (r.getClass()==Bus.class){
                Bus tempBus = (Bus) r;
                showInfoTv.setText(showInfoTv.getText()+"("+tempBus.getBusNumber()+")");
            }
            else if (r.getClass()==Station.class){
                Station tempStation = (Station) r;
                showInfoTv.setText(showInfoTv.getText()+"-->"+tempStation.getName());
            }
        });
        showInfoTv.setText(showInfoTv.getText()+"End");*/

    }
}
