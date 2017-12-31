package top.yimiaohome.zhuhai_busapplication.AMap;

import android.content.Context;
import android.util.Log;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import java.util.List;
import top.yimiaohome.zhuhai_busapplication.Activity.MapActivity;
import top.yimiaohome.zhuhai_busapplication.Adapter.PoiActAdapter;

/**
 * Created by yimia on 2017/12/24.
 * 用于根据关键字搜索 poi
 */

public class Poi implements PoiSearch.OnPoiSearchListener {
    static final String TAG = "Poi";
    private static Poi instance;
    MapActivity mapActivity;
    PoiActAdapter poiActAdapter;

    private Poi() {

    }

    public static Poi getInstance(){
        if (instance == null){
            instance = new Poi();
        }
        return instance;
    }

    public void queryPoi(Context mContenxt,String keyWord) {
        if (mapActivity == null)
            mapActivity = (MapActivity) MapActivity.getCurrentActivity();
        String cityCode = "珠海";
        PoiSearch.Query query = new PoiSearch.Query(keyWord, "", cityCode);//输入提示
        query.setCityLimit(true);    //强制使用城市范围限制
        query.setPageSize(10);
        query.setPageNum(0);
        PoiSearch poiSearch = new PoiSearch(mContenxt, query);
        Log.d(TAG, "queryPoi: citycode is "+query.getCity());
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        Log.d(TAG, "onPoiSearched: result code is "+i);
        if (i == 1000){
            List<PoiItem> poiItemList = poiResult.getPois();
            Log.d(TAG, "onPoiSearched: first poi is "+poiItemList.get(0).getTitle());
            mapActivity.poiItemList = poiItemList;
            poiActAdapter = new PoiActAdapter(poiItemList,mapActivity.pointType,mapActivity.mContext);
            mapActivity.poiListLV.setAdapter(poiActAdapter);
        }else {
            Log.d(TAG, "onPoiSearched: error ");
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {
        Log.d(TAG, "onPoiItemSearched: result code is "+i);
    }
}