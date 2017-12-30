package top.yimiaohome.zhuhai_busapplication.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Rikka on 2017/12/24.
 */

public class LocalSql extends SQLiteOpenHelper{
    public static final String LineInfoTable="LineInfoTable";
    private static final int Version=1;
    public static String DBName="Line.db";
    private static LocalSql instance;
    private LocalSql(Context context){
        super(context,DBName,null,Version);
    }

    public static LocalSql getInstance(Context context){
        if(instance==null){
            instance=new LocalSql(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "
                +LineInfoTable
                +"([_id] AUTOINC"
                +",`Id` text"
                +",`FirstStation` text"
                +",`ToStation` text"
                +",CONSTRAINT [sqlite_autoindex_ExcelClass1_1] PRIMARY KEY ([_id]))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if(i1!=i){
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+LineInfoTable);
            onCreate(sqLiteDatabase);
        }
    }

    //查询数据
    public Cursor rowQuery(String sql,String[] args){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(sql, args);
        return cursor;
    }
}
