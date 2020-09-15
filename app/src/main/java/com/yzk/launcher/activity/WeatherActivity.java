package com.yzk.launcher.activity;


import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.yzk.launcher.R;
import com.yzk.launcher.entity.WeatherEntity;
import com.yzk.launcher.utils.HttpUtil;
import com.yzk.launcher.utils.SpeechUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class WeatherActivity extends BaseActivity {

    // 腾讯天气接口
    private static final String WEATHER_URL_1 = "https://wis.qq.com/weather/common?weather_type=observe|forecast_24h&source=wxa";
    // 北京气象局
    private static String WEATHER_URL_2 = "https://bjwx.91weather.com/bjwx/app/forecast/day?";
    private static final String TAG = "WeatherActivity";
    //今天 明天 后天 大后天
    private ImageView imgToday;
    private ImageView imgTomorrow;
    private ImageView imgNextDay;
    private ImageView imgNextNextDay;
    private TextView locationCity;
    private ArrayList<ImageView> imgList = new ArrayList<ImageView>();
    // 高德定位
    public AMapLocationClient locationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        initView();
        SpeechUtil.initSpeech(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initLocation();
    }

    @Override
    protected void onStop() {
        super.onStop();
        SpeechUtil.stopSpeaking();
    }

    private void initView() {
        imgToday = (ImageView) findViewById(R.id.img_today);
        imgTomorrow = (ImageView) findViewById(R.id.img_tomorrow);
        imgNextDay = (ImageView) findViewById(R.id.img_nextDay);
        imgNextNextDay = (ImageView) findViewById(R.id.img_nextnextDay);
        locationCity = findViewById(R.id.location_city);
        imgList.add(imgToday);
        imgList.add(imgToday);
        imgList.add(imgTomorrow);
        imgList.add(imgNextDay);
        imgList.add(imgNextNextDay);
    }

    private void initLocation() {
        AMapLocationClientOption locationOption = new AMapLocationClientOption();
        locationOption.setOnceLocation(true);
        locationOption.setHttpTimeOut(30000);
        // 定位精度
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        // 初始化定位
        locationClient = new AMapLocationClient(getApplicationContext());
        locationClient.setLocationOption(locationOption);
        // 设置定位回调监听
        locationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {

                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        String province = aMapLocation.getProvince();// 城市信息
                        String city = aMapLocation.getCity();// 城市信息
                        double latitude = aMapLocation.getLatitude(); // 维度
                        double longitude = aMapLocation.getLongitude();// 经度

                        if (!TextUtils.isEmpty(city)) {
                            locationCity.setText(String.format("%s", city));
//                            Log.e(TAG, "gaode: " + province+" " + city);
//                            Log.e(TAG, "gaode: " + latitude +" "+ longitude);
                            // 天气
                            getWeatherData(province, city, latitude, longitude);
                        }
                    }
                }
            }
        });
        // 启动定位
        locationClient.startLocation();
    }


    private void getWeatherData(final String province, final String city, final double latitude, final double longitude) {
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String url = WEATHER_URL_1 + "&province=" + province + "&city=" + city;
                    try {

                        String json = HttpUtil.getNetString(url);
                        JSONObject jsonObject = new JSONObject(json).optJSONObject("data").optJSONObject("forecast_24h");
                        //
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(SpeechUtil.getDateWeekLunar());
                        for (int i = 1; i < 5; i++) {
                            WeatherEntity weatherEntity = JSON.parseObject(jsonObject.optJSONObject("" + i).toString(), WeatherEntity.class);
                            String wea = weatherEntity.getDay_weather();
                            setImgIcon(wea, imgList.get(i));
                            //
                            String day;
                            if (i == 1) {
                                day = "";
                            } else if (i == 2) {
                                day = "明天 ";
                            } else if (i == 3) {
                                day = "后天 ";
                            } else {
                                day = "大后天 ";
                            }
                            stringBuilder.append(day).append("  天气 ").append(weatherEntity.getDay_weather()).append("   ");
                            stringBuilder.append(" 最低气温  ").append(weatherEntity.getMin_degree()).append("℃   ");
                            stringBuilder.append(" 最高气温  ").append(weatherEntity.getMax_degree()).append("℃   ");
                            stringBuilder.append(weatherEntity.getDay_wind_direction()).append(weatherEntity.getDay_wind_power()).append("级\n          ");

                        }
                        // 播放语音
                        SpeechUtil.startSpeaking(stringBuilder.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                        // 备用接口
                        try {
                            String json = HttpUtil.getNetString(WEATHER_URL_2 + "lat=" + latitude + "&lon=" + longitude);
//                            Log.e("--------",""+json);
                            JSONArray jsonArray = new JSONObject(json).optJSONArray("detail");
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append(SpeechUtil.getDateWeekLunar());
                            for (int i = 1; i < 5; i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i - 1);
                                String wea = jsonObject.optString("weather");
                                setImgIcon(wea, imgList.get(i));
                                //
                                String day;
                                if (i == 1) {
                                    day = "";
                                } else if (i == 2) {
                                    day = "明天 ";
                                } else if (i == 3) {
                                    day = "后天 ";
                                } else {
                                    day = "大后天 ";
                                }
                                stringBuilder.append(day).append("  天气 ").append(jsonObject.optString("weather")).append("   ");
                                stringBuilder.append(" 最低气温  ").append(jsonObject.optString("minTemp")).append("℃   ");
                                stringBuilder.append(" 最高气温  ").append(jsonObject.optString("maxTemp")).append("℃   ");
                                String fx = jsonObject.optString("windDir").replace("风", "");
                                String fl = jsonObject.optString("windLevel").replace("阵风", " ");
                                stringBuilder.append(fx + "风").append(fl).append("\n          ");
                            }
                            // 播放语音
                            SpeechUtil.startSpeaking(stringBuilder.toString());
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置天气图标
     */
    private void setImgIcon(String str, ImageView imageView) {
        if (str.contains("雷阵雨")) {
            updateUI(imageView, R.mipmap.thunder_rain);
        } else if (str.contains("多云转雷阵雨")) {
            updateUI(imageView, R.mipmap.cloud_then_rain);
        } else if (str.contains("雨")) {
            updateUI(imageView, R.mipmap.rain);
        } else if (str.contains("小雪")) {
            updateUI(imageView, R.mipmap.snow_little);
        } else if (str.contains("大雪")) {
            updateUI(imageView, R.mipmap.snow_big);
        } else if (str.contains("雨加雪")) {
            updateUI(imageView, R.mipmap.snow_and_rain);
        } else if (str.contains("多云")) {
            updateUI(imageView, R.mipmap.cloud);
        } else if (str.contains("晴")) {
            updateUI(imageView, R.mipmap.sun);
        } else if (str.contains("阴") || str.contains("霾") || str.contains("雾")) {
            updateUI(imageView, R.mipmap.overcast);
        }
    }

    /**
     * 更新天气图标UI
     */
    private void updateUI(final ImageView imageView, final int res) {
        imageView.post(new Runnable() {
            @Override
            public void run() {
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageResource(res);
                AlphaAnimation animation = new AlphaAnimation(0, 1);// 透明度0变化到透明度为1
                animation.setDuration(1000);// 动画执行时间1s
                imageView.startAnimation(animation);
            }
        });
    }

}
