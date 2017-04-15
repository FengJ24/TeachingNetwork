package com.university.education.fragment;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.university.education.R;
import com.university.education.base.BaseFragment;
import com.university.education.base.BasePager;
import com.university.education.viewpager.jiaoxuewang.MettingPager;
import com.university.education.viewpager.jiaoxuewang.TeachMessagePager;
import com.university.education.viewpager.jiaoxuewang.TeachNotificationPager;
import com.university.education.viewpager.jiaoxuewang.TestMessagePager;

import java.util.ArrayList;

/**
 * Created by jian on 2016/12/25.
 */

public class TeachFragment extends BaseFragment {
    private Activity mActivity;
    private ImageView base_activity_back;
    private TextView base_name;
    private TabLayout fragment_teach_tablayout;
    private ViewPager viewpager;
    private ArrayList<BasePager> mBasePagers;
    private String[] mStrings;

    @Override
    public View initView(Activity activity) {
        mActivity = activity;
        View inflate = LayoutInflater.from(activity).inflate(R.layout.fragment_teach, null);
        base_name = (TextView) inflate.findViewById(R.id.base_name);
        base_activity_back = (ImageView) inflate.findViewById(R.id.base_activity_backed);
        fragment_teach_tablayout = (TabLayout) inflate.findViewById(R.id.fragment_teach_tablayout);
        viewpager = (ViewPager) inflate.findViewById(R.id.viewpager);
        base_activity_back.setVisibility(View.INVISIBLE);
        return inflate;
    }

    @Override
    public void initDate() {
        mStateLayout.showContentView();
        base_name.setText("教学网");
        mBasePagers = new ArrayList<>();
        mBasePagers.add(new TeachNotificationPager(activity));
        mBasePagers.add(new TestMessagePager(activity));
        mBasePagers.add(new TeachMessagePager(activity));
        mBasePagers.add(new MettingPager(activity));

        mStrings = new String[]{"教务通知", "考试信息", "教学信息", "报告会"};
        viewpager.setAdapter(new MyViewPagerAdaper(mBasePagers));
        fragment_teach_tablayout.setupWithViewPager(viewpager);
    }

    @Override
    public void initListener() {
        //ViewPager的监听
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mBasePagers.get(position).destoryMessage();
                mBasePagers.get(position).initData();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewpager.setCurrentItem(0);
        mBasePagers.get(0).initData();
    }

    private class MyViewPagerAdaper extends PagerAdapter {
        private ArrayList<BasePager> mBasePagers;

        public MyViewPagerAdaper(ArrayList<BasePager> basePagers) {
            mBasePagers = basePagers;
        }

        @Override
        public int getCount() {
            return mBasePagers.size() == 0 ? 0 : mBasePagers.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mBasePagers.get(position).view);
//            mBasePagers.get(position).initData();
            return mBasePagers.get(position).view;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {

            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mStrings[position];
        }
    }

}
