package top.yimiaohome.zhuhai_busapplication.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import top.yimiaohome.zhuhai_busapplication.Database.ExcelAsyncTask;
import top.yimiaohome.zhuhai_busapplication.R;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 0;
    static List<String> PERMISSIONS = new ArrayList<String>();
    private Button openRealTimeBusQueryBtn;
    private Button openRouteSearchBtn;

    public static Activity getCurrentActivity () {
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(
                    null);
            Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
            activitiesField.setAccessible(true);
            Map activities = (Map) activitiesField.get(activityThread);
            for (Object activityRecord : activities.values()) {
                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);
                if (!pausedField.getBoolean(activityRecord)) {
                    Field activityField = activityRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    Activity activity = (Activity) activityField.get(activityRecord);
                    return activity;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ExcelAsyncTask task=new ExcelAsyncTask(getApplicationContext());
        task.execute();
        openRealTimeBusQueryBtn = (Button) findViewById(R.id.open_realtime_bus_query_btn);
        openRouteSearchBtn = (Button) findViewById(R.id.open_route_search_btn);
        openRealTimeBusQueryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RealTimeBusQueryActivity.class);
                startActivity(intent);
            }
        });
        openRouteSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MapActivity.class);
                startActivity(intent);
            }
        });
        PERMISSIONS.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        PERMISSIONS.add(Manifest.permission.ACCESS_FINE_LOCATION);
        PERMISSIONS.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        PERMISSIONS.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        PERMISSIONS.add(Manifest.permission.READ_PHONE_STATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        PERMISSIONS.forEach(p->{
            if (ContextCompat.checkSelfPermission(this,(String)p)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{(String)p},REQUEST_CODE);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //可在此继续其他操作。
        if (requestCode == REQUEST_CODE) {
            if (PERMISSIONS.contains(permissions))
                Toast.makeText(getApplicationContext(), "取消此权限将无法使用公交出行路线规划功能", Toast.LENGTH_LONG).show();
        }
    }
}
