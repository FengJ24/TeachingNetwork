package com.university.education.UI;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.university.education.R;
import com.university.education.base.BaseFragment;
import com.university.education.bean.EventBusBean;
import com.university.education.constants.Constants;
import com.university.education.fragment.MeFragment;
import com.university.education.fragment.NewsFragment;
import com.university.education.fragment.TeachFragment;
import com.university.education.utils.PreferenceUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mViewById;
    private EditText account;
    private EditText passwrd;
    private Button login;
    private String mAccountString;
    private String mPasswrdString;
    private ArrayList<BaseFragment> fragmentList;
    private ArrayList<String> tip;
    private FragmentManager mSupportFragmentManager;
    private BaseFragment lastFragment;
    private FrameLayout main_framelayout;
    private RadioButton main_rb_fisrstpage;
    private RadioButton main_rb_discover;
    private RadioButton main_rb_me;
    private RadioGroup main_rg;
    private LinearLayout activity_main;
    private boolean isMe;
    private BaseFragment baseFragment = null;
    private String tag = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);

        }
        EventBus.getDefault().register(this);


    }

    private void initView() {
        main_framelayout = (FrameLayout) findViewById(R.id.main_framelayout);
        main_rb_fisrstpage = (RadioButton) findViewById(R.id.main_rb_fisrstpage);
        main_rb_discover = (RadioButton) findViewById(R.id.main_rb_discover);
        main_rb_me = (RadioButton) findViewById(R.id.main_rb_me);
        main_rg = (RadioGroup) findViewById(R.id.main_rg);
        main_rb_discover.setOnClickListener(this);
        main_rb_me.setOnClickListener(this);
        main_rb_fisrstpage.setOnClickListener(this);
        //默认选中综合
        main_rg.check(R.id.main_rb_fisrstpage);
        //获取FragmentManager
        mSupportFragmentManager = getSupportFragmentManager();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void selectFirstPager(EventBusBean eventBusBean) {
        if (eventBusBean.getType().equals(Constants.LOGIN_BACK)) {
            baseFragment = fragmentList.get(0);
            tag = "MULTIPLEFRAGMENT";
            main_rb_me.setChecked(false);
            main_rb_discover.setChecked(false);
            main_rb_fisrstpage.setChecked(true);
            //每次切换,将上一个隐藏掉
            mSupportFragmentManager.beginTransaction().hide(lastFragment).commit();
            if (tip.contains(tag)) {
                //如果标示集合中有这个fragment的标示那就直接show
                mSupportFragmentManager.beginTransaction().show(baseFragment).commit();
            } else {
                //标示集合中没有,说明没有add过  那么就add 将标示添加到集合中
                mSupportFragmentManager.beginTransaction().add(R.id.main_framelayout, baseFragment, tag).commit();
                tip.add(tag);
            }
            //将上一个lastFragment重新赋值
            lastFragment = baseFragment;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void selectMePager(final EventBusBean eventBusBean) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                if (eventBusBean.getType().equals(Constants.LOGIN_SUCCESS)) {
                    baseFragment = fragmentList.get(2);
                    tag = "ME";
                    main_rb_me.setChecked(true);
                    main_rb_discover.setChecked(false);
                    main_rb_fisrstpage.setChecked(false);
                    //每次切换,将上一个隐藏掉
                    mSupportFragmentManager.beginTransaction().hide(lastFragment). commitAllowingStateLoss();
                    if (tip.contains(tag)) {
                        //如果标示集合中有这个fragment的标示那就直接show
                        mSupportFragmentManager.beginTransaction().show(baseFragment). commitAllowingStateLoss();
                    } else {
                        //标示集合中没有,说明没有add过  那么就add 将标示添加到集合中
                        mSupportFragmentManager.beginTransaction().add(R.id.main_framelayout, baseFragment, tag).commitAllowingStateLoss();
                        tip.add(tag);
                    }
                    //将上一个lastFragment重新赋值
                    lastFragment = baseFragment;
                }
            }
        });

    }

    /**
     * 初始化数据
     */
    private void initData() {
        //将Fragment添加到集合中
        fragmentList = new ArrayList<BaseFragment>();
        fragmentList.add(new NewsFragment());
        fragmentList.add(new TeachFragment());
        // fragmentList.add(new DiscoverFragment());
        fragmentList.add(new MeFragment());
        //创建显示集合
        tip = new ArrayList<String>();
        //默认显示第一个
        mSupportFragmentManager.beginTransaction().add(R.id.main_framelayout, fragmentList.get(0),
                "MULTIPLEFRAGMENT").commit();
        tip.add("MULTIPLEFRAGMENT");
        //默认上一个lastFragment是MultipleFragment
        lastFragment = fragmentList.get(0);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.main_rb_me:
                if (PreferenceUtils.getString(this, Constants.USERNAME) != "" && PreferenceUtils.getString(this, Constants.PASSWORD) != "") {
                    baseFragment = fragmentList.get(2);
                    tag = "ME";
                    //每次切换,将上一个隐藏掉
                    mSupportFragmentManager.beginTransaction().hide(lastFragment).commit();
                    if (tip.contains(tag)) {
                        //如果标示集合中有这个fragment的标示那就直接show
                        mSupportFragmentManager.beginTransaction().show(baseFragment).commit();
                    } else {
                        //标示集合中没有,说明没有add过  那么就add 将标示添加到集合中
                        mSupportFragmentManager.beginTransaction().add(R.id.main_framelayout, baseFragment, tag).commit();
                        tip.add(tag);
                    }
                    //将上一个lastFragment重新赋值
                    lastFragment = baseFragment;
                    main_rb_me.setChecked(true);
                    main_rb_discover.setChecked(false);
                    main_rb_fisrstpage.setChecked(false);
                    isMe = false;
                } else {
                    //跳转登录
                    main_rb_me.setChecked(false);
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
                break;
            case R.id.main_rb_discover:
                baseFragment = fragmentList.get(1);
                tag = "TRENDSFRAGMENT";
                main_rb_me.setChecked(false);
                main_rb_discover.setChecked(true);
                main_rb_fisrstpage.setChecked(false);
                //每次切换,将上一个隐藏掉
                mSupportFragmentManager.beginTransaction().hide(lastFragment).commit();
                if (tip.contains(tag)) {
                    //如果标示集合中有这个fragment的标示那就直接show
                    mSupportFragmentManager.beginTransaction().show(baseFragment).commit();
                } else {
                    //标示集合中没有,说明没有add过  那么就add 将标示添加到集合中
                    mSupportFragmentManager.beginTransaction().add(R.id.main_framelayout, baseFragment, tag).commit();
                    tip.add(tag);
                }
                //将上一个lastFragment重新赋值
                lastFragment = baseFragment;
                break;
            case R.id.main_rb_fisrstpage:
                baseFragment = fragmentList.get(0);
                tag = "MULTIPLEFRAGMENT";
                main_rb_me.setChecked(false);
                main_rb_discover.setChecked(false);
                main_rb_fisrstpage.setChecked(true);
                //每次切换,将上一个隐藏掉
                mSupportFragmentManager.beginTransaction().hide(lastFragment).commit();
                if (tip.contains(tag)) {
                    //如果标示集合中有这个fragment的标示那就直接show
                    mSupportFragmentManager.beginTransaction().show(baseFragment).commit();
                } else {
                    //标示集合中没有,说明没有add过  那么就add 将标示添加到集合中
                    mSupportFragmentManager.beginTransaction().add(R.id.main_framelayout, baseFragment, tag).commit();
                    tip.add(tag);
                }
                //将上一个lastFragment重新赋值
                lastFragment = baseFragment;
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
