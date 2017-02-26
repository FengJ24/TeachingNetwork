package com.university.education.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jian on 2016/10/8.
 */

public abstract class BaseFragment extends Fragment {


    public Activity activity;
    private boolean firstShow = true;
    private static final String TAG = "BaseFragment";


    @Override
    // 创建Fragment的时候
    public void onCreate(Bundle savedInstanceState) {
        this.activity = getActivity();
        super.onCreate(savedInstanceState);
    }

    @Override
    // 每次创建,绘制该Fragmentd的View的组件时
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = initView(activity);

        return view;
    }

    @Override
    // 当Fragment在Activity被启动之后会回调
    public void onActivityCreated(Bundle savedInstanceState) {
        initDate();
        initListener();
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    /**
     * 设置该Fragment的视图View,在基类onCreate方法
     *
     * @return
     */
    public abstract View initView(Activity activity);

    /**
     * onCreateView时候会执行联网请求数据
     *
     * @return
     */
    public abstract void initDate();

    /**
     * onCreateView时候会执行联网请求数据
     *
     * @return
     */
    public abstract void initListener();

}
