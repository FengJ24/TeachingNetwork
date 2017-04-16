package com.university.education.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.university.education.R;
import com.university.education.adapter.GradeChooseTermAdapter;
import com.university.education.adapter.GradeChooseYearAdapter;
import com.university.education.bean.ClassTableChooseBean;
import com.university.education.bean.EnentBusTableBean;
import com.university.education.constants.Constants;

import java.util.List;


/**
 * Created by fengjian on 2017/3/1.
 *
 * @describe 展示发布结果的容器
 */

public class GradeChooseXuenianXueqiView extends FrameLayout {
    private final Handler mHandler;
    private final Context mContext;
    private final List<ClassTableChooseBean> mXuenian;
    private final List<ClassTableChooseBean> mXueqi;
    private ViewGroup rootView;
    private View contentView;
    public GridViewForScrollView grade_gridview;
    public GridViewForScrollView subject_gridview;
    public LinearLayout subjuct_item_lal;
    public TextView confirm;
    public LinearLayout choose_grade_subject;
    private String xuenianName;
    private String xueqiName;
    private View mView;
    private boolean isShowing;
    private onViewShowListener mOnViewShowListener;

    public GradeChooseXuenianXueqiView(Handler handler, Context context, List<ClassTableChooseBean> xuenian, List<ClassTableChooseBean> xueqi) {
        super(context);
        mHandler = handler;
        mContext = context;
        mXuenian = xuenian;
        mXueqi = xueqi;
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        contentView = LayoutInflater.from(mContext.getApplicationContext()).inflate(R.layout.layout_grade_choose, null);
        this.grade_gridview = (GridViewForScrollView) contentView.findViewById(R.id.grade_gridview);
        this.subject_gridview = (GridViewForScrollView) contentView.findViewById(R.id.subject_gridview);
        this.subjuct_item_lal = (LinearLayout) contentView.findViewById(R.id.subjuct_item_lal);
        this.confirm = (TextView) contentView.findViewById(R.id.confirm);
        this.choose_grade_subject = (LinearLayout) contentView.findViewById(R.id.choose_grade_subject);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnentBusTableBean enentBusTableBean = new EnentBusTableBean(xuenianName, xueqiName);
                Message obtain = Message.obtain();
                obtain.what = Constants.CLASS_TABLE_DIF;
                obtain.obj = enentBusTableBean;
                mHandler.sendMessage(obtain);
                dismiss();
            }
        });
        setGridView();
    }


    /**
     * 设置GridView的数据
     */
    private void setGridView() {
        GradeChooseYearAdapter chooseYearAdapter = new GradeChooseYearAdapter(mXuenian, GradeChooseXuenianXueqiView.this, mContext);
        GradeChooseTermAdapter chooseTermAdapter = new GradeChooseTermAdapter(mXueqi, GradeChooseXuenianXueqiView.this, mContext);
        grade_gridview.setNumColumns(3);
        subject_gridview.setNumColumns(3);
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
     * 显示试卷
     */
    public void show(View view) {
        mView = view;
        if (rootView == null) {
            rootView = findRootView(mView);
        }
        if (contentView.getParent() != null) {
            rootView.removeView(contentView);
        }
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        rootView.addView(contentView, layoutParams);
        invalidate();
        isShowing = true;
        if (mOnViewShowListener!=null){
            mOnViewShowListener.showed();
        }
    }

    /**
     * 隐藏试卷
     */
    public void dismiss() {
        if (rootView == null) {
            rootView = findRootView(mView);
        }
        rootView.removeView(contentView);
        isShowing = false;
        if (mOnViewShowListener!=null){
            mOnViewShowListener.dismissed();
        }
    }

    public boolean isShow() {
        return isShowing;
    }

    /**
     * 找到系统的跟布局将
     *
     * @param view 根布局中的某一个View
     */
    private ViewGroup findRootView(View view) {
        do {
            if (view instanceof FrameLayout) {
                if (view.getId() == R.id.grade_content) {
                    return (ViewGroup) view;
                }
            }
            if (view != null) {
                ViewParent parent = view.getParent();
                view = parent instanceof View ? (View) parent : null;
            }
        } while (view != null);
        return null;
    }


    public int setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return 0;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
//        listView.setLayoutParams(params);
        return height;
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

    public interface onViewShowListener{
        void showed();
        void dismissed();
    }
    public void setonViewShowListener(onViewShowListener onViewShowListener){
        mOnViewShowListener = onViewShowListener;
    }
}
