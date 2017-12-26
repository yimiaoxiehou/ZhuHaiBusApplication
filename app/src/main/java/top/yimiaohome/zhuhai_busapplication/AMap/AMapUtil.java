package top.yimiaohome.zhuhai_busapplication.AMap;

import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yimia on 2017/12/26.
 * modify by AMap official demo.
 */

public class AMapUtil {
    public static LatLonPoint convertToLatLngPoint(LatLng latlon) {
        return new LatLonPoint(latlon.latitude,latlon.longitude);
    }
    public static LatLng convertToLatLng(LatLonPoint latLonPoint){
        return new LatLng(latLonPoint.getLatitude(),latLonPoint.getLongitude());
    }

    public static ArrayList<LatLng> convertArrList(List<LatLonPoint> shapes) {
        ArrayList<LatLng> lineShapes = new ArrayList<LatLng>();
        shapes.forEach(s->{
            LatLng latLngTemp = convertToLatLng(s);
            lineShapes.add(latLngTemp);
        });
        return lineShapes;
    }
}
