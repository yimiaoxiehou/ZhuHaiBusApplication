package top.yimiaohome.zhuhai_busapplication.AMap;

import android.content.Context;
import android.util.Log;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import java.util.List;
import top.yimiaohome.zhuhai_busapplication.Activity.MapActivity;

/**
 * Created by yimia on 2017/12/24.
 */

public class Poi {
    static final String TAG = "Poi";

    public static void queryPoi(Context mContenxt,String keyWord) {
        String cityCode = "珠海";
        PoiSearch.Query query = new PoiSearch.Query(keyWord, "", cityCode);//输入提示
        query.setCityLimit(true);
        query.setPageSize(10);
        query.setPageNum(0);
        PoiSearch poiSearch = new PoiSearch(mContenxt, query);
        Log.d(TAG, "queryPoi: citycode is "+query.getCity());
        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult poiResult, int i) {
                Log.d(TAG, "onPoiSearched: result code is "+i);
                if (i == 1000){
                    List<PoiItem> poiItemList = poiResult.getPois();
                    Log.d(TAG, "onPoiSearched: first poi is "+poiItemList.get(0).getTitle());
                    MapActivity.destination_tv.setText(poiItemList.get(0).getTitle());
                    poiItemList.forEach( r -> {
                        Log.d(TAG, "onPoiSearched: result is " + r.getTitle());
                    });
                    MapActivity.poiItemList=poiItemList;
                }else {
                    Log.d(TAG, "onPoiSearched: error ");
                }
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {
                Log.d(TAG, "onPoiItemSearched: result code is "+i);
            }
        });
        poiSearch.searchPOIAsyn();
    }
}