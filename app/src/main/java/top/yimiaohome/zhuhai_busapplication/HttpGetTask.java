package top.yimiaohome.zhuhai_busapplication;

import android.os.AsyncTask;
import android.util.Log;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yimia on 2017/12/16.
 */

public class HttpGetTask extends AsyncTask<HttpUrl,Integer, String> {

    final String TAG = "HttpGetTask";

    static final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20,TimeUnit.SECONDS)
            .readTimeout(20,TimeUnit.SECONDS)
            .build();

    @Override
    protected String doInBackground(HttpUrl... url) {
        try {

            Request request = new Request.Builder()
                    .url(url[0])
                    .build();

            Log.d(TAG, "doInBackground: request:" + request.toString());

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String result = response.body().string();
                Log.d(TAG, "doInBackground: response body is "+result);
                return result;
            }else {
                Log.d(TAG, "doInBackground: response failed");
                return null;
            }
        } catch (IOException e) {
            Log.d(TAG, "doInBackground: error.");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        //在此添加后台进程完成后的操作。
    }
}
