package com.university.education.base;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.university.education.R;

/**
 * Created by jian on 2017/3/5.
 * 理工要闻 Banner页基类
 */

public abstract class BaseNewsBannerPager {
    public View view;
    private Activity activity;
    private FrameLayout mFrameLayout;

    public BaseNewsBannerPager(Activity activity) {
        activity = activity;
        view = View.inflate(activity, R.layout.layout_base_pager, null);
        mFrameLayout = (FrameLayout) view.findViewById(R.id.container);
        mFrameLayout.addView(setContentView(getDetaiilView(activity)));
    }

    //1.共同的头抽取出来
    protected abstract Object getDetaiilView(Activity activity);

    //3.给view中的控件填充数据的方法
    public abstract void initData();


    /**
     * 添加内容布局
     */
    public View setContentView(Object layoutIdOrView) {
        if (layoutIdOrView == null) {
            throw new IllegalArgumentException(
                    "layoutIdOrView参数不能为null，可以是一个布局id，也可以是一个View对象");
        }
        if (layoutIdOrView instanceof View) { // 如果layoutIdOrView是一个View
            return (View) layoutIdOrView;
        } else {
            int layoutId = (Integer) layoutIdOrView;
            View inflate = LayoutInflater.from(activity).inflate(layoutId, mFrameLayout, false);
            return inflate;
        }

    }
}
