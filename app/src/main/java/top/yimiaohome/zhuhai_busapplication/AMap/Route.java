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

public class Route {
    static final String TAG = "Route";
    private static Route instance;
    private Route(){}
    public static Route getInstance(){
        if (instance == null){
            instance = new Route();
        }
        return instance;
    }
    public void getRoute(LatLonPoint startPoint,LatLonPoint endPoint) {
        MapActivity mapActivity = (MapActivity) MapActivity.getCurrentActivity();
        mapActivity.mapReset();
        RouteSearch routeSearch = new RouteSearch(mapActivity.mContext);
        RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(startPoint,endPoint);
        routeSearch.setRouteSearchListener(new RouteSearch.OnRouteSearchListener() {
            @Override
            public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {
                if (i == AMapException.CODE_AMAP_SUCCESS){
                    Log.d(TAG, "onBusRouteSearched: succeed");
                    if (busRouteResult != null && busRouteResult.getPaths().size() != 0){
                        BusPath firstBusPath = busRouteResult.getPaths().get(0);
                        mapActivity.route_tv.setText(firstBusPath.toString());
                        firstBusPath.getSteps().forEach(s->{
                            s.getBusLines().forEach(l->{
                                Log.d(TAG, "onBusRouteSearched: first busPath step is "+l.getBusLineName());
                            });
                        });
                        mapActivity.aMap.clear();
                        BusRouteOverlay busRouteOverlay = new BusRouteOverlay(
                                mapActivity.mContext,
                                mapActivity.aMap,
                                busRouteResult.getPaths().get(0),
                                startPoint,
                                endPoint);
                        busRouteOverlay.addToMap();
                        busRouteOverlay.zoomToSpan();
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
        });
        RouteSearch.BusRouteQuery query = new RouteSearch.BusRouteQuery(fromAndTo,RouteSearch.BUS_LEASE_WALK,"0756",1);
        routeSearch.calculateBusRouteAsyn(query);

    }

}
