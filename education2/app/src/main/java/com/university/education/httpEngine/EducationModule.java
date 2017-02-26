package com.university.education.httpEngine;

import android.app.Activity;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import com.university.education.constants.Constants;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jian on 2016/12/25.
 */

public class EducationModule {
    private Activity mActivity;
    private static final String TAG = "EducationModule";
    private Handler okHttpHandler;


    public EducationModule(Activity activity) {
        mActivity = activity;
        if (okHttpHandler == null) {
            okHttpHandler = new Handler();
        }

    }

    /**
     * 请求教务通知的信息
     */
    public void getTeachNotifacationData(int typeId, final EducationResponseListener educationResponseListener) {
        OkHttpClient okHttpClient = new OkHttpClient();
        int i = okHttpClient.connectTimeoutMillis();
        Request build = new Request.Builder().url("http://218.25.35.28/index.php?wskm=category&act=list&id=" + typeId).get().build();
        //                                         http://218.25.35.28/index.php?wskm=category&act=list&id=20
        okHttpClient.newCall(build).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("失败" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final Document document = Jsoup.parse(response.body().string());
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        educationResponseListener.onSuccess(document);
                    }
                });

            }
        });
    }

    /**
     * 请求教务通知的信息---加载更多
     */
    public void getTeachNotifacationLoadMoreData(final EducationResponseListener educationResponseListener, int typeId, int index) {
        OkHttpClient okHttpClient = new OkHttpClient();
        int i = okHttpClient.connectTimeoutMillis();
        Request build = new Request.Builder().url("http://218.25.35.28/?wskm=category&act=list&id=" + typeId + "&page=" + index).get().build();
        okHttpClient.newCall(build).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("失败" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final Document document = Jsoup.parse(response.body().string());
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        educationResponseListener.onSuccess(document);
                    }
                });

            }
        });
    }

    /**
     * 获取教务通知的详情
     *
     * @param educationResponseListener
     * @param url
     */
    public void getTeachNotifacationDetail(final EducationResponseListener educationResponseListener, String url) {
        OkHttpClient okHttpClient = new OkHttpClient();
        int i = okHttpClient.connectTimeoutMillis();
        Request build = new Request.Builder().url("http://218.25.35.28" + url).get().build();
        okHttpClient.newCall(build).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("失败" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final Document document = Jsoup.parse(response.body().string());
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        educationResponseListener.onSuccess(document);
                    }
                });

            }
        });
    }

    /**
     * 下载文档或者Excel
     *
     * @param educationResponseListener
     * @param url                       文档的链接
     */
    public void downDocOrExcel(final EducationResponseListener educationResponseListener, String url) {
        OkHttpClient okHttpClient = new OkHttpClient();
        int i = okHttpClient.connectTimeoutMillis();
        Request build = new Request.Builder().url("http://218.25.35.28" + url).get().build();
        okHttpClient.newCall(build).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("失败" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final Document document = Jsoup.parse(response.body().string());
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        educationResponseListener.onSuccess(document);
                    }
                });

            }
        });
    }



    /**
     * 下载文件
     *
     * @param fileUrl  文件url
     * @param progressCallBack 存储目标目录
     */
    public void downLoadFile(String fileUrl,String fileName,final ReqProgressCallBack progressCallBack) {
        String filePath = Environment.getExternalStorageDirectory() + Constants.FILE_SAVE_PATH;
        OkHttpClient okHttpClient = new OkHttpClient();
        File file1 = new File(filePath);
        file1.mkdirs();
        final File file = new File(file1, fileName);
        if (file.exists()) {
            successCallBack(file, progressCallBack);
            return;
        }
        try {
            boolean newFile =  file.createNewFile();
        } catch (IOException e) {
//            e.printStackTrace();
        }
        final Request request = new Request.Builder().url("http://218.25.35.28" + fileUrl).build();
        final Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, e.toString());
                failedCallBack("下载失败", progressCallBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    long total = response.body().contentLength();
                    Log.e(TAG, "total------>" + total);
                    long current = 0;
                    is = response.body().byteStream();
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        current += len;
                        fos.write(buf, 0, len);
                        Log.e(TAG, "current------>" + current);
                        progressCallBack(total, current, progressCallBack);
                    }
                    fos.flush();
                    successCallBack(file, progressCallBack);
                } catch (IOException e) {
                    Log.e(TAG, e.toString());
                    failedCallBack("下载失败", progressCallBack);
                } finally {
                    try {
                        if (is != null) {
                            is.close();
                        }
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, e.toString());
                    }
                }
            }
        });
    }

    /**
     * 下载失败
     *
     * @param res      下载失败的需要显示的内容
     * @param callBack 回调接口
     */
    private void failedCallBack(final String res, final ReqProgressCallBack callBack) {
        okHttpHandler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onFail(res);
            }
        });
    }

    /**
     * 下载成功
     *
     * @param file
     * @param callBack
     */
    private void successCallBack(final File file, final ReqProgressCallBack callBack) {
        okHttpHandler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onSuccesss(file);
            }
        });

    }

    /**
     * 进度设置
     *
     * @param total    总大小
     * @param current  当前大小
     * @param callBack 接口
     */
    private void progressCallBack(final long total, final long current, final ReqProgressCallBack callBack) {
        okHttpHandler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onProgress(total, current);
            }
        });

    }


    public interface EducationResponseListener {

        void onSuccess(Document document);
    }

    /**
     * 进度接口
     */
    public interface ReqProgressCallBack {
        /**
         * 响应进度更新
         */
        void onProgress(long total, long current);

        /**
         * 下载成功
         *
         * @param file 下载的文件
         */
        void onSuccesss(File file);

        /**
         * 下载失败
         *
         * @param tip
         */
        void onFail(String tip);
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
