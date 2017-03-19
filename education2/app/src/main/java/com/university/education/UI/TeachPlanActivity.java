package com.university.education.UI;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.university.education.R;
import com.university.education.base.BaseActivity;

/**
 * Created by jian on 2016/12/28.
 */

public class TeachPlanActivity extends BaseActivity {

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {

    }

    @Override
    public Object getContentView() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.activity_teach_plan, null);
        ((TextView) inflate.findViewById(R.id.base_name)).setText("教学计划");
        return inflate;
    }
}
