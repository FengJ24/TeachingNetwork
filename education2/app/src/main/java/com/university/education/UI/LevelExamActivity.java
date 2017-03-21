package com.university.education.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.university.education.R;
import com.university.education.base.BaseActivity;

/**
 * Created by jian on 2016/12/28.
 */

public class LevelExamActivity extends BaseActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_exam);


    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData(TextView base_name, ImageView base_activity_pic, ImageView base_activity_back) {
        base_name.setText("等级考试");
    }


    @Override
    public Object getContentView() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.activity_level_exam, null);
        ((TextView) inflate.findViewById(R.id.base_name)).setText("等级考试");
        return inflate;
    }
}
