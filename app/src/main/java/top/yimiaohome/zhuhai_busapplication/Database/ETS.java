package top.yimiaohome.zhuhai_busapplication.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.io.InputStream;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import top.yimiaohome.zhuhai_busapplication.Entity.Line;

/**
 * Created by Rikka on 2017/12/24.
 */

public class ETS {
    private static final String TAG="ETS";
    private static final String EXCEPTION="excption";
    private static final String Key="is_readed_data";
    private static ETS instance=null;
    private LocalSql mDBHelper=null;

    private ETS(Context context){
        Log.d(TAG, "ETS: create");
        mDBHelper= LocalSql.getInstance(context);
        if(!PreferencesUtils.getBoolean(context,Key,false)){
            if(!Exist()){
                Log.d(TAG,"数据库已存在");
            }
            else {
                ReadToSql(context);
            }
        }
    }

    public static ETS getInstance(Context context){
        Log.d(TAG, "getInstance: get");
        if(instance==null){
            instance=new ETS(context.getApplicationContext());
        }
        return instance;
    }

    public void ReadToSql(Context context){
        try{
            Log.d(TAG, "ReadToSql: start");
            InputStream is= context.getAssets().open("excel_data.xls");
            Workbook book = Workbook.getWorkbook(is);
            Log.d(TAG, "ReadToSql: sheets num is "+book.getNumberOfSheets());
            for (int n=0;n<book.getNumberOfSheets();n++) {
                Sheet sheet = book.getSheet(n);   //获取第一个工作表对象
                int cols = sheet.getColumns();    //获取列数
                Log.d(TAG, "ReadToSql: 1.2 is " + sheet.getCell(0, 1).getContents());
                Log.d(TAG, "ReadToSql: length is " + sheet.getCell(0, 1).getContents().length());
                for (int i = 2; i < cols; i++) {
                    String Id = (sheet.getCell(i, 0)).getContents().trim();
                    int rows = sheet.getRows();
                    String FirstStation = (sheet.getCell(i, 1)).getContents().trim();
                    String LastStation = (sheet.getCell(i, 4)).getContents().trim();
                    Line info = new Line(Id, FirstStation, LastStation);
                    SaveToDB(info);
                    Log.d(TAG, "ReadToSql: Info is " + info.toString());
                }
            }
            book.close();
            is.close();
            Log.d("OK","Excel读取完成");
            PreferencesUtils.putBoolean(context,Key,true);
        }catch (Exception e){
            PreferencesUtils.putBoolean(context,Key,false);
            Log.e(TAG,EXCEPTION,e);
        }
    }

    private void SaveToDB(Line line) {
        if(mDBHelper==null){
            return;
        }
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        try{
            ContentValues values=new ContentValues();
            values.put("Id",line.getId());
            values.put("FirstStation",line.getFromStation());
            values.put("ToStation",line.getToStation());
            db.insert(LocalSql.LineInfoTable,null,values);
        }catch (SQLiteException e){
            Log.e(TAG,EXCEPTION,e);
        }catch (Exception e){
            Log.e(TAG,EXCEPTION,e);
        }finally {
            if(db!=null){
                db.close();
            }
        }
    }

    private boolean Exist(){
        Log.d(TAG,"Exist: Start");
        boolean exist=true;
        if(mDBHelper==null){
            return false;
        }
        SQLiteDatabase db=mDBHelper.getReadableDatabase();
        try{
            Cursor cursor=db.rawQuery("select * from "+LocalSql.LineInfoTable,null);
            if(cursor.moveToNext()){
                int count=cursor.getInt(0);
                if(count>0){
                    exist=false;
                }
                else{
                    exist=true;
                }
            }
        }catch (Exception e){
            Log.e(TAG,EXCEPTION,e);
            exist=false;
        }
        return exist;
    }
}
