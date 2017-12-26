package top.yimiaohome.zhuhai_busapplication.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import top.yimiaohome.zhuhai_busapplication.Database.MyDataBasesHelper;
import top.yimiaohome.zhuhai_busapplication.Entity.Line;
import top.yimiaohome.zhuhai_busapplication.Network.HttpGetServerData;
import top.yimiaohome.zhuhai_busapplication.R;

public class RealTimeBusQueryActivity extends AppCompatActivity {
    String TAG = "RealTimeBusQueryActivity";
    private MyDataBasesHelper myDataBasesHelper;
    private Button lineQueryBtn;
    private EditText lineNumberEt;
    private Context context;
    private Line queryLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realtime_bus_query);
        context=getBaseContext();
        lineNumberEt = (EditText) findViewById(R.id.line_number_et);
        lineQueryBtn = (Button) findViewById(R.id.line_query_btn);
        lineQueryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList result = HttpGetServerData.GetBusListOnRoadWithStation(queryLine.getLineNumber(), queryLine.getFromStation());
                if (result!=null){

                }
                else {
                    Log.d(TAG, "onClick: runningBus is null");
                    Toast.makeText(context,"网络异常,请检测网络",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
