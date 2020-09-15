package com.yzk.launcher;

import android.app.Application;
import android.os.CountDownTimer;
import android.util.Log;

import com.yzk.launcher.utils.ThirdSDKUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by yzk on 2020/8/13
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        init();

        // --> 缓存（内存 & 本地） --> 网络
        // TODO 是否作为 "只从本地缓存获取试验接口" 的数据源？？？，
        // 只从缓存读取（数据源：初始化拉取试验，定时拉取、单个试验接口拉取）

//        String json = "{\n" +
//                "    \"experiment_id\":\"1\",\n" +
//                "    \"experiment_group_id\":\"123\",\n" +
//                "    \"is_control_group\":false,\n" +
//                "    \"debug_mode\":true,\n" +
//                "    \"config\":{\n" +
//                "        \"variables\":\"{\\\"a\\\":\\\"Hello\\\",\\\"b\\\":\\\"World\\\"}\",\n" +
//                "        \"type\":\"JSON\"\n" +
//                "    }\n" +
//                "}";
//        // timeout_milliseconds
//        // timeoutMilliseconds
//        try {
//            JSONObject defaultValue = new JSONObject(json);
//
//            Log.e("---json---",""+defaultValue.optJSONObject("config").optString("variables"));
//            asyncFetchABTest("", defaultValue, new OnABTestReceivedData<JSONObject>() {
//                @Override
//                public void success(JSONObject value) {
//
//
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        asyncFetchABTest("", 0, new OnABTestReceivedData<Integer>() {
//            @Override
//            public void success(Integer nteger) {
//
//
//            }
//        });
    }

    private void init() {
//        ThirdSDKUtil.initDataRangers(this);
        ThirdSDKUtil.initXunFeiVoice(this);
    }

    public static <V> void asyncFetchABTest(String experimentId, V defaultValue, OnABTestReceivedData<V> callBack){

    }


    interface OnABTestReceivedData<V>{
        void success(V value);
    }
}


