package top.yimiaohome.zhuhai_busapplication.Activity;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.RouteOverlay;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.route.BusRouteResult;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import top.yimiaohome.zhuhai_busapplication.AMap.Poi;
import top.yimiaohome.zhuhai_busapplication.AMap.Route;
import top.yimiaohome.zhuhai_busapplication.Adapter.PoiActAdapter;
import top.yimiaohome.zhuhai_busapplication.R;

/**
 * Created by yimia on 2017/12/18.
 */

public class MapActivity extends AppCompatActivity implements AMap.OnMyLocationChangeListener {
    final static String TAG = "MapActivity";

    public Context mContext;
    public List<PoiItem> poiItemList;
    public GeocodeAddress destination;
    public TextView startingET;
    public TextView destinationET;
    public TextView route_tv;
    public AMap aMap;
    public LatLonPoint startPoint;
    public LatLonPoint endPoint;
    public Location mLocation;
    public MapView mMapView;
    public ListView poiListLV;
    public int pointType;
    public boolean startHere;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mContext = getApplicationContext();
        //显示地图
        setContentView(R.layout.activity_map);
        startingET = (EditText) findViewById(R.id.starting_et);
        destinationET = (EditText) findViewById(R.id.destination_et);
        poiListLV = (ListView) findViewById(R.id.poi_lists_lv);
        mMapView = (MapView) findViewById(R.id.map);
        route_tv = (TextView) findViewById(R.id.route_tv);
        mMapView.onCreate(savedInstanceState);
        if (aMap == null){
            aMap = mMapView.getMap();
        }
        //定位并显示当前位置
        mapReset();
        mLocation = aMap.getMyLocation();
        startHere = true;
        startingET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString()!="当前位置"){
                    startHere = false;
                    pointType = PoiActAdapter.start;
                    Poi.getInstance().queryPoi(mContext,s.toString());
                    poiListLV.setVisibility(View.VISIBLE);
                }else{
                    startHere = true;
                }
            }
        });
        destinationET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                pointType = PoiActAdapter.end;
                Poi.getInstance().queryPoi(mContext,s.toString());
                poiListLV.setVisibility(View.VISIBLE);
            }
        });
        RouteOverlay routeOverlay = aMap.addRouteOverlay();
    }

    public void mapReset(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm!=null){
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),0);
        }
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        myLocationStyle.interval(2000);
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setOnMyLocationChangeListener(this);
        aMap.setMyLocationEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onMyLocationChange(Location location) {
        setmLocation(location);
    }
    void setmLocation(Location location){
        if (mLocation == null ||
                (!locationEqual(location.getLongitude(),mLocation.getLongitude())
                        || !locationEqual(location.getLatitude(),mLocation.getLatitude()))){
            mLocation = location;
            Log.d(TAG, "onMyLocationChange: "+location.getLatitude()+","+location.getLongitude());
        }
    }

    Boolean locationEqual(double n,double o){
        if ((int)(n*1000)==(int)(o*1000))
            return true;
        else
            return false;
    }

}