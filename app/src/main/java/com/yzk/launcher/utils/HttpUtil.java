package com.yzk.launcher.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class HttpUtil {

	/**
	 * 根据传入的 url 地址，返回一个 String 字符串,
	 * GET 请求，这里用于读取得到 json 的字符串
	 * */
    public static String getNetString(String url) {
        BufferedReader br = null;
        try {
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            con.setDoInput(true);
            con.connect();
            if (con.getResponseCode() == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder datas = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    datas.append(line);
                }
                return datas.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 下载图片到手机内存
     */
    public static byte[] downLoadImage(String urlpath) {

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            URL url = new URL(urlpath);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();

            if (con.getResponseCode() == 200) {
                // 获取内容总长度
                long totalLength = con.getContentLength();
                BufferedInputStream bi = new BufferedInputStream(con.getInputStream());

                int currentLength = 0;
                int temp = 0;
                byte[] buff = new byte[1024];
                while ((temp = bi.read(buff)) != -1) {
                    currentLength += temp;
                    int progress = (int) ((1.0f * currentLength / totalLength) * 100);
                    //publishProgress用于通知AsyncTask的onProgressUpdate()更新进度
                    //publishProgress(progress);
                    output.write(buff, 0, temp);
                    output.flush();

                }
                return output.toByteArray();

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
				output.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * POST请求
     */

    public static String postData(String urlpath, Map<String, String> params) {
        BufferedOutputStream bo = null;
        BufferedReader br = null;
        try {
            HttpURLConnection con = (HttpURLConnection) new URL(urlpath).openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.connect();
            bo = new BufferedOutputStream(con.getOutputStream());
            Set<String> set = params.keySet();
            Iterator<String> iterator = set.iterator();
            StringBuilder str = new StringBuilder();
            while (iterator.hasNext()) {
                String key = iterator.next();
                String name = params.get(key);
                str.append(key).append("=").append(name).append("&");
            }
            str = str.deleteCharAt(str.length() - 1);

            bo.write(str.toString().getBytes());
            bo.flush();
            if (con.getResponseCode() == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder datas = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    datas.append(line);
                }
                return datas.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                    bo.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
