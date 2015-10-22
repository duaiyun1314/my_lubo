package com.example.MyWeibo.network;


import android.util.Log;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wanglu on 15/4/13.
 */
public class NetWorkTask {
    private static NetWorkTask mTask;
    private OkHttpClient mClient;
    private static final int CONNECT_TIMEOUT_MILLLIS = 10 * 1000;

    private NetWorkTask() {
        initClient();
    }

    public static NetWorkTask getInstance() {
        if (mTask == null) {
            mTask = new NetWorkTask();
        }
        return mTask;
    }

    public String getStringByGet(String urlAddress) {
        try {
            Request request = new Request.Builder().url(urlAddress).build();
            Response response = mClient.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getStringOnce(String urlAddress) {
        try {
            Request request = getBuilder().url(urlAddress).build();
            Response response = mClient.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getStringByPost(String url, Map<String, String> requestParmas) {
        FormEncodingBuilder builder = new FormEncodingBuilder();
        Set<Map.Entry<String, String>> entrySet = requestParmas.entrySet();
        for (Iterator<Map.Entry<String, String>> iterator = entrySet.iterator(); iterator.hasNext(); ) {
            Map.Entry<String, String> entry = iterator.next();
            if (!entry.getKey().equals("method")) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        String once = getOnceStringFromHtmlResponseObject(getStringOnce(url));
        if (once != null) {
            builder.add("once", once);
        }
        Request request = getBuilder().url(url)
                .post(builder.build()).build();
        try {
            Response response = mClient.newCall(request).execute();
            if (response.isSuccessful()) {
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    private void initClient() {
        mClient = new OkHttpClient();
        mClient.setConnectTimeout(CONNECT_TIMEOUT_MILLLIS, TimeUnit.MILLISECONDS);
    }

    private Request.Builder getBuilder() {
        Request.Builder builder = new Request.Builder();
        builder.addHeader("Cache-Control", "max-age=0")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .addHeader("Accept-Charset", "utf-8, iso-8859-1, utf-16, *;q=0.7")
                .addHeader("Accept-Language", "zh-CN, en-US")
                .addHeader("X-Requested-With", "com.android.browser")
                .addHeader("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.2.1; en-us; M040 Build/JOP40D) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30")
                .addHeader("Host", "www.v2ex.com");
        return builder;
    }

    private static String getOnceStringFromHtmlResponseObject(String content) {
        Pattern pattern = Pattern.compile("<input type=\"hidden\" value=\"([0-9]+)\" name=\"once\" />");
        final Matcher matcher = pattern.matcher(content);
        if (matcher.find())
            return matcher.group(1);
        return null;
    }

    private static String getUsernameFromResponse(String content) {
        Pattern userPattern = Pattern.compile("<a href=\"/member/([^\"]+)\" class=\"top\">");
        Matcher userMatcher = userPattern.matcher(content);
        if (userMatcher.find())
            return userMatcher.group(1);
        return null;
    }

}
