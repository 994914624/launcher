package com.yzk.launcher.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

//import com.bumptech.glide.Glide;
//import com.yuyh.library.imgsel.ISNav;
//import com.yuyh.library.imgsel.common.ImageLoader;
//import com.yuyh.library.imgsel.config.ISCameraConfig;
//import com.yuyh.library.imgsel.config.ISListConfig;
import com.yzk.launcher.activity.AppsActivity;
import com.yzk.launcher.activity.WeatherActivity;
import com.yzk.launcher.custom.Lunar;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by yzk on 2020/8/19
 */

public class MainPageUtil {

    /*
     * 打开天气
     */
    public static void openWeather(Context context) {
        try {
            context.startActivity(new Intent(context, WeatherActivity.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开 Apps
     */
    public static void openApps(Context context) {
        try {
            context.startActivity(new Intent(context, AppsActivity.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开微信
     */
    public static void openWeixin(Context context) {
        try {
            Intent lan = context.getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (lan != null) {
                intent.setComponent(lan.getComponent());
            }
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开拨号页面
     */
    public static void openDialPage(Context context) {
        try {
            Intent dialIntent = new Intent(Intent.ACTION_CALL_BUTTON);//跳转到拨号界面
            context.startActivity(dialIntent);
        } catch (Exception e) {
            try {
                Intent intent = new Intent(Intent.ACTION_DIAL);//跳转到拨号界面
                context.startActivity(intent);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    /**
     * call phone
     */
    public static void callPhone(String phoneNum, Activity context) {
        try {
            if (Build.VERSION.SDK_INT >= 23) {
                int REQUEST_CODE_CONTACT = 1000;
                String[] permissions = {Manifest.permission.CALL_PHONE};
                //验证是否许可权限
                for (String str : permissions) {
                    if (context.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                        //申请权限
                        context.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                        return;
                    }
                }
            }
            Intent intent = new Intent(Intent.ACTION_CALL);
            Uri data = Uri.parse("tel:" + phoneNum);
            intent.setData(data);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 日期
     */
    public static void initTimeDateWeek(TextView tvDate, TextView tvAm, TextView tvPm, TextView tvTime) {
        try {
            Calendar today = Calendar.getInstance();
            today.setTime(new Date());
            String date = today.get(Calendar.MONTH) + 1 + "月" + today.get(Calendar.DATE) + "日";
            String week = "";
            switch (today.get(Calendar.DAY_OF_WEEK)) {
                case 1:
                    week = "星期天";
                    break;
                case 2:
                    week = "星期一";
                    break;
                case 3:
                    week = "星期二";
                    break;
                case 4:
                    week = "星期三";
                    break;
                case 5:
                    week = "星期四";
                    break;
                case 6:
                    week = "星期五";
                    break;
                case 7:
                    week = "星期六";
                    break;
            }
            // 设置时间
            setTime(tvAm, tvPm, tvTime);
            // 设置日期
            tvDate.setText(String.format("%S %S  农历%S", date, week, new Lunar(today).toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * 设置时钟时间
     */
    public static void setTime(TextView tvAm, TextView tvPm, TextView tvTime) {
        try {
            Calendar today = Calendar.getInstance();
            today.setTime(new Date());
            int hour = today.get(Calendar.HOUR);
            int minute = today.get(Calendar.MINUTE);

            if (today.get(Calendar.AM_PM) == 1) {
                tvPm.setText("下午");
                tvPm.setVisibility(View.VISIBLE);
                tvAm.setVisibility(View.GONE);
            } else {
                tvAm.setText("上午");
                tvAm.setVisibility(View.VISIBLE);
                tvPm.setVisibility(View.GONE);
            }
            if (hour == 0) {
                hour = 12;
            }
            if (minute < 10) {
                tvTime.setText(String.format("%s:0%s", hour, minute));
            } else {
                tvTime.setText(String.format("%s:%s", hour, minute));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static int TAKE_PHOTO_CODE = 1;
    public static int PICK_PHOTO_CODE = 2;

    /**
     * 选择照片
     */
    public static void pickPhoto(Context context) {
        // 自定义图片加载器
//        ISNav.getInstance().init(new ImageLoader() {
//            @Override
//            public void displayImage(Context context, String path, ImageView imageView) {
//                Log.i("TAG", "displayImage: " + path);
//                Glide.with(context).load(path).into(imageView);
//            }
//        });
//        ISListConfig config = new ISListConfig.Builder()
//                // 是否多选, 默认true
//                .multiSelect(false)
//                // 标题
//                .title("选择照片")
//                // 裁剪大小。needCrop 为 true 的时候配置
//                .cropSize(1, 1, 200, 200)
//                .needCrop(true)
//                .build();
//        // 跳转到图片选择器
//        ISNav.getInstance().toListActivity(context, config, PICK_PHOTO_CODE);
    }

    /**
     * 拍照
     */
    public static void takePhoto(Context context) {
//        ISCameraConfig config = new ISCameraConfig.Builder()
//                .needCrop(true) // 裁剪
//                .cropSize(1, 1, 200, 200)
//                .build();
//
//        ISNav.getInstance().toCameraActivity(context, config, TAKE_PHOTO_CODE);
    }

    /**
     * 处理照片
     */
    public static void handlePhoto(int requestCode, int resultCode, Intent data) {

        if (requestCode == TAKE_PHOTO_CODE && resultCode == RESULT_OK && data != null) {
            String path = data.getStringExtra("result"); // 图片地址
            Log.i("TAG", "take photo: " + path);
//            Glide.with(getApplicationContext()).load(path).into(tmpImg);
        }
        // 图片选择结果回调
        if (requestCode == PICK_PHOTO_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra("result");
            for (String path : pathList) {
                Log.i("TAG", "pick photo: " + path);
//                Glide.with(getApplicationContext()).load(path).into(tmpImg);
            }
        }
    }
}
