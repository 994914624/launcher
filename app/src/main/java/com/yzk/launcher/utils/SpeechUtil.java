package com.yzk.launcher.utils;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.yzk.launcher.custom.Lunar;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by yzk on 2020/8/28
 */

public class SpeechUtil {

    private final static String TAG = "SpeechUtil";
    // 语音
    private static SpeechSynthesizer speechSynthesizer;

    /**
     * 初始化tts语音 及参数设置
     */
    public static void initSpeech(Context context) {
        try {
            // 初始化合成对象
            speechSynthesizer = SpeechSynthesizer.createSynthesizer(context, new InitListener() {
                @Override
                public void onInit(int i) {
                    if (i != ErrorCode.SUCCESS) {
                        Log.d(TAG, "initSpeech code = " + i);
                    }
                }
            });

            if (speechSynthesizer == null) {
                return;
            }
            // 根据合成引擎设置相应参数
            speechSynthesizer.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
            // 合成发音人
            speechSynthesizer.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");
            //设置合成语速
            speechSynthesizer.setParameter(SpeechConstant.SPEED, "20");
            //设置合成音调
            speechSynthesizer.setParameter(SpeechConstant.PITCH, "50");
            //设置合成音量
            speechSynthesizer.setParameter(SpeechConstant.VOLUME, "80");
            //设置播放器音频流类型
            speechSynthesizer.setParameter(SpeechConstant.STREAM_TYPE, "3");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 播放语音
     */
    public static void startSpeaking(String speechStr) {
        if (TextUtils.isEmpty(speechStr) || speechSynthesizer == null) {
            return;
        }
        speechSynthesizer.startSpeaking(speechStr, new SynthesizerListener() {
            @Override
            public void onSpeakBegin() {

            }

            @Override
            public void onBufferProgress(int i, int i1, int i2, String s) {

            }

            @Override
            public void onSpeakPaused() {

            }

            @Override
            public void onSpeakResumed() {

            }

            @Override
            public void onSpeakProgress(int i, int i1, int i2) {

            }

            @Override
            public void onCompleted(SpeechError speechError) {
                Log.d(TAG, "播放完成");
            }

            @Override
            public void onEvent(int i, int i1, int i2, Bundle bundle) {

            }
        });
    }

    /**
     * 停止语音
     */
    public static void stopSpeaking() {
        if (speechSynthesizer == null) {
            return;
        }
        speechSynthesizer.stopSpeaking();
    }

    /**
     * 日期
     */
    public static String getDateWeekLunar() {
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
            return String.format("今天 %S  %S     农历%S", date, week, new Lunar(today).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
