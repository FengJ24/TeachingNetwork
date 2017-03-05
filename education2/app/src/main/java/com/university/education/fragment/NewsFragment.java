package com.university.education.fragment;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.university.education.R;
import com.university.education.base.BaseFragment;
import com.university.education.base.BaseNewsBannerPager;

import java.util.ArrayList;

/**
 * Created by jian on 2016/12/25.
 */

public class NewsFragment extends BaseFragment implements View.OnClickListener {
    private Activity mActivity;
    private View rootView;
    private ImageView base_activity_back;
    private TextView base_name;
    private ImageView base_activity_pic;
    private LinearLayout base_activity_title;
    private ViewPager viewpager;
    private LinearLayout school_news;
    private LinearLayout teah_stydy;
    private LinearLayout Student_activity;
    private LinearLayout university_culture;
    private LinearLayout ligong_fengcai;
    private LinearLayout dang_zheng;

    @Override
    public View initView(Activity activity) {
        mActivity = activity;
        View inflate = LayoutInflater.from(activity).inflate(R.layout.fragment_news, null);
        this.rootView = inflate;
        this.base_activity_back = (ImageView) rootView.findViewById(R.id.base_activity_back);
        this.base_name = (TextView) rootView.findViewById(R.id.base_name);
        this.base_activity_pic = (ImageView) rootView.findViewById(R.id.base_activity_pic);
        this.base_activity_title = (LinearLayout) rootView.findViewById(R.id.base_activity_title);
        this.viewpager = (ViewPager) rootView.findViewById(R.id.viewpager);
        this.school_news = (LinearLayout) rootView.findViewById(R.id.school_news);
        this.teah_stydy = (LinearLayout) rootView.findViewById(R.id.teah_stydy);
        this.Student_activity = (LinearLayout) rootView.findViewById(R.id.Student_activity);
        this.university_culture = (LinearLayout) rootView.findViewById(R.id.university_culture);
        this.ligong_fengcai = (LinearLayout) rootView.findViewById(R.id.ligong_fengcai);
        this.dang_zheng = (LinearLayout) rootView.findViewById(R.id.dang_zheng);
        return inflate;
    }

    @Override
    public void initDate() {
        ArrayList<BaseNewsBannerPager> baseNewsBannerPagers = new ArrayList<>();

    }

    @Override
    public void initListener() {
        school_news.setOnClickListener(this);
        teah_stydy.setOnClickListener(this);
        Student_activity.setOnClickListener(this);
        university_culture.setOnClickListener(this);
        ligong_fengcai.setOnClickListener(this);
        dang_zheng.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }


}
