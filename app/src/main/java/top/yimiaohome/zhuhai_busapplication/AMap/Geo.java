package top.yimiaohome.zhuhai_busapplication.AMap;

import android.content.Context;
import android.util.Log;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import java.util.List;
import top.yimiaohome.zhuhai_busapplication.Activity.MapActivity;

/**
 * Created by yimia on 2017/12/24.
 * 用于解析 Poi 信息
 */

public class Geo  {
    static final String TAG = "GeocodeSearch";
    private static Geo instance;
    private Geo(){}

    public static Geo getInstance(){
        if (instance == null){
            instance = new Geo();
        }
        return instance;
    }

    public void queryDestinationLocation(Context mContext,PoiItem poiItem){
        //地址编码
        MapActivity mapActivity = (MapActivity) MapActivity.getCurrentActivity();
        GeocodeSearch geocodeSearch = new GeocodeSearch(mContext);
        geocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
                Log.d(TAG, "onRegeocodeSearched: "+regeocodeResult.toString());
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int rCode) {
                Log.d(TAG, "onGeocodeSearched: rCode is "+rCode);
                if (rCode == 1000){
                    List<GeocodeAddress> addressList = geocodeResult.getGeocodeAddressList();
                    Log.d(TAG, "onGeocodeSearched: list size is "+addressList.size());
                    mapActivity.destination = addressList.get(0);
                    Log.d(TAG, "onGeocodeSearched: first address is "+addressList.get(0).getFormatAddress());
                }else {
                    Log.d(TAG, "onGeocodeSearched: error");
                }
            }
        });
        GeocodeQuery geocodeQuery = new GeocodeQuery(poiItem.getTitle(),"0756");
        geocodeSearch.getFromLocationNameAsyn(geocodeQuery);
    }
}
