package com.KiteXu.AndroidTest.common;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Jack on 12/6/13.
 */
public class JsonData {

    private static String path;

    public JsonData(String path)
    {
        this.path = path;
    }

    public static String getJson()
    {
        String json = null;
        URL url;
        JSONArray result = null;

        try {
            url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url
                    .openConnection();// 利用HttpURLConnection对象,我们可以从网络中获取网页数据.
            conn.setConnectTimeout(5 * 1000); // 单位是毫秒，设置超时时间为5秒
            conn.setRequestMethod("GET"); // HttpURLConnection是通过HTTP协议请求path路径的，所以需要设置请求方式,可以不设置，因为默认为GET
            if (conn.getResponseCode() == 200)
            {// 判断请求码是否是200码，否则失败
                InputStream is = conn.getInputStream(); // 获取输入流
                byte[] data = readStream(is); // 把输入流转换成字符数组
                json = new String(data, "UTF-8"); // 把字符数组转换成字符串
            }
            else
            {
                throw new IOException();
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
//            e.printStackTrace();
            Log.v("@@@@@@", "error1");
        } catch (IOException e) {
            // TODO Auto-generated catch block
//            e.printStackTrace();
            Log.v("@@@@@@", "error2");
        }

        return json;
    }

    private static byte[] readStream(InputStream in) throws IOException {
        ByteArrayOutputStream baout = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = in.read(buffer)) != -1) {
            baout.write(buffer, 0, len);
        }
        baout.close();
        in.close();

        return baout.toByteArray();
    }
}
