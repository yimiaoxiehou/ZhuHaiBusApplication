package top.yimiaohome.zhuhai_busapplication;

import android.os.AsyncTask;
import android.util.Log;
import java.util.concurrent.TimeUnit;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yimia on 2017/12/16.
 */

public class HttpGetTask extends AsyncTask<HttpUrl,Integer, Response> {
    final String TAG = "HttpGetTask";

    @Override
    protected Response doInBackground(HttpUrl... url) {
        try {
            final OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .readTimeout(5,TimeUnit.SECONDS)
                    .build();

            final Request request = new Request.Builder()
                    .url(url[0])
                    .build();

            Log.d(TAG, "doInBackground: request:" + request.toString());
            return client.newCall(request).execute();

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("get", "getDetail: erroe");
            return null;
        }
    }

    @Override
    protected void onPostExecute(Response response) {
        super.onPostExecute(response);
        //在此添加后台进程完成后的操作。
    }
}
