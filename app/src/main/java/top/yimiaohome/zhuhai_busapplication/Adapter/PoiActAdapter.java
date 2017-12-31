package top.yimiaohome.zhuhai_busapplication.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.LatLonPoint;
import java.util.List;
import top.yimiaohome.zhuhai_busapplication.AMap.Route;
import top.yimiaohome.zhuhai_busapplication.Activity.MapActivity;
import top.yimiaohome.zhuhai_busapplication.R;
import static android.content.ContentValues.TAG;

/**
 * Created by yimia on 2017/12/31.
 */

public class PoiActAdapter extends BaseAdapter{
    public static final int start = 1;
    public static final int end = 2;
    private Context context;
    private List<PoiItem> poiItems;
    private MapActivity mapActivity;
    private int pointType;

    public PoiActAdapter(List<PoiItem> poiItems,int pointType,Context context){
        this.poiItems = poiItems;
        this.pointType = pointType;
        this.context = context;
    }

    @Override
    public int getCount() {
        return poiItems == null ? 0 : poiItems.size();
    }

    @Override
    public Object getItem(int position) {
        return poiItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (mapActivity == null){
            mapActivity= (MapActivity) MapActivity.getCurrentActivity();
        }
        String inflater = context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(inflater);
        TextView textView = (TextView) layoutInflater.inflate(R.layout.item_view,null);
        textView.setText(poiItems.get(position).getTitle());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: select poiItem is " + textView.getText());
                switch (pointType){
                    case start:
                        mapActivity.startingET.setText(textView.getText());
                        mapActivity.aMap.clear();
                        mapActivity.mapReset();
                        mapActivity.startPoint = mapActivity.poiItemList.get(position).getLatLonPoint();
                        if (mapActivity.endPoint != null)
                            Route.getInstance().getRoute(mapActivity.startPoint, mapActivity.endPoint);
                        mapActivity.poiListLV.setVisibility(View.GONE);
                        break;
                    case end:
                        mapActivity.destinationET.setText(textView.getText());
                        mapActivity.aMap.clear();
                        mapActivity.mapReset();
                        if (mapActivity.startHere)
                            mapActivity.startPoint = new LatLonPoint(mapActivity.mLocation.getLatitude(),mapActivity.mLocation.getLongitude());
                        mapActivity.endPoint = mapActivity.poiItemList.get(position).getLatLonPoint();
                        Route.getInstance().getRoute(mapActivity.startPoint, mapActivity.endPoint);
                        mapActivity.poiListLV.setVisibility(View.GONE);
                        break;
                }
            }
        });
        return textView;
    }

}