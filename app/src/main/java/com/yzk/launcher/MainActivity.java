package com.yzk.launcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yzk.launcher.activity.BaseActivity;
import com.yzk.launcher.adapter.MainAdapter;
import com.yzk.launcher.adapter.RecycleViewItemTouchHelper;
import com.yzk.launcher.interfaces.Phone;
import com.yzk.launcher.custom.CustomStaggeredGridLayoutManager;
import com.yzk.launcher.entity.ContactEntity;
import com.yzk.launcher.utils.ContactUtil;
import com.yzk.launcher.utils.MainPageUtil;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;


public class MainActivity extends BaseActivity implements Phone {

    private RecyclerView mRecyclerView;
    private MainAdapter mainAdapter;
    private String tmpNum;
    private TextView tvTime;
    private TextView tvAm;
    private TextView tvPm;
    private TextView tvDate;
    private View footerView;
    private Handler handler;
    private ItemTouchHelper mItemTouchHelper;
    // 当前显示的联系人
    private LinkedHashSet<String> displayContactIds = new LinkedHashSet<>();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            MainPageUtil.setTime(tvAm, tvPm, tvTime);
            // 每 30 秒更新一次时钟
            handler.postDelayed(runnable, 30000);
        }
    };
    private final static String TAG = "MainActivity";
    private final static String WEI_XIN = "微信";
    private final static String WEATHER = "天气";
    private final static String APPS = "应用";
    private final static String PHONE_CALL = "电话";
    private final static String XI_QU = "戏曲";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initAdapter();
        handler = new Handler(getMainLooper());
        handler.postDelayed(runnable, 30000);
        try {
            // 适配魅族 申请读取联系人权限
            if ((Build.VERSION.SDK_INT < 23)) {
                String[] cols = {ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
                Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        cols, null, null, null);
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        MainPageUtil.initTimeDateWeek(tvDate, tvAm, tvPm, tvTime);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000 && grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            MainPageUtil.callPhone(tmpNum, this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 获取选中的联系人
        ContactEntity contactEntity = ContactUtil.pickContactResult(data, this);
        if (contactEntity != null && !displayContactIds.contains(contactEntity.getContactId())) {
            displayContactIds.add(contactEntity.getContactId());
            mainAdapter.addData(contactEntity);
            mainAdapter.notifyDataSetChanged();
            // 存文件一份
            saveDataToSp(displayContactIds, getApplicationContext());
            Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "已添加过了", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        tvTime = findViewById(R.id.tv_time_main_top);
        tvDate = findViewById(R.id.tv_date_main_top);
        tvAm = findViewById(R.id.tv_am_main_top);
        tvPm = findViewById(R.id.tv_pm_main_top);
        footerView = findViewById(R.id.cl_item_footer);
        mRecyclerView = findViewById(R.id.main_recyclerView);
        //设置 2 列
        CustomStaggeredGridLayoutManager manager = new CustomStaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //解决 item 跳动
        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRecyclerView.setItemAnimator(null);
        mRecyclerView.setLayoutManager(manager);
        //
        footerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "添加联系人", Toast.LENGTH_SHORT).show();
                ContactUtil.pickContact(MainActivity.this);
            }
        });
    }

    private void initAdapter() {
        String[] names = {WEI_XIN, WEATHER, XI_QU, APPS, PHONE_CALL};
        int[] bgColors = {0xFF7BB740, 0xFF3C73D3, 0xFF8362D3, 0xFFF3AE35, 0xFF50B6AC};
        int[] imgIds = {R.mipmap.icon_wx, R.mipmap.icon_weather, R.mipmap.icon_xq, R.mipmap.icon_apps, R.mipmap.icon_call};

        List<ContactEntity> data = new ArrayList<>();
        // top 四个功能
        for (int i = 0; i < 5; i++) {
            ContactEntity contactEntity = new ContactEntity(names[i], bgColors[i], imgIds[i], 1);
            data.add(contactEntity);
        }

        // 填充之前已记录的联系人
        LinkedHashSet<String> hashSet = getDataFromSp();
        for (String contactId : hashSet) {
            displayContactIds.add(contactId);
            data.add(ContactUtil.getContactInfo(contactId, this));
        }

        mainAdapter = new MainAdapter(data, this);
        mainAdapter.openLoadAnimation();
        mRecyclerView.setAdapter(mainAdapter);

        // 拖动
        mItemTouchHelper = new ItemTouchHelper(new RecycleViewItemTouchHelper(mainAdapter, getApplicationContext()));
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
        mainAdapter.setItemTouchHelper(mItemTouchHelper);
    }

    @Override
    public void call(String phoneNumber) {
        // TODO call phone
        if (!TextUtils.isEmpty(phoneNumber)) {
            tmpNum = phoneNumber;
            MainPageUtil.callPhone(tmpNum, this);
        }
    }

    @Override
    public void topClick(String name) {
        switch (name) {
            case WEATHER:
                MainPageUtil.openWeather(this);
                break;
            case APPS:
                MainPageUtil.openApps(this);
                break;
            case WEI_XIN:
                MainPageUtil.openWeixin(this);
                break;
            case PHONE_CALL:
                MainPageUtil.openDialPage(this);
                break;
            case XI_QU:
                MainPageUtil.openWebView(this);
                break;
            default:
                break;
        }
    }

    @Override
    public void onContactLongClick(String contactId, int position) {
        Log.e("---onContactLClick----", "" + contactId + displayContactIds.toString());
        dialog(contactId, position);
    }

    private void dialog(final String contactId, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("是否移除联系人?")
                .setCancelable(true)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        displayContactIds.remove(contactId);
                        saveDataToSp(displayContactIds, getApplicationContext());
                        mainAdapter.remove(position);
                        Toast.makeText(MainActivity.this, "移除联系人", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).create().show();

    }

    /**
     * 从 SP 读取存的 contactId
     */
    private LinkedHashSet<String> getDataFromSp() {
        try {
            SharedPreferences sharedPreferences = getSharedPreferences("contacts_data", MODE_PRIVATE);
            String str = sharedPreferences.getString("contact_id",null);
            if(str != null){
                str = str.replace(" ","");
                String[] strArr = str.split(",");
                Log.e("eeee","----getDataFromSp---"+strArr.length);
                LinkedHashSet<String> linkedHashSet = new LinkedHashSet<String>(Arrays.asList(strArr));
                Log.e("eeee22","----getDataFromSp---"+linkedHashSet.toString());
                return linkedHashSet;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Collection
        return displayContactIds;
    }

    /**
     * contactId 存 SP
     */
    public static void saveDataToSp(LinkedHashSet displayContactIds, Context context) {
        if (context == null) return;
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences("contacts_data", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String str = displayContactIds.toString();
            str = str.replace("[","");
            str = str.replace("]","");
            str = str.replace(" ","");
            Log.e("eeee","----saveDataToSp---"+str);

            editor.putString("contact_id", str).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
