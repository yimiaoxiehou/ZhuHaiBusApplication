package top.yimiaohome.zhuhai_busapplication.AMap;

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

/**
 * Created by yimia on 2017/12/24.
 */

public class Route {
    static final String TAG = "Route";

    public static void getRoute(LatLonPoint startPoint,LatLonPoint endPoint) {
        RouteSearch routeSearch = new RouteSearch(MapActivity.mContext);
        RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(startPoint,endPoint);
        routeSearch.setRouteSearchListener(new RouteSearch.OnRouteSearchListener() {
            @Override
            public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {
                if (i == AMapException.CODE_AMAP_SUCCESS){
                    Log.d(TAG, "onBusRouteSearched: succeed");
                    if (busRouteResult != null && busRouteResult.getPaths().size() != 0){
                        BusPath firstBusPath = busRouteResult.getPaths().get(0);
                        firstBusPath.getSteps().forEach(s->{
                            s.getBusLines().forEach(l->{
                                Log.d(TAG, "onBusRouteSearched: first busPath step is "+l.getBusLineName());
                            });
                        });
                        BusRouteOverlay busRouteOverlay = new BusRouteOverlay(
                                MapActivity.mContext,
                                MapActivity.aMap,
                                busRouteResult.getPaths().get(0),
                                startPoint,
                                endPoint);
                        busRouteOverlay.addToMap();
                        busRouteOverlay.zoomToSpan();

                        /*if (busRouteResult.getPaths().size()>0){
                            busRouteResult.getPaths().forEach(r->{
                                r.getSteps().forEach(s->{
                                    s.getBusLines().forEach(l->{
                                        l.
                                    });
                                });
                            });
                        } */
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
