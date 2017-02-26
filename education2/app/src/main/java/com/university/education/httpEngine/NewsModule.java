package com.university.education.httpEngine;

import android.app.Activity;

/**
 * Created by jian on 2016/12/25.
 */

public class NewsModule {
    private Activity mActivity;

    public NewsModule(Activity activity) {
        mActivity = activity;
    }
    /**获取学校概况*/
//    public
//    mViewById = (TextView)findViewById(R.id.text);
//    OkHttpClient okHttpClient = new OkHttpClient();
//    int i = okHttpClient.connectTimeoutMillis();
//    Request request = new Request.Builder().url("http://www.sylu.edu.cn/sylusite/").get().build();
//    okHttpClient.newCall(request).enqueue(new Callback() {
//        @Override
//        public void onFailure(Call call, IOException e) {
//            System.out.println("失败"+e.getMessage());
//        }
//
//        @Override
//        public void onResponse(Call call, Response response) throws IOException {
//            Document document = Jsoup.parse(response.body().string());
//            Element select = document.select("meta[content]").last();
//            Elements select1 = select.select("[content]");
//            final String content = select1.attr("content");
//            System.out.println(content);
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    mViewById.setText(content);
//                }
//            });
//        }
//    });
}
