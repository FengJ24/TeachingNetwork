package com.university.education.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.university.education.R;

/**
 * Created by jian on 2016/12/28.
 */

public class TeachPlanActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teach_plan);
        ((TextView) findViewById(R.id.base_name)).setText("教学计划");

    }
}
