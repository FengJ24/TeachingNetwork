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

public class EmploymentModule {
    private Activity mActivity;

    public EmploymentModule(Activity activity) {
        mActivity = activity;
    }

    /**
     * 获取就业招聘数据信息
     *
     * @param employmentResponseListener
     */
    public void getEmployData(final EmploymentResponseListener employmentResponseListener) {
        OkHttpClient okHttpClient = new OkHttpClient();
        int i = okHttpClient.connectTimeoutMillis();
        Request request = new Request.Builder().url("http://zsjy.sylu.edu.cn/plus/list.php?tid=6").get().build();
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
                                        if (employmentResponseListener != null) {
                                            employmentResponseListener.success(document);
                                        }
                                    }
                                });
                            }
                        }
                );
    }

    /**
     * 获取文章信息
     *
     * @param employmentResponseListener
     */
    public void getPerfectArticle(String url,final EmploymentResponseListener employmentResponseListener) {
        OkHttpClient okHttpClient = new OkHttpClient();
        int i = okHttpClient.connectTimeoutMillis();
        Request request = new Request.Builder().url(url).get().build();
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
                                        if (employmentResponseListener != null) {
                                            employmentResponseListener.success(document);
                                        }
                                    }
                                });
                            }
                        }
                );
    }

    public interface EmploymentResponseListener {
        void success(Document document);
    }
}
