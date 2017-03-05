package com.university.education.httpEngine;

import android.app.Activity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jian on 2016/12/25.
 */

public class NewsModule {
    private Activity mActivity;

    public NewsModule(Activity activity) {
        mActivity = activity;
    }

    /**
     * 获取首页信息
     *
     * @param newsResponseListener
     */
    public void getSchoolNewsData(final NewsResponseListener newsResponseListener) {
        OkHttpClient okHttpClient = new OkHttpClient();
        int i = okHttpClient.connectTimeoutMillis();
        Request request = new Request.Builder().url("http://www.sylu.edu.cn/sylusite/").get().build();
        okHttpClient.
                newCall(request).
                enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                final Document document = Jsoup.parse(response.body().string());
                                mActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (newsResponseListener != null) {
                                            newsResponseListener.success(document);
                                        }
                                    }
                                });
                            }
                        }
                );
    }

    public interface NewsResponseListener {
        void success(Document document);
    }
}
