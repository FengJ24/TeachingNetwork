package com.university.education.UI;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.university.education.R;
import com.university.education.base.BaseActivity;
import com.university.education.bean.ShareBean;
import com.university.education.constants.Constants;
import com.university.education.view.ShareDailog;


public class WebviewActivity extends BaseActivity {
    private WebView webview;
    private String Strtitle = "";
    private String url = "";
    private ProgressDialog dialog;
    private String title;
    private FloatingActionButton web_share;
    private String name;


    @Override
    public void initListener() {

    }

    @Override
    public void initData(TextView base_name, ImageView base_activity_pic, ImageView base_activity_back) {
        if (title!= null){
            if (!TextUtils.isEmpty(title)){
                base_name.setText(title);
            }else{
                base_name.setText("消息详情");
            }
        }else{
            base_name.setText("消息详情");
        }
        webViewSetting();
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webview.setNetworkAvailable(true);
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });
        webview.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                mHandler.sendEmptyMessage(Constants.HandlerConstent.HTTP_ERROR);
            }

            public void onPageFinished(WebView view, String url) {
                mHandler.sendEmptyMessage(Constants.HandlerConstent.HTTP_SUCCESS);
            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                mHandler.sendEmptyMessage(Constants.HandlerConstent.HTTP_START);
            }
        });
        webview.loadUrl(url);
    }

    @Override
    public Object getContentView() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.webview_activity, null);
        webview = (WebView) inflate.findViewById(R.id.webview);
        web_share = (FloatingActionButton) inflate.findViewById(R.id.web_share);
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        name = getIntent().getStringExtra("name");

        web_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 分享
                 */
                    ShareDailog shareDailog = new ShareDailog(WebviewActivity.this);
                    shareDailog.setData(new ShareBean(url,name,Constants.WEBVIEW_SHARE));
                    shareDailog.show();

            }
        });
        return inflate;
    }


    /**
     * 璁剧疆webview
     */
    private void webViewSetting() {
        WebSettings ws = webview.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setJavaScriptCanOpenWindowsAutomatically(true);
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

        ws.setUseWideViewPort(true);

        ws.setSupportZoom(true);
        ws.setPluginState(PluginState.ON);
        ws.setLoadWithOverviewMode(true);
        ws.setGeolocationEnabled(true);
        ws.setDomStorageEnabled(true);
        ws.setDatabaseEnabled(true);
        // 閸氼垳鏁avascript
        ws.setJavaScriptEnabled(true);

        ws.setPluginState(PluginState.ON);
        ws.setRenderPriority(RenderPriority.HIGH);// 閿熸枻鎷烽敓鏂ゆ嫹
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.HandlerConstent.HTTP_SUCCESS:
                    showContentView();
                    break;
                case Constants.HandlerConstent.HTTP_ERROR:
                    break;
                case Constants.HandlerConstent.HTTP_START:
                    break;
            }
        }
    };


}
