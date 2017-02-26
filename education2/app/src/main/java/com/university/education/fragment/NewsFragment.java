package com.university.education.fragment;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.university.education.base.BaseFragment;

/**
 * Created by jian on 2016/12/25.
 */

public class NewsFragment extends BaseFragment {
    private Activity mActivity;

    @Override
    public View initView(Activity activity) {
        mActivity = activity;
        TextView textView = new TextView(mActivity);
        textView.setText("理工要闻");
        return textView;
    }

    @Override
    public void initDate() {

    }

    @Override
    public void initListener() {

    }
}
