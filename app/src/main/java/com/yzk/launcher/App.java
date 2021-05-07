package com.yzk.launcher;

import android.app.Application;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;

import com.yzk.launcher.utils.HttpUtil;
import com.yzk.launcher.utils.ThirdSDKUtil;

import org.json.JSONException;
import org.json.JSONObject;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by yzk on 2020/8/13
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        init();

//        String a ="{\"status\":\"SUCCESS\",\"results\":[{\"abtest_experiment_id\":\"63\",\"abtest_experiment_group_id\":\"0\",\"is_control_group\":true,\"is_white_list\":false,\"variables\":[{\"name\":\"color1\",\"value\":\"1\",\"type\":\"INTEGER\"},{\"name\":\"color2\",\"value\":\"111\",\"type\":\"STRING\"},{\"name\":\"color3\",\"value\":\"true\",\"type\":\"BOOLEAN\"},{\"name\":\"color4\",\"value\":\"{\\\"a\\\":\\\"Hello\\\",\\\"b\\\":\\\"World\\\"}\",\"type\":\"JSON\"}]},{\"abtest_experiment_id\":\"100\",\"abtest_experiment_group_id\":\"0\",\"is_control_group\":true,\"is_white_list\":false,\"variables\":[{\"name\":\"100\",\"value\":\"1\",\"type\":\"INTEGER\"}]}]}";
//
////            ByteArrayOutputStream bos = new ByteArrayOutputStream();
////            ObjectOutputStream oos = new ObjectOutputStream(bos);
////            oos.writeObject(jsonObject);
//        try {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("a","{\"b\":\"c\"}");
//            jsonObject.put("d",new JSONObject());
//            Log.i("哦哦",jsonObject.toString());
//            String base64 = android.util.Base64.encodeToString(jsonObject.toString().getBytes(), android.util.Base64.NO_WRAP);
//            Log.i("哦哦",base64);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }





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


        // JSON  类型
//        try {
//            JSONObject defaultValue = new JSONObject(对照组的默认值);
//            SensorsABTest.shareInstance().fastFetchABTest(${具体的试验 ID}, defaultValue, new OnABTestReceivedData<JSONObject>() {
//                @Override
//                public void onResult(JSONObject result) {
//                    // TODO 请根据 result 进行自己的试验
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        asyncFetchABTest("", null, new OnABTestReceivedData<Boolean>() {
//            @Override
//            public void success(Boolean result) {
//                switch (result) {
//                    case 0:
//                        //对照组的处理逻辑
//                        break;
//                    case 1:
//                        //试验组 1 的处理逻辑
//                        break;
//                    case 2:
//                        //试验组 2 的处理逻辑
//                        break;
//                    //……
//                    //……
//                    default:
//                        //其他情况的处理逻辑
//                        break;
//                }

//            }
//        });

    }


    private void init() {
//        ThirdSDKUtil.initDataRangers(this);
        ThirdSDKUtil.initXunFeiVoice(this);
    }

//    @Deprecated
//    public static <V> void asyncFetchABTest(String experimentId, V defaultValue, OnABTestReceivedData<V> callBack) {
//
//    }
//
//
//    interface OnABTestReceivedData<V> {
//        void success(V value);
//    }
}


