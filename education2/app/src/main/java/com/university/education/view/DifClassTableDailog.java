package com.university.education.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.university.education.R;
import com.university.education.adapter.ChooseTermAdapter;
import com.university.education.adapter.ChooseYearAdapter;
import com.university.education.bean.ClassTableChooseBean;
import com.university.education.bean.EnentBusTableBean;
import com.university.education.constants.Constants;

import java.util.List;

/**
 * Created by jian on 2017/3/25.
 */

public class DifClassTableDailog extends Dialog {
    private GridViewForScrollView grade_gridview;
    private GridViewForScrollView subject_gridview;
    private LinearLayout subjuct_item_lal;
    private LinearLayout choose_grade_subject;
    private Handler mHandler;
    private Context mContext;
    private List<ClassTableChooseBean> mXuenian;
    private List<ClassTableChooseBean> mXueqi;
    private TextView confirm;
    private String xuenianName;
    private String xueqiName;

    public DifClassTableDailog(Handler handler, Context context, List<ClassTableChooseBean> xuenian, List<ClassTableChooseBean> xueqi) {
        super(context, R.style.MainQuickOptionDailog);
        mHandler = handler;
        mContext = context;
        mXuenian = xuenian;
        mXueqi = xueqi;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_table_lesson);
        this.grade_gridview = (GridViewForScrollView) findViewById(R.id.grade_gridview);
        this.subject_gridview = (GridViewForScrollView) findViewById(R.id.subject_gridview);
        this.subjuct_item_lal = (LinearLayout) findViewById(R.id.subjuct_item_lal);
        this.choose_grade_subject = (LinearLayout) findViewById(R.id.choose_grade_subject);
        this.confirm = (TextView) findViewById(R.id.confirm);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        layoutParams.width = (int) (displayMetrics.widthPixels * 0.6f);
        layoutParams.height = (int) (displayMetrics.widthPixels * 0.8f);
        layoutParams.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(layoutParams);
        setGridView();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnentBusTableBean enentBusTableBean = new EnentBusTableBean(xuenianName, xueqiName);
                Message obtain = Message.obtain();
                obtain.what = Constants.CLASS_TABLE_DIF;
                obtain.obj = enentBusTableBean;
                mHandler.sendMessage(obtain);
                if (isShowing()) {
                    dismiss();
                }
            }
        });
    }

    /**
     * 设置GridView的数据
     */
    private void setGridView() {
        ChooseYearAdapter chooseYearAdapter = new ChooseYearAdapter(mXuenian, DifClassTableDailog.this, mContext);
        ChooseTermAdapter chooseTermAdapter = new ChooseTermAdapter(mXueqi, DifClassTableDailog.this, mContext);
        grade_gridview.setNumColumns(2);
        subject_gridview.setNumColumns(2);
        grade_gridview.setAdapter(chooseYearAdapter);
        subject_gridview.setAdapter(chooseTermAdapter);
        for (int i = 0; i < mXuenian.size(); i++) {
            if (mXuenian.get(i).isCheck()) {
                xuenianName = mXuenian.get(i).getName();
            }
        }
        for (int i = 0; i < mXueqi.size(); i++) {
            if (mXueqi.get(i).isCheck()) {
                xueqiName = mXueqi.get(i).getName();
            }
        }

    }

    /**
     * 学期数据发生变化
     *
     * @param name
     */
    public void callnotifyDataSetChanged(String name) {
        this.xuenianName = name;
    }

    /**
     * 学期数据发生变化
     *
     * @param name
     */
    public void callSubjectDataChanged(String name) {
        this.xueqiName = name;
    }
}
