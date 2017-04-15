package com.university.education.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.university.education.R;
import com.university.education.view.StateLayout;

/**
 * Created by jian on 2017/3/5.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    private StateLayout mContainer;
    private View mContentView;
    private ImageView base_activity_back;
    private TextView base_name;
    private ImageView base_activity_pic;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.activity_layout_base);
        initView();
        initListener();
        initData(base_name,base_activity_pic,base_activity_back);
    }

    /**
     * 初始化数据
     */
    private void initView() {
        mContainer = (StateLayout) findViewById(R.id.statelayout);
        mContainer.setContentView(getContentView());
        base_activity_back = (ImageView) findViewById(R.id.base_activity_backed);
        base_activity_back.setOnClickListener(this);
        base_name = (TextView) findViewById(R.id.base_name);
        base_name.setOnClickListener(this);
        base_activity_pic = (ImageView) findViewById(R.id.base_activity_pic);
        base_activity_pic.setOnClickListener(this);
    }

    public abstract void initListener();

    public abstract void initData(TextView base_name,ImageView base_activity_pic,ImageView base_activity_back);

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
        mContentView.setVisibility(View.INVISIBLE);
        return mContentView;
    }

    /**
     * 显示内容
     */
    public void showContentView() {
        mContainer.showContentView();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.base_activity_backed:
                this.finish();
                break;
        }
    }
}
