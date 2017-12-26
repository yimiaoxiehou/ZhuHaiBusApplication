package top.yimiaohome.zhuhai_busapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import top.yimiaohome.zhuhai_busapplication.Entity.Bus;
import top.yimiaohome.zhuhai_busapplication.Entity.Station;
import top.yimiaohome.zhuhai_busapplication.R;

/**
 * Created by HK on 2017/12/19.
 */

public class BusAdapter extends BaseAdapter {
    private static List textIdList;
    private Context context;
    public BusAdapter(Context context){
        this.context=context;
    }
    public static void setTextIdList(List textIdList1) {
        textIdList = textIdList1;
    }

    @Override
    public int getCount() {
        return textIdList.size();
    }

    @Override
    public Object getItem(int position) {
        return textIdList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String inflater=context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater layoutInflater=(LayoutInflater) context.getSystemService(inflater);
        LinearLayout linearLayout=null;
        if(textIdList.get(position).getClass()==Bus.class){
            linearLayout = (LinearLayout) layoutInflater.inflate(R.layout.busitem_view,null);
            TextView textView1=(TextView) linearLayout.findViewById(R.id.bus_text1);
            Bus tempBus = (Bus) textIdList.get(position);
            textView1.setText(tempBus.getBusNumber());


        }
        else if(textIdList.get(position).getClass()==Station.class){
            linearLayout=(LinearLayout) layoutInflater.inflate(R.layout.stationitem_view,null);
            TextView textView2=(TextView) linearLayout.findViewById(R.id.station_text);
            Station tempBus=(Station) textIdList.get(position);
            textView2.setText(tempBus.getName());
            TextView textView3=(TextView) linearLayout.findViewById(R.id.station_index);
            textView3.setText(""+position);
        }
        return linearLayout;
    }
    public void addText(String busNumber){
        textIdList.add(busNumber);
        notifyDataSetChanged();
    }
}
