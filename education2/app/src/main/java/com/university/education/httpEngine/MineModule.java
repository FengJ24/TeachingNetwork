package com.university.education.httpEngine;

import android.app.Activity;

import com.university.education.HttpApi.HttpApi;
import com.university.education.constants.Constants;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by jian on 2016/12/25.
 */

public class MineModule {
    private Activity mActivity;
    public final static int CONNECT_TIMEOUT = 60;
    public final static int READ_TIMEOUT = 100;
    public final static int WRITE_TIMEOUT = 60;

    public MineModule(Activity activity) {
        mActivity = activity;
    }

    /*登录*/
    public void login(String accountString, String passwrdString, final MineResponseListener mineResponseListerner) {
        OkHttpClient okHttpClient = new OkHttpClient();
        int i = okHttpClient.connectTimeoutMillis();
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody requestBody = builder.add("RadioButtonList1", "学生").add("TextBox1", accountString)
                .add("TextBox2", passwrdString).add("__VIEWSTATE", Constants.LOGIN_VIEWSTATE)
                .add("Button1", "").add("lbLanguage", "").build();
        Request request = new Request.Builder().url(HttpApi.LOGIN).post(requestBody).build();
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
                                        if (mineResponseListerner != null) {
                                            mineResponseListerner.success(document);
                                        }
                                    }
                                });
                            }
                        }
                );
    }

    /*获取个人课程表*/
    public void getClassTable(String xuehao, String name, boolean isFirst, final MineResponseListener mineResponseListerner) {
        OkHttpClient okHttpClient = new OkHttpClient();
        int i = okHttpClient.connectTimeoutMillis();
        Request.Builder builde = new Request.Builder().url(HttpApi.BASE + Constants.CLASS_TABLE + "xh=" + xuehao + "&xm=" + name + Constants.CLASS_TABLE_ID).get();
        Request request = builde.addHeader("Referer", HttpApi.BASE + Constants.FIRST_DEATIL + xuehao).build();
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
                                        if (mineResponseListerner != null) {
                                            mineResponseListerner.success(document);
                                        }
                                    }
                                });
                            }
                        }
                );
    }

    /*获取个人成绩*/
    public void getStudentGradde(String xuehao, String name, boolean isFirst, final MineResponseListener mineResponseListerner) {
        OkHttpClient okHttpClient = new OkHttpClient();
        int i = okHttpClient.connectTimeoutMillis();
        Request.Builder builde = new Request.Builder().url(HttpApi.QUERY_GRADE + Constants.STUDENT_GRADE + "xh=" + xuehao + "&xm=" + name + Constants.STUDENT_GRADE_ID).get();
        Request request = builde.addHeader("Referer", HttpApi.QUERY_GRADE + Constants.FIRST_DEATIL + xuehao).build();
        String s = HttpApi.BASE + Constants.CLASS_TABLE + "xh=" + xuehao + "&xm=" + name + Constants.CLASS_TABLE_ID;
        String s1 = HttpApi.BASE + Constants.FIRST_DEATIL + xuehao;
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
                                        if (mineResponseListerner != null) {
                                            mineResponseListerner.success(document);
                                        }
                                    }
                                });
                            }
                        }
                );
    }

    /*获取历年成绩*/
    public void getAllYearGradde(String xuehao, String name,String xuenian, String xueqi,String type,String viewState, boolean isFirst, final MineResponseListener mineResponseListerner) {
        OkHttpClient okHttpClient = new OkHttpClient();
        int i = okHttpClient.connectTimeoutMillis();
        String url = HttpApi.BASE + Constants.STUDENT_GRADE + "xh=" + xuehao + "&xm=" + name + Constants.STUDENT_GRADE_ID;
        RequestBody resquestBody = new FormBody.Builder().add("xh", xuehao).add("xm", name).add("gnmkdm", Constants.STUDENT_GRADE_ID).build();
        RequestBody muiltipartBody = new MultipartBody.Builder().addPart(resquestBody).setType(MultipartBody.FORM)
                .addFormDataPart("__EVENTTARGET", "")
                .addFormDataPart("__EVENTARGUMENT", "")
                .addFormDataPart("__VIEWSTATE", viewState)
                .addFormDataPart("hidLanguage", "")
                .addFormDataPart("ddlXN", xuenian)
                .addFormDataPart("ddlXQ", xueqi)
                .addFormDataPart("ddl_kcxz", "")
                .addFormDataPart("btn_xq", type).build();
        Request.Builder builde = new Request.Builder().url(HttpApi.BASE + Constants.STUDENT_GRADE + "xh=" + xuehao + "&xm=" + name + Constants.STUDENT_GRADE_ID)
                .post(muiltipartBody);
        Request request = builde.addHeader("Referer", encodeHeadInfo(url)).build();
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
                                        if (mineResponseListerner != null) {
                                            mineResponseListerner.success(document);
                                        }
                                    }
                                });
                            }
                        }
                );
    }

    /*获取不同学期的课表*/
    public void getDifClassTable(String xuehao, String name, String viewState, String xuenian, String xueqi, final MineResponseListener mineResponseListerner) {
        OkHttpClient okHttpClient = new OkHttpClient();
        int i = okHttpClient.connectTimeoutMillis();
        String url = HttpApi.BASE + Constants.CLASS_TABLE + "xh=" + xuehao + "&xm=" + name + Constants.CLASS_TABLE_ID;
        RequestBody resquestBody = new FormBody.Builder().add("xh", xuehao).add("xm", name).add("gnmkdm", Constants.CLASS_TABLE_ID).build();
        RequestBody muiltipartBody = new MultipartBody.Builder().addPart(resquestBody).setType(MultipartBody.FORM)
                .addFormDataPart("__EVENTTARGET", "xnd")
                .addFormDataPart("__EVENTARGUMENT", "")
                .addFormDataPart("__VIEWSTATE", viewState)
                .addFormDataPart("xnd", xuenian)
                .addFormDataPart("xqd", xueqi)
                .build();
        Request.Builder builde = new Request.Builder().url(HttpApi.BASE + Constants.CLASS_TABLE + "xh=" + xuehao + "&xm=" + name + Constants.CLASS_TABLE_ID)
                .post(muiltipartBody);
        Request request = builde.addHeader("Referer", encodeHeadInfo(url)).build();
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
                                        if (mineResponseListerner != null) {
                                            mineResponseListerner.success(document);
                                        }
                                    }
                                });
                            }
                        }
                );
    }

    /*获取等级考试本学期详情*/
    public void getLevelExam(String xuehao, String name, final MineResponseListener mineResponseListerner) {
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = HttpApi.BASE + Constants.LEVEL_EXAM + "xh=" + xuehao + "&xm=" + name + Constants.LEVEL_EXAM_ID;
        RequestBody resquestBody = new FormBody.Builder().add("xh", xuehao).add("xm", name).add("gnmkdm", Constants.LEVEL_EXAM_ID).build();
        Request.Builder builde = new Request.Builder().url(HttpApi.BASE + Constants.LEVEL_EXAM + "xh=" + xuehao + "&xm=" + name + Constants.LEVEL_EXAM_ID)
                .get();
        Request request = builde.addHeader("Referer", encodeHeadInfo(HttpApi.BASE + Constants.LEVEL_EXAM + "xh=" + xuehao)).build();
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
                                        if (mineResponseListerner != null) {
                                            mineResponseListerner.success(document);
                                        }
                                    }
                                });
                            }
                        }
                );
    }

    /*获取等级考试不同学期详情*/
    public void getLevelExamDif(String xuehao, String name, String viewState, String xueqi, final MineResponseListener mineResponseListerner) {
        OkHttpClient okHttpClient = new OkHttpClient();
        int i = okHttpClient.connectTimeoutMillis();
        String url = HttpApi.BASE + Constants.LEVEL_EXAM + "xh=" + xuehao + "&xm=" + name + Constants.LEVEL_EXAM_ID;
        RequestBody resquestBody = new FormBody.Builder().add("xh", xuehao).add("xm", name).add("gnmkdm", Constants.LEVEL_EXAM_ID).build();
        RequestBody muiltipartBody = new MultipartBody.Builder().addPart(resquestBody).setType(MultipartBody.FORM)
                .addFormDataPart("__EVENTTARGET", "xnd")
                .addFormDataPart("__EVENTARGUMENT", "")
                .addFormDataPart("__VIEWSTATE", viewState)
                .addFormDataPart("xq", xueqi)
                .addFormDataPart("kcxz", "全部")
                .build();
        Request.Builder builde = new Request.Builder().url(HttpApi.BASE + Constants.CLASS_TABLE + "xh=" + xuehao + "&xm=" + name + Constants.CLASS_TABLE_ID)
                .post(muiltipartBody);
        Request request = builde.addHeader("Referer", encodeHeadInfo(url)).build();
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
                                        if (mineResponseListerner != null) {
                                            mineResponseListerner.success(document);
                                        }
                                    }
                                });
                            }
                        }
                );
    }

    /*获取教学计划本学期详情*/
    public void getTeachPlan(String xuehao, String name, final MineResponseListener mineResponseListerner) {
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = HttpApi.BASE + Constants.TEACH_PLAN + "xh=" + xuehao + "&xm=" + name + Constants.TEACH_PLAN_ID;
        RequestBody resquestBody = new FormBody.Builder().add("xh", xuehao).add("xm", name).add("gnmkdm", Constants.TEACH_PLAN_ID).build();
        Request.Builder builde = new Request.Builder().url(HttpApi.BASE + Constants.TEACH_PLAN + "xh=" + xuehao + "&xm=" + name + Constants.TEACH_PLAN_ID)
                .get();
        Request request = builde.addHeader("Referer", encodeHeadInfo(HttpApi.BASE + Constants.TEACH_PLAN + "xh=" + xuehao)).build();
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
                                        if (mineResponseListerner != null) {
                                            mineResponseListerner.success(document);
                                        }
                                    }
                                });
                            }
                        }
                );
    }

    /*获取教学计划不同学期详情*/
    public void getTeachPlanDif(String xuehao, String name, String viewState, String xueqi, final MineResponseListener mineResponseListerner) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)//设置连接超时时间
                .build();
        String url = HttpApi.BASE + Constants.TEACH_PLAN + "xh=" + xuehao + "&xm=" + name + Constants.TEACH_PLAN_ID;
        RequestBody resquestBody = new FormBody.Builder().add("xh", xuehao).add("xm", name).add("gnmkdm", Constants.TEACH_PLAN_ID).build();
        RequestBody muiltipartBody = new MultipartBody.Builder().addPart(resquestBody).setType(MultipartBody.FORM)
                .addFormDataPart("__EVENTTARGET", "xq")
                .addFormDataPart("__EVENTARGUMENT", "")
                .addFormDataPart("__VIEWSTATE", viewState)
                .addFormDataPart("xq", xueqi)
                .addFormDataPart("kcxz", "全部")
                .build();
        Request.Builder builde = new Request.Builder().url(HttpApi.BASE + Constants.TEACH_PLAN + "xh=" + xuehao + "&xm=" + name + Constants.TEACH_PLAN_ID)
                .post(muiltipartBody);
        Request request = builde.addHeader("Referer", encodeHeadInfo(url)).build();
        okHttpClient.
                newCall(request).
                enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                System.out.println();
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                final Document document = Jsoup.parse(response.body().string());
                                mActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (mineResponseListerner != null) {
                                            mineResponseListerner.success(document);
                                        }
                                    }
                                });
                            }
                        }
                );
    }

    /*退出登录*/
    public void exit_Login(String xuehao, final MineResponseListener mineResponseListerner) {
        OkHttpClient okHttpClient = new OkHttpClient();
        int i = okHttpClient.connectTimeoutMillis();
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody requestBody = builder.add("__EVENTTARGET", "likTc").add("__EVENTARGUMENT", "")
                .add("__VIEWSTATE", Constants.AFTER_LOGIN).build();
        Request request = new Request.Builder().url(HttpApi.EXIT_LOGIN).post(requestBody).build();
        okHttpClient.
                newCall(request).
                enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                System.out.println();
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                final Document document = Jsoup.parse(response.body().string());
                                mActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (mineResponseListerner != null) {
                                            mineResponseListerner.success(document);
                                        }
                                    }
                                });
                            }
                        }
                );
    }

    public interface MineResponseListener {
        void success(Document document);
    }

    private static String encodeHeadInfo(String headInfo) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0, length = headInfo.length(); i < length; i++) {
            char c = headInfo.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                stringBuffer.append(String.format("\\u%04x", (int) c));
            } else {
                stringBuffer.append(c);
            }
        }
        return stringBuffer.toString();
    }
}
