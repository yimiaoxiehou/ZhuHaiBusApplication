package top.yimiaohome.zhuhai_busapplication.Database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by Rikka on 2017/12/26.
 * 使用异步存储读取Excel数据到数据库
 */

public class ExcelAsyncTask extends AsyncTask<Void,Void,Void> {
    final String TAG = "ExcelAsyncTask";
    private Context context=null;
    private ETS dbManager=null;

    public ExcelAsyncTask(Context context){
        this.context=context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Log.d(TAG, "doInBackground: start");
        dbManager=ETS.getInstance(context);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.d(TAG, "onPostExecute: finish");
    }
}
