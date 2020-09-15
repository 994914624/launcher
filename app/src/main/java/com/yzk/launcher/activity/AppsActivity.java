package com.yzk.launcher.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzk.launcher.R;

import java.util.ArrayList;
import java.util.List;

public class AppsActivity extends BaseActivity {

    List<ResolveInfo> apps ;
    BaseAdapter baseAdapter;
    GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps);
        initView();
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<ResolveInfo> allApps = getPackageManager().queryIntentActivities(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER), 0);
                for(int i=0;i< allApps.size();i++){
                    if(filterApp(allApps.get(i).activityInfo.applicationInfo)){
                        apps.add(allApps.get(i));
                    }
                }
                gridView.post(new Runnable() {
                    @Override
                    public void run() {
                        baseAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    public boolean filterApp(ApplicationInfo info) {
        if("设置".equals(""+info.loadLabel(getPackageManager()))||"短信".equals(""+info.loadLabel(getPackageManager()))
                ||"应用商店".equals(""+info.loadLabel(getPackageManager()))||"电话".equals(""+info.loadLabel(getPackageManager()))){
            return true;
        }
        return (info.flags & ApplicationInfo.FLAG_SYSTEM) <= 0;
    }

    private void initView() {
        apps = new ArrayList<>();
        gridView = findViewById(R.id.apps_list);
        baseAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return apps.size();
            }

            @Override
            public Object getItem(int position) {
                return apps.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ImageView iv;
                TextView tv;

                if(convertView == null){
                    convertView = getLayoutInflater().inflate(R.layout.apps_item,null);

                }
                    iv = convertView.findViewById(R.id.item_apps_icon);
                    tv = convertView.findViewById(R.id.item_apps_name);

                ResolveInfo info = apps.get(position);
                iv.setImageDrawable(info.activityInfo.loadIcon(getPackageManager()));
                tv.setText(info.loadLabel(getPackageManager()));
                return convertView;
            }
        };
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Intent lan = getPackageManager().getLaunchIntentForPackage(apps.get(position).activityInfo.packageName);
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (lan != null) {
                        intent.setComponent(lan.getComponent());
                    }
                    startActivity(intent);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        gridView.setAdapter(baseAdapter);
    }
}
