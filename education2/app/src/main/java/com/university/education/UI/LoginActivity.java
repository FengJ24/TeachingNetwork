package com.university.education.UI;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            EventBus.getDefault().post(new EventBusBean(Constants.LOGIN_SUCCESS, ""));
            super.handleMessage(msg);
        }
    };
    private CheckBox checkbox_login;


    @Override
    public void initListener() {
        checkbox_login.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PreferenceUtils.putBoolean(LoginActivity.this, "isStorePassWord", isChecked);
            }
        });
    }

    @Override
    public void initData(TextView base_name, ImageView base_activity_pic, ImageView base_activity_back) {
        base_name.setText("登录");
        showContentView();
        Boolean isStorePassWord = PreferenceUtils.getBoolean(LoginActivity.this, "isStorePassWord");
        if (isStorePassWord) {
            username.setText(PreferenceUtils.getString(LoginActivity.this, "acount"));
            String password = PreferenceUtils.getString(LoginActivity.this, "password1");
            String password2 = PreferenceUtils.getString(LoginActivity.this, Constants.PASSWORD);
            this.password.setText(password);
            checkbox_login.setChecked(true);
        } else {
            username.setText("");
            password.setText("");
            checkbox_login.setChecked(false);
        }
        base_activity_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public Object getContentView() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.activity_login, null);
        username = (EditText) inflate.findViewById(R.id.username);
        password = (EditText) inflate.findViewById(R.id.password);
        login = (Button) inflate.findViewById(R.id.login);
        checkbox_login = (CheckBox) inflate.findViewById(R.id.checkbox_login);
        login.setOnClickListener(this);

        return inflate;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                submit();
                connectNet(mUsernameString, mPasswordString);
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

                    PreferenceUtils.putString(LoginActivity.this, "acount", mUsernameString);
                    PreferenceUtils.putString(LoginActivity.this, "password1", mPasswordString);
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
            Toast.makeText(this, "学号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        mPasswordString = password.getText().toString().trim();
        if (TextUtils.isEmpty(mPasswordString)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}
