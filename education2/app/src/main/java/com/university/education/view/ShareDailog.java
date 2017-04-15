package com.university.education.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tencent.connect.share.QQShare;
import com.tencent.open.utils.ThreadManager;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.university.education.R;
import com.university.education.bean.ShareBean;

/**
 * Created by jian on 2017/3/25.
 * 分享对话框
 */

public class ShareDailog extends Dialog implements View.OnClickListener {
    private TextView lesson_name;
    private TextView time;
    private TextView teacher;
    private TextView location;
    private Context mContext;
    private String mDetail;
    private LinearLayout qq;
    private LinearLayout wx;
    private LinearLayout wx_friend;
    private ShareBean mShareBean;
    private Tencent mTencent;

    public ShareDailog(Context context) {
        super(context, R.style.MainQuickOptionDailog);
        mContext = context;

    }

    public void setData(ShareBean shareBean) {
        mShareBean = shareBean;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_share);
        //根据APPID 获取入口信息
        mTencent = Tencent.createInstance("1106103606", mContext.getApplicationContext());
        //获取QQ分享对象
        this.qq = (LinearLayout) findViewById(R.id.qq);
        this.wx = (LinearLayout) findViewById(R.id.wx);
        this.wx_friend = (LinearLayout) findViewById(R.id.wx_friend);
        qq.setOnClickListener(this);
        wx.setOnClickListener(this);
        wx_friend.setOnClickListener(this);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        layoutParams.width = (int) (displayMetrics.widthPixels * 0.8f);
        layoutParams.height = (int) (displayMetrics.widthPixels * 0.4f);
        layoutParams.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(layoutParams);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.qq:
                shareQQ();
                break;
            case R.id.wx:
                break;
            case R.id.wx_friend:
                break;
        }

    }

    private void shareQQ() {
        final Bundle bundle = new Bundle();
        bundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE,QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
        bundle.putString(QQShare.SHARE_TO_QQ_TITLE, "教务通知");// 标题
        bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, mShareBean.getTitle());// 摘要
        bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL,"http://218.25.35.28"+mShareBean.getShareUrl());// 内容地址
        bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL,"https://imgsa.baidu.com/baike/w=150/sign=85365409a1cc7cd9fa2d30dc09002104/dc54564e9258d1099a47e656d358ccbf6d814da9.jpg");// 网络图片地址　　params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "应用名称");// 应用名称
        bundle.putString(QQShare.SHARE_TO_QQ_EXT_INT, "其它附加功能");
        ThreadManager.getMainHandler().post(new Runnable() {

            @Override
            public void run() {
                mTencent.shareToQQ((Activity) mContext, bundle, qqShareListener);
            }
        });

        dismiss();


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

}
