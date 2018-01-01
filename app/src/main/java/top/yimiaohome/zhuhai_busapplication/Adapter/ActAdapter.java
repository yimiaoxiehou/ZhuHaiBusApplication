package top.yimiaohome.zhuhai_busapplication.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import top.yimiaohome.zhuhai_busapplication.Activity.RealTimeBusQueryActivity;
import top.yimiaohome.zhuhai_busapplication.R;

/**
 * Created by Rikka on 2017/12/29.
 */

public class ActAdapter extends CursorAdapter {
    final String TAG = "ActAdapter";
    RealTimeBusQueryActivity realTimeBusQueryActivity;
    private LayoutInflater layoutInflater;

    public ActAdapter(Context context, Cursor cursor, boolean autoRequery){
        super(context,cursor,autoRequery);
        realTimeBusQueryActivity = (RealTimeBusQueryActivity) RealTimeBusQueryActivity.getCurrentActivity();
        layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public CharSequence ToString(Cursor cursor){
        if(cursor==null){
            return "";
        }
        else {
            return cursor.getString(cursor.getColumnIndex("_id"));
        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view=layoutInflater.inflate(R.layout.item_view,null);

        TextView item=(TextView)view;
        item.setText(cursor.getString(cursor.getColumnIndex("_id")));
        TextView textView= view.findViewById(R.id.point_item_tv);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: select lineId is "+textView.getText());
                realTimeBusQueryActivity.lineNumber_actv.setText(textView.getText());
                realTimeBusQueryActivity.showFromAndToStation((String) textView.getText());
            }
        });
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView item=(TextView)view;
        item.setText(cursor.getString(cursor.getColumnIndex("_id")));
    }
}