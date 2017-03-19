package com.university.education.base;


import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;

import com.university.education.R;
import com.university.education.view.StateLayout;

import java.util.List;

public abstract class BasePager {
    private final StateLayout mStateLayout;
    public Activity mActivity;
    public View view;
    private Dialog loadingDialog;

    public BasePager(Activity activity) {
        this.mActivity = activity;
        mStateLayout = (StateLayout) View.inflate(activity, R.layout.layout_state, null);
        mStateLayout.setContentView(getDetaiilView(activity));
        view = mStateLayout;
    }

    //1.共同的头抽取出来
    //2.xml--->view方法
    protected abstract Object getDetaiilView(Activity activity);

    //3.给view中的控件填充数据的方法
    public abstract void initData();

    //4.当界面不显示的时候需要移除的监听器
    public abstract void destoryMessage();

    //7.根据网络链接情况的不同显示的不同布局

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
            View inflate = LayoutInflater.from(mActivity).inflate(layoutId, null);
            return inflate;
        }

    }
    public boolean switchShowView(Object list) {
        boolean isShow = false;
        if (list instanceof List<?>) {
            List<?> list1 = (List<?>) list;
//			if (list1 == null) {
//				stateLayout.showFailView();
//			} else if (list1.isEmpty()) {
            mStateLayout.showContentView();
//			} else {
//				TLog.log("showContentView");
//				stateLayout.showContentView();
            isShow = true;
//			}
        } else {
            if (list == null) {
                mStateLayout.showFailView();
            } else {
                mStateLayout.showContentView();
                isShow = true;
            }
        }

        return isShow;
    }
}

