package top.yimiaohome.zhuhai_busapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by yimia on 2017/12/17.
 */

public class MyDataBasesHelper extends SQLiteOpenHelper {

    public static String CREATE_TABLE_LINE = "create table Line (" +
            "id integer primary key autoincrement," +
            "line_number text," +
            "line_Id text)";

    private Context mContext;

    public MyDataBasesHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_LINE);
        Toast.makeText(mContext,"Create table line succeeded",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
