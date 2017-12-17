package top.yimiaohome.zhuhai_busapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity";
    private MyDataBasesHelper myDataBasesHelper;
    private Button queryRunningBusBtn;
    private Button queryLinesBtn;
    private TextView showInfoTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queryRunningBusBtn=findViewById(R.id.query_runningBus_btn);
        queryLinesBtn=findViewById(R.id.query_lines_btn);
        showInfoTv=findViewById(R.id.show_info_tv);
        queryRunningBusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Bus> runningBus = HttpGetServerDatas.GetBusListOnRoad("8路", "拱北口岸总站");
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
                ArrayList<Line> lines = HttpGetServerDatas.GetLineListByLineName("8路");
                if (lines!=null){
                    showInfoTv.setText("");
                    for (Line line : lines){
                        showInfoTv.setText(showInfoTv.getText()+"\n"+line.getLineNumber());
                        ArrayList<Station> stations = HttpGetServerDatas.GetStationList(line.getId());
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
    }
}
