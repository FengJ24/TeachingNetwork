package com.university.education.UI;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.connect.share.QQShare;
import com.tencent.open.utils.ThreadManager;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.university.education.R;
import com.university.education.base.BaseActivity;
import com.university.education.bean.ShareBean;
import com.university.education.constants.Constants;
import com.university.education.httpEngine.EducationModule;
import com.university.education.utils.FileUtils;
import com.university.education.view.ShareDailog;

import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.File;

/**
 * Created by jian on 2017/2/15.
 */

public class TeachNitificationActivity extends BaseActivity implements View.OnClickListener {
    private TextView title;
    private TextView publish;
    private TextView watch_counnt;
    private TextView content;
    private String mUrl;
    private TextView down;
    private EducationModule mEducationModule;
    private String mDocOrExcelDown;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private StringBuffer mContent;
    private String fileName;
    private TextView open;
    private File mFile;
    private FloatingActionButton share;
    private String mTitle;
    private Tencent mTencent;
    private String mType;


    @Override
    public void initListener() {
        down.setOnClickListener(this);
        open.setOnClickListener(this);
        share.setOnClickListener(this);
    }

    @Override
    public void initData(TextView base_name, ImageView base_activity_pic, ImageView base_activity_back) {
        closeDownAndOpen();
        base_name.setText("教务通知详情");
        mTencent = Tencent.createInstance("1106103606", this.getApplicationContext());
        Intent intent = getIntent();
        mUrl = intent.getStringExtra(Constants.TEACH_NOTIFICATION_URL);
        mType = intent.getStringExtra("type");
        getDetailData(mUrl);
        base_activity_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



    @Override
    public Object getContentView() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.activity_teach_nitification, null);
        title = (TextView) inflate.findViewById(R.id.title);
        publish = (TextView) inflate.findViewById(R.id.publish);
        watch_counnt = (TextView) inflate.findViewById(R.id.watch_counnt);
        content = (TextView) inflate.findViewById(R.id.content);
        down = (TextView) inflate.findViewById(R.id.down);
        open = (TextView) inflate.findViewById(R.id.open);
        share = (FloatingActionButton) inflate.findViewById(R.id.share);
        return inflate;
    }

    /**
     * 进行网络请求和解析数据
     *
     * @param url 对应的网络地址
     */
    private void getDetailData(String url) {
        mEducationModule = new EducationModule(this);
        mEducationModule.getTeachNotifacationDetail(new EducationModule.EducationResponseListener() {

            @Override
            public void onSuccess(Document document) {
                showContentView();
                setSucessData(document);
            }
        }, mUrl);
    }

    /**
     * 设置请求成功的功能
     *
     * @param document 请求返回的doc对象
     */
    private void setSucessData(Document document) {
        mContent = new StringBuffer();
        Element first = document.select("div.hd").first();
        Element contentmain = document.select("div#contentmain").first();
        Elements p = contentmain.select("p");
        Element docOrExcel = contentmain.select("span.down").first();
        if (docOrExcel != null) {
            showDownAndOpen();
            Node node = docOrExcel.childNode(0);
            fileName = docOrExcel.text();
            Attributes attributes = node.attributes();
            mDocOrExcelDown = attributes.get("href");
            judgeIsDown(fileName);
        }
        for (int i = 0; i < p.size(); i++) {
            mContent = mContent.append(p.get(i).text() + "\n");
        }
        Element child = first.child(0);
        mTitle = child.text();
        Element rinfo = document.select("div.rinfo").first();
        String publish = rinfo.child(1).text();
        String num = document.select("i.num").first().text();
        setDetailData(mContent.toString(), mTitle, publish, num);
    }

    /**
     * 设置详情页数据
     *
     * @param contentStr 内容
     * @param titleStr   标题
     * @param publishStr 发行者
     * @param num        浏览量
     */
    private void setDetailData(String contentStr, String titleStr, String publishStr, String num) {
        title.setText(titleStr);
        content.setText(contentStr);
        publish.setText(publishStr);
        watch_counnt.setText("浏览量:" + num);
    }

    /**
     * 打开文件
     */
    private void open() {
        if (mFile != null) {
            new FileUtils(this).openFile(mFile);
        }
    }

    /**
     * 下载对应的表格和文档
     */
    private void down() {
        int permission = ActivityCompat.checkSelfPermission(TeachNitificationActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {//申请SD卡读写权限
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(TeachNitificationActivity.this, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        } else {
            if (mDocOrExcelDown != null) {
                mEducationModule.downLoadFile(mDocOrExcelDown, fileName, new EducationModule.ReqProgressCallBack() {


                    @Override
                    public void onProgress(long total, long current) {

                    }

                    @Override
                    public void onSuccesss(File file) {
                        mFile = file;
                        Toast.makeText(TeachNitificationActivity.this, "文件下载成功", Toast.LENGTH_SHORT).show();
                        setDownLoadSuccessData();
                    }

                    @Override
                    public void onFail(String tip) {

                    }
                });
            }
        }

    }

    /**
     * 显示下载和打开
     */
    private void showDownAndOpen() {
        open.setVisibility(View.VISIBLE);
        down.setVisibility(View.VISIBLE);
        open.setTextColor(Color.parseColor("#B4B4B4"));
        open.setBackgroundResource(R.drawable.shape_tv_bg);
        down.setTextColor(Color.parseColor("#FFFFFF"));
        down.setBackgroundColor(Color.parseColor("#4BA0F7"));
    }

    /**
     * 隐藏下载和打开
     */
    private void closeDownAndOpen() {
        open.setVisibility(View.INVISIBLE);
        down.setVisibility(View.INVISIBLE);

    }

    /**
     * 设置下载成功后的数据主要是设置下载和打开的背景
     */
    private void setDownLoadSuccessData() {
        down.setTextColor(Color.parseColor("#B4B4B4"));
        down.setBackgroundResource(R.drawable.shape_tv_bg);
        open.setTextColor(Color.parseColor("#FFFFFF"));
        open.setBackgroundColor(Color.parseColor("#4BA0F7"));
    }

    /**
     * 判断文件是否下载过
     *
     * @return
     */
    private void judgeIsDown(String fileName) {
        String filePath = Environment.getExternalStorageDirectory() + Constants.FILE_SAVE_PATH;
        File file1 = new File(filePath);
        mFile = new File(file1, fileName);
        if (mFile.exists()) {
            setDownLoadSuccessData();
        } else {
//            closeDownAndOpen();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.down:
                down();
                break;
            case R.id.open:
                open();
                break;
            case R.id.share:
                share();
                break;
        }
    }

    /**
     * 分享
     */
    private void share() {
        ShareDailog shareDailog = new ShareDailog(this);
        ShareBean shareBean = new ShareBean(mUrl, mTitle, Constants.JIAOXUEWANG_SHARE);
        shareBean.setType(mType);
        shareDailog.setData(shareBean);
        shareDailog.show();

    }
    private void shareQQ() {
        final Bundle bundle = new Bundle();
//        bundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
//        bundle.putString(QQShare.SHARE_TO_QQ_TITLE, mTitle);
////        bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY,"分享信息的主体内容")
//        bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, mUrl);
//        bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "https://imgsa.baidu.com/baike/w=150/sign=85365409a1cc7cd9fa2d30dc09002104/dc54564e9258d1099a47e656d358ccbf6d814da9.jpg");
//        bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, "沈阳理工大学");
//        bundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
//        bundle.putInt(QQShare.SHARE_TO_QQ_EXT_INT, 0);

        bundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE,QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        bundle.putString(QQShare.SHARE_TO_QQ_TITLE, "标题");// 标题
        bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, "要分享的摘要");// 摘要
        bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL,"http://www.qq.com/news/1.html");// 内容地址
        bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,"http://imgcache.qq.com/qzone/space_item/pre/0/66768.gif");// 网络图片地址　　params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "应用名称");// 应用名称
        bundle.putString(QQShare.SHARE_TO_QQ_EXT_INT, "其它附加功能");

        ThreadManager.getMainHandler().post(new Runnable() {

            @Override
            public void run() {
                mTencent.shareToQQ(TeachNitificationActivity.this, bundle, qqShareListener);
            }
        });



    }
    IUiListener qqShareListener = new IUiListener() {
        @Override
        public void onCancel() {
            System.out.println();
        }

        @Override
        public void onComplete(Object response) {
            // TODO Auto-generated method stub
            System.out.println();
        }

        @Override
        public void onError(UiError e) {
            // TODO Auto-generated method stub
            System.out.println();
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       super.onActivityResult(requestCode, resultCode, data);
        Tencent.onActivityResultData(requestCode, resultCode, data, qqShareListener);
    }
}
