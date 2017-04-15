package com.university.education.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.university.education.R;

/**
 * Created by jian on 2017/3/25.
 * 课表详情对话框
 */

public class ClassLessonDetailDailog extends Dialog {
    private TextView lesson_name;
    private TextView time;
    private TextView teacher;
    private TextView location;
    private Context mContext;
    private String mDetail;

    public ClassLessonDetailDailog(Context context) {
        super(context, R.style.MainQuickOptionDailog);
        mContext = context;

    }

    public void setLessDetail(String detail) {
        if (!detail.equals(" ")||!detail.equals("")){
            if (!isShowing()){
                show();
                mDetail = detail;
                setCourseDetail(mDetail);
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_dif_class_table);
        this.lesson_name = (TextView) findViewById(R.id.lesson_name);
        this.time = (TextView) findViewById(R.id.time);
        this.teacher = (TextView) findViewById(R.id.teacher);
        this.location = (TextView) findViewById(R.id.location);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        layoutParams.width = (int) (displayMetrics.widthPixels * 0.6f);
        layoutParams.height = (int) (displayMetrics.widthPixels * 0.4f);
        layoutParams.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(layoutParams);



    }

    private void setCourseDetail(String courseInfo) {
        lesson_name.setVisibility(View.VISIBLE);
        time.setVisibility(View.VISIBLE);
        teacher.setVisibility(View.VISIBLE);
        location.setVisibility(View.VISIBLE);
        if (courseInfo != null || !courseInfo.equals("")) {
            String name = courseInfo;
            String[] split = name.split(" ");
            if (TextUtils.isEmpty(split[0])){
                dismiss();
            }
            if (split.length == 1) {
                lesson_name.setText(split[0]);
                time.setVisibility(View.GONE);
                teacher.setVisibility(View.GONE);
                location.setVisibility(View.GONE);
            } else if (split.length == 2) {
                lesson_name.setText(split[0]);
                time.setText(split[1]);
                teacher.setVisibility(View.GONE);
                location.setVisibility(View.GONE);
            } else if (split.length == 3) {
                lesson_name.setText(split[0]);
                time.setText(split[1]);
                teacher.setText(split[2]);
                location.setVisibility(View.GONE);
            } else if (split.length == 4) {
                lesson_name.setText(split[0]);
                time.setText(split[1]);
                teacher.setText(split[2]);
                location.setText(split[3]);
            }
        }
    }
}
