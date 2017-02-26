package com.university.education.HttpApi;

import android.app.Activity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static java.util.concurrent.TimeUnit.MICROSECONDS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Created by jian on 2016/12/25.
 */

public class HttpEngine {
    private Activity activity;
    private OkHttpClient okHttpClient;

    public HttpEngine(Activity activity) {
        this.activity = activity;
        init(activity);
    }

    private void init(Activity activity) {
        okHttpClient = new OkHttpClient();
        okHttpClient = new OkHttpClient.Builder().connectTimeout(6000, MILLISECONDS).readTimeout(10000, MICROSECONDS).build();
    }

    /**
     * 异步post获取数据
     **/
    public void postData(String url, Map<String, String> params, final OnHttpResponseListener responseListener) {
        okHttpClient.newCall(buildPostRequest(url, params)).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                responseListener.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        responseListener.onSuccess(result);
                    }
                });
            }
        });
    }

    /**
     * 创建请求体
     **/
    private Request buildPostRequest(String url, Map<String, String> params) {
        if (params == null) {
            params = new HashMap<String, String>();
        }
        FormBody.Builder builder = new FormBody.Builder();
        //遍历集合拼接参数
        Set set = params.entrySet();
        Iterator i = set.iterator();
        while (i.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) i.next();
            builder.add(entry.getKey(), entry.getValue());
        }
        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
    }

    /**
     * 网络请求监听
     **/
    public interface OnHttpResponseListener {
        /**
         * 请求成功
         **/
        void onFailure(IOException e);

        /**
         * 请求失败
         **/
        void onSuccess(String response);
    }
}
