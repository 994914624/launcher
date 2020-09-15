package com.yzk.launcher.utils;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

//import com.bytedance.applog.AppLog;
//import com.bytedance.applog.ILogger;
//import com.bytedance.applog.InitConfig;
////import com.bytedance.applog.picker.Picker;
//import com.bytedance.applog.util.UriConstants;
//
//import java.util.HashMap;
//import java.util.Map;

/**
 * Created by yzk on 2020/8/15
 */

public class ThirdSDKUtil {

    public static void initDataRangers(Context context) {
//        /* 初始化开始 */
//        final InitConfig config = new InitConfig("your_appid", "your_channel"); // appid和渠道，appid如不清楚请联系客户成功经理
//
//        //上报域名只支持中国
//        config.setUriConfig (UriConstants.DEFAULT);
//
//        // 是否在控制台输出日志，可用于观察用户行为日志上报情况
//        config.setLogger(new ILogger() {
//            @Override
//            public void log(String s, Throwable throwable) {
//                Log.d("------->: ",""+s);
//            }
//        });
//
//        // 开启圈选埋点
//        config.setPicker(new Picker((Application) context, config));
//
//        // 开启AB测试
//        config.setAbEnable(true);
//
//        config.setAutoStart(true);
//        AppLog.init(context, config);
//        /* 初始化结束 */
//
//      	/* 自定义 “用户公共属性”（可选，初始化后调用, key相同会覆盖）
//      	关于自定义 “用户公共属性” 请注意：1. 上报机制是随着每一次日志发送进行提交，默认的日志发送频率是1分钟，所以如果在一分钟内连续修改自定义用户公共属性，，按照日志发送前的最后一次修改为准， 2. 不推荐高频次修改，如每秒修改一次 */
//        Map<String,Object> headerMap = new HashMap<String, Object>();
//        headerMap.put("level",8);
//        headerMap.put("gender","female");
//        AppLog.setHeaderInfo((HashMap<String, Object>)headerMap);
//
//
//        AppLog.onEventV3("play_video");
    }

    public static void initXunFeiVoice(Context context) {
        SpeechUtility.createUtility(context, "appid=59b36d25");
    }
}
