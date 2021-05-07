package com.yzk.launcher.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yzk.launcher.R;

public class WebViewActivity extends BaseActivity {

    private WebView webView = null;
    private static final String WEB_URL = "https://cn.bing.com/videos/search?q=%e8%b1%ab%e5%89%a7&FORM=HDRSC3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        initView();
    }

    private void initView() {
        webView = findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setDomStorageEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        webView.loadUrl(WEB_URL);
        webView.setWebChromeClient(new WebChromeClient() {
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                return super.shouldOverrideUrlLoading(view, url);
                if (url == null||url.contains("https://m.youku.com/download?")||url.contains("https://mbdapp.iqiyi.com/")||url.contains("http://cdn.data.video.iqiyi.com/")) return true;
                Log.i("shouldOverrideUrl", "" + url);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (url.startsWith("https://m.youku.com/video/id_")) {
                    String vid = url.substring(url.indexOf("id_") + 3, url.indexOf("?"));
                    Log.i("shouldOverrideUrl", "" + vid);
                    try {
                        // 打开优酷 App
                        intent.setData(Uri.parse("youku://ykshortvideo?source=a2h0j.10182321.limitedplaybutton&action=ykshortvideo&pagetype=playerPage&callup_type=clk&is_h5=1&data_vdotype=short&data_logver=v2&half=1&evokeType=0&vid=" + vid + "&url=ykshortvideo://video_play?vid=" + vid + "&instationType=H5_WAKE"));
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                } else if (url.startsWith("iqiyi://mobile/player?")) {
                    try {
                        // 打开爱奇艺
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                        // 返回2次
                        onBackPressed();
                        onBackPressed();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();
            webView.destroy();
            webView = null;
        }
    }
}
