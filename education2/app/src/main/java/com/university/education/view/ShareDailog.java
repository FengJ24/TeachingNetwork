package com.university.education.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tencent.connect.share.QQShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.open.utils.ThreadManager;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.university.education.R;
import com.university.education.bean.ShareBean;
import com.university.education.constants.Constants;
import com.university.education.utils.Util;

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
    private static final int THUMB_SIZE = 150;
    public static IWXAPI api;

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
        api = WXAPIFactory.createWXAPI(mContext, Constants.APP_ID, false);
        api.registerApp(Constants.APP_ID);
        mTencent = Tencent.createInstance("1106117180", mContext.getApplicationContext());
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
                shareWX();
                break;
            case R.id.wx_friend:
                shareWXFriend();
                break;
        }

    }

    private void shareWXFriend() {
        WXWebpageObject webpage = new WXWebpageObject();
        WXMediaMessage msg = new WXMediaMessage(webpage);

        if (mShareBean.getSource().equals(Constants.WEBVIEW_SHARE)) {
            if (mShareBean.getType() == null) {
                msg.title = mShareBean.getTitle();
            } else {
                msg.title = mShareBean.getType() + "(" + mShareBean.getTitle() + ")";
            }
            webpage.webpageUrl = mShareBean.getShareUrl();
        } else {
            if (mShareBean.getType() == null) {
                msg.title = mShareBean.getTitle();
            } else {
                msg.title = mShareBean.getType() + "(" + mShareBean.getTitle() + ")";
            }
            webpage.webpageUrl = "http://218.25.35.28" + mShareBean.getShareUrl();
        }
        msg.description = mShareBean.getTitle();
        Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.app_icon_108);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        bmp.recycle();
        msg.thumbData = Util.bmpToByteArray(thumbBmp, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);
    }

    private void shareWX() {
        WXWebpageObject webpage = new WXWebpageObject();
        WXMediaMessage msg = new WXMediaMessage(webpage);
        if (mShareBean.getSource().equals(Constants.WEBVIEW_SHARE)) {
            msg.title = "理工要闻";
            webpage.webpageUrl = mShareBean.getShareUrl();
        } else {
            msg.title = mShareBean.getType();
            webpage.webpageUrl = "http://218.25.35.28" + mShareBean.getShareUrl();
        }
        msg.description = mShareBean.getTitle();
        Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.app_icon_108);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        bmp.recycle();
        msg.thumbData = Util.bmpToByteArray(thumbBmp, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        api.sendReq(req);

    }

    private void shareQQ() {
        final Bundle bundle = new Bundle();
        bundle.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);

        bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, mShareBean.getTitle());// 摘要
        if (mShareBean.getSource().equals(Constants.WEBVIEW_SHARE)) {
            bundle.putString(QQShare.SHARE_TO_QQ_TITLE, "理工要闻");// 标题
            bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, mShareBean.getShareUrl());// 内容地址

        } else if (mShareBean.getSource().equals(Constants.JIAOXUEWANG_SHARE)) {
            bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://218.25.35.28" + mShareBean.getShareUrl());// 内容地址
            bundle.putString(QQShare.SHARE_TO_QQ_TITLE, "教务通知");// 标题
        }
        bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "https://imgsa.baidu.com/baike/w=150/sign=85365409a1cc7cd9fa2d30dc09002104/dc54564e9258d1099a47e656d358ccbf6d814da9.jpg");// 网络图片地址　　params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "应用名称");// 应用名称
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

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

}
