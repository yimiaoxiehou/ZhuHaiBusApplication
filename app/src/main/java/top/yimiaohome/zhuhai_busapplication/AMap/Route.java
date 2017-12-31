package top.yimiaohome.zhuhai_busapplication.AMap;

import android.app.Activity;
import android.util.Log;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import top.yimiaohome.zhuhai_busapplication.Activity.MapActivity;
import top.yimiaohome.zhuhai_busapplication.R;

/**
 * Created by yimia on 2017/12/24.
 */

public class Route implements RouteSearch.OnRouteSearchListener {
    static final String TAG = "Route";
    private static Route instance;
    MapActivity mapActivity;
    LatLonPoint startPoint,endPoint;
    private Route(){}

    public static Route getInstance(){
        if (instance == null){
            instance = new Route();
        }
        return instance;
    }

    public void getRoute(LatLonPoint startPoint,LatLonPoint endPoint) {
        mapActivity = (MapActivity) MapActivity.getCurrentActivity();
        this.startPoint=startPoint;
        this.endPoint=endPoint;
        RouteSearch routeSearch = new RouteSearch(mapActivity.mContext);
        RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(startPoint,endPoint);
        routeSearch.setRouteSearchListener(this);
        RouteSearch.BusRouteQuery query = new RouteSearch.BusRouteQuery(fromAndTo,RouteSearch.BUS_LEASE_WALK,"0756",1);
        routeSearch.calculateBusRouteAsyn(query);

    }

    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {
        if (i == AMapException.CODE_AMAP_SUCCESS){
            Log.d(TAG, "onBusRouteSearched: succeed");
            if (busRouteResult != null && busRouteResult.getPaths().size() > 0){
                Log.d(TAG, "onBusRouteSearched: path num is "+busRouteResult.getPaths().size());
                mapActivity.busRouteResult = busRouteResult;
                mapActivity.busRouteResultPosition = 0;
                mapActivity.screenRouteOverlay();
            }
        }
        else{
            Log.d(TAG, "onBusRouteSearched: error");
        }
    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {

    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }
}
