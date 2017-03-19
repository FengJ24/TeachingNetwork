package com.university.education.UI;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
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
import com.university.education.constants.Constants;


public class WebviewActivity extends BaseActivity {
    private TextView title_textview;
    private WebView webview;
    private String Strtitle = "";
    private String url = "";
    private ProgressDialog dialog;
    private ImageView base_activity_back;
    private TextView base_name;


    @Override
    public void initListener() {

    }

    @Override
    public Object getContentView() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.webview_activity, null);
        webview = (WebView) inflate.findViewById(R.id.webview);
        base_activity_back = (ImageView) inflate.findViewById(R.id.base_activity_back);
        base_name = (TextView) inflate.findViewById(R.id.base_name);
        base_activity_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        base_name.setText("消息详情");
        url = getIntent().getStringExtra("url");
        return inflate;
    }

    @Override
    public void initData() {
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
