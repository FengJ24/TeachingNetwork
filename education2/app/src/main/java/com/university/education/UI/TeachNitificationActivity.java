package com.university.education.UI;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.university.education.R;
import com.university.education.base.BaseActivity;
import com.university.education.constants.Constants;
import com.university.education.httpEngine.EducationModule;
import com.university.education.utils.FileUtils;

import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.File;

import static com.university.education.R.layout.activity_teach_nitification;

/**
 * Created by jian on 2017/2/15.
 */

public class TeachNitificationActivity extends BaseActivity implements View.OnClickListener {
    private ImageView base_activity_back;
    private TextView base_name;
    private LinearLayout base_activity_title;
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


    @Override
    public void initListener() {
        base_activity_back.setOnClickListener(this);
        down.setOnClickListener(this);
        open.setOnClickListener(this);
    }

    @Override
    public void initData() {
        closeDownAndOpen();
        base_name.setText("教务通知详情");
        Intent intent = getIntent();
        mUrl = intent.getStringExtra(Constants.TEACH_NOTIFICATION_URL);
        getDetailData(mUrl);
    }

    @Override
    public Object getContentView() {
        View inflate = LayoutInflater.from(this).inflate(activity_teach_nitification, null);
        base_activity_back = (ImageView) inflate.findViewById(R.id.base_activity_back);
        base_name = (TextView) inflate.findViewById(R.id.base_name);
        base_activity_title = (LinearLayout) inflate.findViewById(R.id.base_activity_title);
        title = (TextView) inflate.findViewById(R.id.title);
        publish = (TextView) inflate.findViewById(R.id.publish);
        watch_counnt = (TextView) inflate.findViewById(R.id.watch_counnt);
        content = (TextView) inflate.findViewById(R.id.content);
        down = (TextView) inflate.findViewById(R.id.down);
        open = (TextView) inflate.findViewById(R.id.open);
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
        String title = child.text();
        Element rinfo = document.select("div.rinfo").first();
        String publish = rinfo.child(1).text();
        String num = document.select("i.num").first().text();
        setDetailData(mContent.toString(), title, publish, num);
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
            case R.id.base_activity_back:
                finish();
                break;
            case R.id.down:
                down();
                break;
            case R.id.open:
                open();
                break;
        }
    }
}
