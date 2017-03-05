package com.university.education.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.university.education.R;

/**
 * Created by jian on 2017/3/5.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private FrameLayout mContainer;
    private View mContentView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.activity_base);
        initView();
        initListener();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initView() {
        mContainer = (FrameLayout) findViewById(R.id.base_activity_container);
        getView(getContentView());
    }

    public abstract void initListener();

    public abstract void initData();

    /**
     * 设置界面的布局
     */
    public abstract Object getContentView();

    /**
     * 处理返回的View
     */
    public View getView(Object layoutIdOrView) {
        if (layoutIdOrView == null) {
            throw new IllegalArgumentException(
                    "layoutIdOrView参数不能为null，可以是一个布局id，也可以是一个View对象");
        }
        if (layoutIdOrView instanceof View) { // 如果layoutIdOrView是一个View
            mContentView = (View) layoutIdOrView;
        } else {
            int layoutId = (Integer) layoutIdOrView;
            mContentView = View.inflate(this, layoutId, null);
        }
        mContainer.addView(mContentView);
        return mContentView;
    }


}
