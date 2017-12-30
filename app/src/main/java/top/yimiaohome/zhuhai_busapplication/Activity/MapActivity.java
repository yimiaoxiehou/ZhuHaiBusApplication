package top.yimiaohome.zhuhai_busapplication.Activity;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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
import top.yimiaohome.zhuhai_busapplication.R;

/**
 * Created by yimia on 2017/12/18.
 */

public class MapActivity extends AppCompatActivity implements View.OnClickListener,AMap.OnMyLocationChangeListener {
    final static String TAG = "MapActivity";

    public Context mContext;
    public List<PoiItem> poiItemList;
    public GeocodeAddress destination;
    public TextView destination_tv;
    public TextView route_tv;
    public BusRouteResult busRouteResult;
    public AMap aMap;
    public LatLonPoint startPoint;
    public LatLonPoint endPoint;
    public Location mLocation;
    public MapView mMapView;
    public EditText destination_et;
    public Button queryDest_bn;
    boolean isFirst;

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
        destination_et = (EditText) findViewById(R.id.destination_et);
        queryDest_bn = (Button) findViewById(R.id.query_location_btn);
        destination_tv = (TextView) findViewById(R.id.destination_query_result_tv);
        mMapView = (MapView) findViewById(R.id.map);
        route_tv = (TextView) findViewById(R.id.route_tv);
        mMapView.onCreate(savedInstanceState);
        if (aMap == null){
            aMap = mMapView.getMap();
        }
        isFirst=true;
        //定位并显示当前位置
        mapReset();
        queryDest_bn.setOnClickListener(this);
        RouteOverlay routeOverlay = aMap.addRouteOverlay();
    }

    public void mapReset(){
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        myLocationStyle.interval(2000);
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setOnMyLocationChangeListener(this);
        aMap.setMyLocationEnabled(true);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: click");
        switch (v.getId()){
            case R.id.query_location_btn:
                if (isFirst) {
                    //查询 poi (point of interest) 兴趣点
                    if (destination_et.getText().toString()!="") {
                        isFirst=false;
                        poiItemList=null;
                        Poi.getInstance().queryPoi(mContext, destination_et.getText().toString());
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm!=null){
                            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),0);
                        }
                    }
                }
                else{
                    isFirst = true;
                    if (poiItemList!=null){
                        aMap.clear();
                        mapReset();
                        startPoint = new LatLonPoint(mLocation.getLatitude(), mLocation.getLongitude());
                        endPoint = poiItemList.get(0).getLatLonPoint();
                        Route.getInstance().getRoute(startPoint, endPoint);
                    }
                }
        }
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
        if (mLocation == null || (!locationEquael(location.getLongitude(),mLocation.getLongitude())
                || !locationEquael(location.getLatitude(),mLocation.getLatitude()))){
            mLocation = location;
            Log.d(TAG, "onMyLocationChange: "+location.getLatitude()+","+location.getLongitude());
        }
    }

    Boolean locationEquael(double n,double o){
        if ((int)(n*1000)==(int)(o*1000))
            return true;
        else
            return false;
    }
}