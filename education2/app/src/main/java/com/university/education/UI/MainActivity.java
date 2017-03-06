package com.university.education.UI;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.university.education.R;
import com.university.education.base.BaseFragment;
import com.university.education.bean.EventBusBean;
import com.university.education.constants.Constants;
import com.university.education.fragment.MeFragment;
import com.university.education.fragment.NewsFragment;
import com.university.education.fragment.SchoolSceneFragment;
import com.university.education.fragment.TeachFragment;
import com.university.education.utils.PreferenceUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private View rootView;
    private FrameLayout main_framelayout;
    private ImageView activity_fragment_tab1_imageview;
    private TextView tab_textview1;
    private LinearLayout tab_linearlayout1;
    private ImageView activity_fragment_tab2_imageview;
    private TextView tab_textview2;
    private LinearLayout tab_linearlayout2;
    private ImageView activity_fragment_tab3_imageview;
    private TextView tab_textview3;
    private LinearLayout tab_linearlayout3;
    private ImageView activity_fragment_tab4_imageview;
    private TextView tab_textview4;
    private LinearLayout tab_linearlayout4;
    private BaseFragment baseFragment = null;
    private String tag = null;
    private ArrayList<BaseFragment> fragmentList;
    private FragmentManager mSupportFragmentManager;
    private BaseFragment lastFragment;
    private boolean mIsMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        setContentView(R.layout.activity_main);
        requestPermiss();
        initView();
        initData();
        EventBus.getDefault().register(this);


    }

    /**
     * 请求权限
     */
    private void requestPermiss() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }
    }

    private void initView() {
        this.main_framelayout = (FrameLayout) findViewById(R.id.main_framelayout);
        this.activity_fragment_tab1_imageview = (ImageView) findViewById(R.id.activity_fragment_tab1_imageview);
        this.tab_textview1 = (TextView) findViewById(R.id.tab_textview1);
        this.tab_linearlayout1 = (LinearLayout) findViewById(R.id.tab_linearlayout1);
        this.activity_fragment_tab2_imageview = (ImageView) findViewById(R.id.activity_fragment_tab2_imageview);
        this.tab_textview2 = (TextView) findViewById(R.id.tab_textview2);
        this.tab_linearlayout2 = (LinearLayout) findViewById(R.id.tab_linearlayout2);
        this.activity_fragment_tab3_imageview = (ImageView) findViewById(R.id.activity_fragment_tab3_imageview);
        this.tab_textview3 = (TextView) findViewById(R.id.tab_textview3);
        this.tab_linearlayout3 = (LinearLayout) findViewById(R.id.tab_linearlayout3);
        this.activity_fragment_tab4_imageview = (ImageView) findViewById(R.id.activity_fragment_tab4_imageview);
        this.tab_textview4 = (TextView) findViewById(R.id.tab_textview4);
        this.tab_linearlayout4 = (LinearLayout) findViewById(R.id.tab_linearlayout4);
        tab_linearlayout1.setOnClickListener(this);
        tab_linearlayout2.setOnClickListener(this);
        tab_linearlayout3.setOnClickListener(this);
        tab_linearlayout4.setOnClickListener(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void selectFirstPager(EventBusBean eventBusBean) {
        if (eventBusBean.getType().equals(Constants.LOGIN_BACK)) {
            baseFragment = fragmentList.get(0);
            //每次切换,将上一个隐藏掉
            mSupportFragmentManager.beginTransaction().hide(lastFragment).commit();
            if (baseFragment.isAdded()) {
                mSupportFragmentManager.beginTransaction().show(baseFragment).commit();
            } else {
                mSupportFragmentManager.beginTransaction().add(R.id.main_framelayout, baseFragment, tag).commit();
            }
            //将上一个lastFragment重新赋值
            lastFragment = baseFragment;
            changeTabColor(0);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void selectMePager(final EventBusBean eventBusBean) {
        if (eventBusBean.getType().equals(Constants.LOGIN_SUCCESS)) {
            if (mIsMe) {
                baseFragment = fragmentList.get(3);
                tag = "ME";
                //每次切换,将上一个隐藏掉
                mSupportFragmentManager.beginTransaction().hide(lastFragment).commitAllowingStateLoss();
                if (baseFragment.isAdded()) {
                    mSupportFragmentManager.beginTransaction().show(baseFragment).commit();
                } else {
                    mSupportFragmentManager.beginTransaction().add(R.id.main_framelayout, baseFragment, tag).commit();
                }
                //将上一个lastFragment重新赋值
                lastFragment = baseFragment;
                changeTabColor(3);
            }
        }

    }

    /**
     * 初始化数据
     */
    private void initData() {
        mSupportFragmentManager = getSupportFragmentManager();
        //将Fragment添加到集合中
        fragmentList = new ArrayList<BaseFragment>();
        fragmentList.add(new NewsFragment());
        fragmentList.add(new TeachFragment());
        fragmentList.add(new SchoolSceneFragment());
        fragmentList.add(new MeFragment());
        //默认显示第一个
        mSupportFragmentManager.beginTransaction().add(R.id.main_framelayout, fragmentList.get(0),
                "MULTIPLEFRAGMENT").commit();
        //默认上一个lastFragment是MultipleFragment
        lastFragment = fragmentList.get(0);
        changeTabColor(0);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_linearlayout1:
                baseFragment = fragmentList.get(0);
                //每次切换,将上一个隐藏掉
                mSupportFragmentManager.beginTransaction().hide(lastFragment).commit();
                if (baseFragment.isAdded()) {
                    mSupportFragmentManager.beginTransaction().show(baseFragment).commit();
                } else {
                    mSupportFragmentManager.beginTransaction().add(R.id.main_framelayout, baseFragment, tag).commit();
                }
                //将上一个lastFragment重新赋值
                lastFragment = baseFragment;
                changeTabColor(0);
                break;
            case R.id.tab_linearlayout2:
                baseFragment = fragmentList.get(1);
                tag = "TRENDSFRAGMENT";
                //每次切换,将上一个隐藏掉
                mSupportFragmentManager.beginTransaction().hide(lastFragment).commit();
                if (baseFragment.isAdded()) {
                    //如果标示集合中有这个fragment的标示那就直接show
                    mSupportFragmentManager.beginTransaction().show(baseFragment).commit();
                } else {
                    //标示集合中没有,说明没有add过  那么就add 将标示添加到集合中
                    mSupportFragmentManager.beginTransaction().add(R.id.main_framelayout, baseFragment, tag).commit();
                }
                //将上一个lastFragment重新赋值
                lastFragment = baseFragment;
                changeTabColor(1);
                break;
            case R.id.tab_linearlayout3:
                baseFragment = fragmentList.get(2);
                //每次切换,将上一个隐藏掉
                mSupportFragmentManager.beginTransaction().hide(lastFragment).commit();
                if (baseFragment.isAdded()) {
                    mSupportFragmentManager.beginTransaction().show(baseFragment).commit();
                } else {
                    mSupportFragmentManager.beginTransaction().add(R.id.main_framelayout, baseFragment, tag).commit();
                }
                //将上一个lastFragment重新赋值
                lastFragment = baseFragment;
                changeTabColor(2);
                break;

            case R.id.tab_linearlayout4:
                if (PreferenceUtils.getString(this, Constants.USERNAME) != "" && PreferenceUtils.getString(this, Constants.PASSWORD) != "") {
                    baseFragment = fragmentList.get(3);
                    //每次切换,将上一个隐藏掉
                    mSupportFragmentManager.beginTransaction().hide(lastFragment).commit();
                    if (baseFragment.isAdded()) {
                        mSupportFragmentManager.beginTransaction().show(baseFragment).commit();
                    } else {
                        mSupportFragmentManager.beginTransaction().add(R.id.main_framelayout, baseFragment, tag).commit();
                    }
                    //将上一个lastFragment重新赋值
                    lastFragment = baseFragment;
                    changeTabColor(3);
                } else {
                    //跳转登录
                    mIsMe = true;
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }

                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void changeTabColor(int index) {
        if (index == 0) {
            activity_fragment_tab1_imageview.setImageResource(R.drawable.main_rb_bg_first_select);
            activity_fragment_tab2_imageview.setImageResource(R.drawable.main_rb_bg_discover_unselect);
            activity_fragment_tab3_imageview.setImageResource(R.drawable.school_scene_no_choose);
            activity_fragment_tab4_imageview.setImageResource(R.drawable.main_rb_bg_me_unselect);
            tab_textview1.setTextColor(getResources().getColor(R.color.blue_item));
            tab_textview2.setTextColor(getResources().getColor(R.color.RadioButton_bg));
            tab_textview3.setTextColor(getResources().getColor(R.color.RadioButton_bg));
            tab_textview4.setTextColor(getResources().getColor(R.color.RadioButton_bg));
        } else if (index == 1) {
            activity_fragment_tab1_imageview.setImageResource(R.drawable.main_rb_bg_first_unselect);
            activity_fragment_tab2_imageview.setImageResource(R.drawable.main_rb_bg_discover_select);
            activity_fragment_tab3_imageview.setImageResource(R.drawable.school_scene_no_choose);
            activity_fragment_tab4_imageview.setImageResource(R.drawable.main_rb_bg_me_unselect);
            tab_textview1.setTextColor(getResources().getColor(R.color.RadioButton_bg));
            tab_textview2.setTextColor(getResources().getColor(R.color.blue_item));
            tab_textview3.setTextColor(getResources().getColor(R.color.RadioButton_bg));
            tab_textview4.setTextColor(getResources().getColor(R.color.RadioButton_bg));
        } else if (index == 2) {
            activity_fragment_tab1_imageview.setImageResource(R.drawable.main_rb_bg_first_unselect);
            activity_fragment_tab2_imageview.setImageResource(R.drawable.main_rb_bg_discover_unselect);
            activity_fragment_tab3_imageview.setImageResource(R.drawable.school_scene_choose);
            activity_fragment_tab4_imageview.setImageResource(R.drawable.main_rb_bg_me_unselect);
            tab_textview1.setTextColor(getResources().getColor(R.color.RadioButton_bg));
            tab_textview2.setTextColor(getResources().getColor(R.color.RadioButton_bg));
            tab_textview3.setTextColor(getResources().getColor(R.color.blue_item));
            tab_textview4.setTextColor(getResources().getColor(R.color.RadioButton_bg));
        } else if (index == 3) {
            activity_fragment_tab1_imageview.setImageResource(R.drawable.main_rb_bg_first_unselect);
            activity_fragment_tab2_imageview.setImageResource(R.drawable.main_rb_bg_discover_unselect);
            activity_fragment_tab3_imageview.setImageResource(R.drawable.school_scene_no_choose);
            activity_fragment_tab4_imageview.setImageResource(R.drawable.main_rb_bg_me_select);
            tab_textview1.setTextColor(getResources().getColor(R.color.RadioButton_bg));
            tab_textview2.setTextColor(getResources().getColor(R.color.RadioButton_bg));
            tab_textview3.setTextColor(getResources().getColor(R.color.RadioButton_bg));
            tab_textview4.setTextColor(getResources().getColor(R.color.blue_item));
        }
    }
}
