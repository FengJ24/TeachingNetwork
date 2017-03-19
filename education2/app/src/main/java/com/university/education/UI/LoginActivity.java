package com.university.education.UI;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.university.education.R;
import com.university.education.base.BaseActivity;
import com.university.education.bean.EventBusBean;
import com.university.education.constants.Constants;
import com.university.education.httpEngine.MineModule;
import com.university.education.utils.PreferenceUtils;

import org.greenrobot.eventbus.EventBus;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Created by jian on 2016/12/25.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText username;
    private EditText password;
    private Button login;
    private String mUsernameString;
    private String mPasswordString;
    private ImageView base_activity_back;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            EventBus.getDefault().post(new EventBusBean(Constants.LOGIN_SUCCESS, ""));
            super.handleMessage(msg);
        }
    };


    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        showContentView();
    }

    @Override
    public Object getContentView() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.activity_login, null);
        username = (EditText) inflate.findViewById(R.id.username);
        password = (EditText) inflate.findViewById(R.id.password);
        login = (Button) inflate.findViewById(R.id.login);
        base_activity_back = (ImageView) inflate.findViewById(R.id.base_activity_back);
        login.setOnClickListener(this);
        base_activity_back.setOnClickListener(this);

        return inflate;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                submit();
                connectNet(mUsernameString, mPasswordString);
                break;
            case R.id.base_activity_back:
                loginBack();
                break;
        }
    }

    /*登录界面返回*/
    private void loginBack() {
        //选中首页
        EventBus.getDefault().post(new EventBusBean(Constants.LOGIN_BACK, ""));
        finish();
    }

    private void connectNet(String usernameString, String passwordString) {

        new MineModule(this).login(usernameString, passwordString, new MineModule.MineResponseListener() {
            @Override
            public void success(Document document) {

                Elements title = document.select("title");
                if ("登录".equals(title.text())) {
                    Toast.makeText(LoginActivity.this, "学号或密码有误", Toast.LENGTH_SHORT).show();
                } else {
                    //登录成功,将账号和密码存储
                    PreferenceUtils.putString(LoginActivity.this, Constants.USERNAME, mUsernameString);
                    PreferenceUtils.putString(LoginActivity.this, Constants.PASSWORD, mPasswordString);
                    //学号和姓名
                    Elements select = document.select("div.info");
                    Elements userInfo = select.select("span#xhxm");
                    String text = userInfo.text();
                    String[] split = text.split(" ");
                    //将学号和姓名进行存储
                    PreferenceUtils.putString(LoginActivity.this, Constants.NAME, split[1]);
                    PreferenceUtils.putString(LoginActivity.this, Constants.XUEHAO, split[0]);
                    mHandler.sendEmptyMessage(0);
                    finish();
                }
            }
        });
    }

    private void submit() {
        // validate
        mUsernameString = username.getText().toString().trim();
        if (TextUtils.isEmpty(mUsernameString)) {
            Toast.makeText(this, "学号", Toast.LENGTH_SHORT).show();
            return;
        }

        mPasswordString = password.getText().toString().trim();
        if (TextUtils.isEmpty(mPasswordString)) {
            Toast.makeText(this, "密码", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}
