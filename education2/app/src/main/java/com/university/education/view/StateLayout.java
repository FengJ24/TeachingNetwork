package com.university.education.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.university.education.R;


/**
 * Created by jian on 2016/10/9.
 */

public class StateLayout extends FrameLayout {

    private LinearLayout mLondingView;
    private TextView mFailView;
    private View mContentView;
    private LinearLayout mEmptyView;
    private ProgressBar start_loading;
    private AnimationDrawable mAnimation;

    public StateLayout(Context context) {
        this(context, null);
    }

    public StateLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public StateLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 显示正常界面
     */
    public void showContentView() {
        showView(mContentView);
    }

    /**
     * 显示正在加载的View
     */
    public void showLoadingView() {
        showView(mLondingView);
    }

    /**
     * 显示加载失败的View
     */
    public void showFailView() {
        showView(mFailView);
    }

    /**
     * 显示为空的View
     */
    public void showEmptyView() {
        showView(mEmptyView);
    }

    @Override
    /**布局加载完成*/
    protected void onFinishInflate() {
        mLondingView = (LinearLayout) findViewById(R.id.state_linlearlayout);
        mFailView = (TextView) findViewById(R.id.statelayout_tv_fail);
        mEmptyView = (LinearLayout) findViewById(R.id.statelayout_empty);
        start_loading = (ProgressBar) findViewById(R.id.start_loading);

//        mAnimation = (AnimationDrawable) start_loading.getBackground();
//        // 为了防止在onCreate方法中只显示第一帧的解决方案之一
//        start_loading.post(new Runnable() {
//            @Override
//            public void run() {
//                mAnimation.start();
//            }
//        });
        showLoadingView();
    }


    /**
     * 显示指定的View，隐藏其它的View
     *
     * @param view 指定要显示的View
     */
    private void showView(View view) {
        // 遍历所有的View
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i); // 取出一个子孩子
            child.setVisibility(view == child ? View.VISIBLE : View.GONE);
            // 显示指定的View，隐藏其它的View
        }
    }

    /**
     * 添加内容布局
     */
    public void setContentView(Object layoutIdOrView) {
        if (layoutIdOrView == null) {
            throw new IllegalArgumentException(
                    "layoutIdOrView参数不能为null，可以是一个布局id，也可以是一个View对象");
        }
        if (layoutIdOrView instanceof View) { // 如果layoutIdOrView是一个View
            mContentView = (View) layoutIdOrView;
        } else {
            int layoutId = (Integer) layoutIdOrView;
            mContentView = View.inflate(getContext(), layoutId, null);
        }

        addView(mContentView); // 把正常界面的View添加到StateLayout中进行显示
        mContentView.setVisibility(View.GONE); // 默认显示的是loadingView

    }
}
