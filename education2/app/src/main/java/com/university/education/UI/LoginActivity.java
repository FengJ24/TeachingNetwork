package com.university.education.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.university.education.R;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText username;
    private EditText password;
    private Button login;
    private String mUsernameString;
    private String mPasswordString;
    private ImageView base_activity_back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        base_activity_back = (ImageView) findViewById(R.id.base_activity_back);
        login.setOnClickListener(this);
        base_activity_back.setOnClickListener(this);
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
                    EventBus.getDefault().post(new EventBusBean(Constants.LOGIN_SUCCESS, ""));
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
