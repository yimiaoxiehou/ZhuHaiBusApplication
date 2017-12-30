package top.yimiaohome.zhuhai_busapplication.Activity;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;
import top.yimiaohome.zhuhai_busapplication.Adapter.BusAdapter;
import top.yimiaohome.zhuhai_busapplication.Database.ActAdapter;
import top.yimiaohome.zhuhai_busapplication.Database.LocalSql;
import top.yimiaohome.zhuhai_busapplication.Entity.Line;
import top.yimiaohome.zhuhai_busapplication.Network.HttpGetServerData;
import top.yimiaohome.zhuhai_busapplication.R;

public class RealTimeBusQueryActivity extends AppCompatActivity implements TextWatcher {
    public AutoCompleteTextView lineNumber_actv;
    String TAG = "RealTimeBusQueryActivity";
    private Button lineQueryBTN;
    private Context context;
    private Line queryLine;
    private ListView runningBusAndStationLV;
    private ActAdapter actAdapter;
    private LocalSql db;
    private TextView fromStationTV;
    private TextView toStationTV;
    private TextView directionTV;
    private LinearLayout forAndToStationLL;

    public static Activity getCurrentActivity () {
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(
                    null);
            Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
            activitiesField.setAccessible(true);
            Map activities = (Map) activitiesField.get(activityThread);
            for (Object activityRecord : activities.values()) {
                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);
                if (!pausedField.getBoolean(activityRecord)) {
                    Field activityField = activityRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    Activity activity = (Activity) activityField.get(activityRecord);
                    return activity;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realtime_bus_query);
        context=getBaseContext();
        lineNumber_actv = (AutoCompleteTextView) findViewById(R.id.line_number_actv);
        lineNumber_actv.addTextChangedListener(this);
        db=LocalSql.getInstance(getApplicationContext());
        setNewDict("");
        lineNumber_actv.setAdapter(actAdapter);
        lineQueryBTN = (Button) findViewById(R.id.line_query_btn);
        fromStationTV = (TextView) findViewById(R.id.from_station_tv);
        toStationTV = (TextView) findViewById(R.id.to_station_tv);
        directionTV = (TextView) findViewById(R.id.dictionary_tv);
        forAndToStationLL = (LinearLayout) findViewById(R.id.from_and_to_ll);
        runningBusAndStationLV = (ListView) findViewById(R.id.running_bus_and_station_lv);
        lineQueryBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = queryLine.getFromStation();
                queryLine.setFromStation(queryLine.getToStation());
                queryLine.setToStation(temp);
                showStationAndRunningBusList();
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        setNewDict(editable.toString());
    }

    void setNewDict(String key){
        Cursor cursor=db.rowQuery(
                "select Id as _id from "
                        + LocalSql.LineInfoTable
                        +" where Id like ? "
                ,new String[]{"%"+key+"%"});
        if (cursor.moveToFirst()==false||cursor.getCount()==1){
            lineNumber_actv.dismissDropDown();
            Log.d(TAG, "setNewDict: have no point || donot need point");
        }else {
            actAdapter = new ActAdapter(this, cursor, true);
            Log.d(TAG, "setNewDict: cursor cols is " + cursor.getColumnCount());
            lineNumber_actv.setAdapter(actAdapter);
            Log.d(TAG, "setNewAdapter: first info is " + cursor.getExtras().getString("Id"));
        }
        if (cursor.moveToFirst()==false||cursor.getCount()==1){
            lineNumber_actv.dismissDropDown();
            Log.d(TAG, "setNewDict: have no point || donot need point");
        }else {
            actAdapter = new ActAdapter(this, cursor, true);
            Log.d(TAG, "setNewDict: cursor cols is " + cursor.getColumnCount());
            lineNumber_actv.setAdapter(actAdapter);
            Log.d(TAG, "setNewAdapter: first info is " + cursor.getExtras().getString("Id"));
        }
    }

    public void showFromAndToStation(String lineId){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm!=null){
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),0);
        }
        lineNumber_actv.dismissDropDown();
        Log.d(TAG, "showFromAndToStation: lineId is "+lineId);
        Cursor cursor = db.rowQuery(
                        "select `Id` as _id," +
                                "FirstStation as _first," +
                                "`ToStation` as _to from "
                        + LocalSql.LineInfoTable
                        +" where id like ? "
                ,new String[]{lineId});
        Log.d(TAG, "showFromAndToStation: lineNumber_actv is "+ lineId);
        if (cursor.moveToFirst() == false){

        }else{
            queryLine = new Line(cursor.getString(cursor.getColumnIndex("_id")),
                    cursor.getString(cursor.getColumnIndex("_first")),
                    cursor.getString(cursor.getColumnIndex("_to")));
            Log.d(TAG, "showFromAndToStation: queryline is "+queryLine.toString());
            showStationAndRunningBusList();
        }
    }

    void showStationAndRunningBusList(){
        fromStationTV.setText(queryLine.getFromStation());
        toStationTV.setText(queryLine.getToStation());
        directionTV.setVisibility(View.VISIBLE);
        ArrayList result = HttpGetServerData.GetBusListOnRoadWithStation(queryLine.getId().replace(" ",""),
                queryLine.getFromStation().replace(" ",""));
        //ArrayList result = HttpGetServerData.GetBusListOnRoadWithStation("8路","拱北口岸总站");
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

}
