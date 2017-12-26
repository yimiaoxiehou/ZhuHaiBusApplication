package top.yimiaohome.zhuhai_busapplication.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import top.yimiaohome.zhuhai_busapplication.R;

public class MainActivity extends AppCompatActivity {
    private Button openRealTimeBusQueryBtn;
    private Button openRouteSearchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openRealTimeBusQueryBtn = (Button) findViewById(R.id.open_realtime_bus_query_btn);
        openRouteSearchBtn = (Button) findViewById(R.id.open_route_search_btn);
        openRealTimeBusQueryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RealTimeBusQueryActivity.class);
                startActivity(intent);
            }
        });
        openRouteSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RouteSearchActivity.class);
                startActivity(intent);
            }
        });
    }
}
