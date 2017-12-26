package top.yimiaohome.zhuhai_busapplication.Activity;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
import java.util.List;
import top.yimiaohome.zhuhai_busapplication.AMap.Poi;
import top.yimiaohome.zhuhai_busapplication.AMap.Route;
import top.yimiaohome.zhuhai_busapplication.R;

/**
 * Created by yimia on 2017/12/18.
 */

public class MapActivity extends AppCompatActivity implements View.OnClickListener,AMap.OnMyLocationChangeListener {
    final static String TAG = "MapActivity";

    public static Context mContext;
    public static List<PoiItem> poiItemList;
    public static GeocodeAddress destination;
    public static TextView destination_tv;
    public static BusRouteResult busRouteResult;
    public static AMap aMap;
    public static LatLonPoint startPoint;
    public static LatLonPoint endPoint;
    Location mLocation;
    MapView mMapView;
    EditText destination_et;
    Button queryDest_bn;
    boolean isFirst;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        //显示地图
        setContentView(R.layout.activity_map);
        destination_et = (EditText) findViewById(R.id.destination_et);
        queryDest_bn = (Button) findViewById(R.id.query_location_btn);
        destination_tv = (TextView) findViewById(R.id.destination_query_result_tv);
        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        if (aMap == null){
            aMap = mMapView.getMap();
        }
        isFirst=true;
        //定位并显示当前位置
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.interval(2000);
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setOnMyLocationChangeListener(this);
        aMap.setMyLocationEnabled(true);
        queryDest_bn.setOnClickListener(this);
        RouteOverlay routeOverlay = aMap.addRouteOverlay();
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: click");
        switch (v.getId()){
            case R.id.query_location_btn:
                if (isFirst) {
                    isFirst=false;
                    //查询 poi (point of interest) 兴趣点
                    if (destination_et.getText().toString()!="") {
                        Poi.queryPoi(mContext,destination_et.getText().toString());
                    }
                }
                else{
                    isFirst=true;
                    startPoint = new LatLonPoint(mLocation.getLatitude(),mLocation.getLongitude());
                    endPoint = poiItemList.get(0).getLatLonPoint();
                    Route.getRoute(startPoint,endPoint);
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
        if (mLocation == null || (location.getLongitude()!=mLocation.getLongitude() || location.getLatitude()!=mLocation.getLatitude())){
            mLocation = location;
            Log.d(TAG, "onMyLocationChange: "+location.getLatitude()+","+location.getLongitude());
        }
    }

}