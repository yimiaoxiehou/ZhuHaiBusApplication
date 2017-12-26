package top.yimiaohome.zhuhai_busapplication.AMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import java.util.ArrayList;
import java.util.List;
import top.yimiaohome.zhuhai_busapplication.R;

/**
 * Created by yimia on 2017/12/26.
 * modify by aMap official demo
 */

public class RouteOverlay {
    protected List<Marker> stationMarkers = new ArrayList<Marker>();
    protected List<Polyline> allPolyLines = new ArrayList<Polyline>();
    protected Marker startMarker;
    protected Marker endMarker;
    protected LatLng startPoint;
    protected LatLng endPoint;
    protected AMap mAMap;
    protected boolean nodeIconVisible = true;
    private Context mContext;
    private Bitmap startBit,endBit,busBit,walkBit,driveBit;

    public RouteOverlay(Context context){
        mContext=context;
    }

    public void removeFromMap(){
        if (startMarker != null){
            startMarker.remove();
        }
        if (endMarker != null){
            endMarker.remove();
        }
        stationMarkers.forEach(s->{
            s.remove();
        });
        allPolyLines.forEach(a->{
            a.remove();
        });
    }

    private void destroyBit(){
        if (startBit != null){
            startBit.recycle();
            startBit = null;
        }
        if (endBit != null){
            endBit.recycle();
            endBit = null;
        }
        if (busBit != null){
            busBit.recycle();
            busBit = null;
        }
        if (walkBit != null){
            walkBit.recycle();
            walkBit = null;
        }
        if (driveBit != null){
            driveBit.recycle();
            driveBit = null;
        }

    }
    /**
     * 给起点Marker设置图标，并返回更换图标的图片。如不用默认图片，需要重写此方法。
     * @return 更换的Marker图片。
     */
    protected BitmapDescriptor getStartBitmapDescriptor(){
        return BitmapDescriptorFactory.fromResource(R.drawable.amap_start);
    }

    /**
     * 给终点Marker设置图标，并返回更换图标的图片。如不用默认图片，需要重写此方法。
     * @return 更换的Marker图片。
     */
    protected BitmapDescriptor getEndBitmapDescriptor() {
        return BitmapDescriptorFactory.fromResource(R.drawable.amap_end);
    }
    /**
     * 给公交Marker设置图标，并返回更换图标的图片。如不用默认图片，需要重写此方法。
     * @return 更换的Marker图片。
     */
    protected BitmapDescriptor getBusBitmapDescriptor() {
        return BitmapDescriptorFactory.fromResource(R.drawable.amap_bus);
    }
    /**
     * 给步行Marker设置图标，并返回更换图标的图片。如不用默认图片，需要重写此方法。
     * @return 更换的Marker图片。
     */
    protected BitmapDescriptor getWalkBitmapDescriptor() {
        return BitmapDescriptorFactory.fromResource(R.drawable.amap_man);
    }
    /**
     * 给自驾Marker设置图标，并返回更换图标的图片。如不用默认图片，需要重写此方法。
     * @return 更换的Marker图片。
     */
    protected BitmapDescriptor getDriveBitmapDescriptor() {
        return BitmapDescriptorFactory.fromResource(R.drawable.amap_car);
    }

    protected void addStartAndEndMarker(){
        startMarker = mAMap.addMarker((new MarkerOptions())
                .position(startPoint)
                .icon(getStartBitmapDescriptor())
                .title("起点"));

        endMarker = mAMap.addMarker((new MarkerOptions())
                .position(endPoint)
                .icon(getEndBitmapDescriptor())
                .title("终点"));
    }
    /**
     * 移动镜头到当前的视角。
     * @since V2.1.0
     */
    public void zoomToSpan(){
        if (startPoint != null){
            if (mAMap == null){
                return;
            }
            try{
                LatLngBounds bounds = getLatLngBounds();
                mAMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds,50));
            }catch (Throwable e){
                e.printStackTrace();
            }
        }
    }

    public LatLngBounds getLatLngBounds() {
        LatLngBounds.Builder b = LatLngBounds.builder();
        b.include(new LatLng(startPoint.latitude,startPoint.longitude));
        b.include(new LatLng(endPoint.latitude,endPoint.longitude));
        return b.build();
    }

    public void setNodeIconVisible(boolean visible){
        try {
            nodeIconVisible = visible;
            if (this.stationMarkers != null && this.stationMarkers.size() > 0) {
                stationMarkers.forEach(s -> {
                    s.setVisible(visible);
                });
            }
        }catch (Throwable e){
            e.printStackTrace();
        }
    }
    protected void addStationMarker(MarkerOptions options){
        if (options == null){
            return;
        }
        Marker marker = mAMap.addMarker(options);
        if (marker!=null){
            stationMarkers.add(marker);
        }
    }
    protected void addPolyLine(PolylineOptions options){
        if (options == null){
            return;
        }
        Polyline polyline = mAMap.addPolyline(options);
        if (polyline != null){
            allPolyLines.add(polyline);
        }
    }
    protected float getRouteWidth(){
        return 18f;
    }
    protected int getWalkColor(){
        return Color.parseColor("#6db74d");
    }
    /**
     * 自定义路线颜色。
     * return 自定义路线颜色。
     */
    protected int getBusColor() {
        return Color.parseColor("#537edc");
    }

    protected int getDriveColor() {
        return Color.parseColor("#537edc");
    }
}
