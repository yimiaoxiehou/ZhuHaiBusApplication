package top.yimiaohome.zhuhai_busapplication.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import top.yimiaohome.zhuhai_busapplication.Entity.Bus;
import top.yimiaohome.zhuhai_busapplication.Entity.Station;
import top.yimiaohome.zhuhai_busapplication.R;


/**
 * Created by HK on 2017/12/29.
 */

public class BusAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static List textIdList;
    private Context context;
    private LayoutInflater layoutInflater;
    public BusAdapter(Context context,List textIdList){
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
        this.textIdList=textIdList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==ITEM_TYPE.busitem.ordinal()){
            return new BusItemViewHolder(layoutInflater.inflate(R.layout.busitem_view,parent,false));
        }
        else{
            return new StationItemViewHolder(layoutInflater.inflate(R.layout.stationitem_view,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof BusItemViewHolder){
            Bus tempBus = (Bus) textIdList.get(position);
            ((BusItemViewHolder)holder).bustext.setText(tempBus.getBusNumber());
        }
        else if(holder instanceof StationItemViewHolder){
            Station tempBus=(Station) textIdList.get(position);
            ((StationItemViewHolder)holder).stationtext.setText(tempBus.getName());
            ((StationItemViewHolder)holder).stationindex.setText(""+position);
        }
    }

    @Override
    public int getItemCount() {
        return textIdList.size();
    }

    @Override
    public int getItemViewType(int position){
        if(textIdList.get(position).getClass()== Bus.class){
            return ITEM_TYPE.busitem.ordinal();
        }
        else{
            return ITEM_TYPE.stationitem.ordinal();
        }
    }

    public enum ITEM_TYPE{
        busitem,
        stationitem
    }

    public static class BusItemViewHolder extends RecyclerView.ViewHolder{
        TextView bustext;
        public BusItemViewHolder(View view){
            super(view);
            bustext=(TextView)view.findViewById(R.id.bus_text);
        }
    }

    public static class StationItemViewHolder extends RecyclerView.ViewHolder{
        TextView stationtext;
        TextView stationindex;
        public StationItemViewHolder(View view){
            super(view);
            stationtext=(TextView)view.findViewById(R.id.station_text);
            stationindex=(TextView)view.findViewById(R.id.station_index);
        }
    }

}
