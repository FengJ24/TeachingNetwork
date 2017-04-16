package com.university.education.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.university.education.R;
import com.university.education.bean.StudentGradeBean;

import java.util.List;

/**
 * Created by jian on 2017/2/14.
 */
public class GradeRecycleAdapter extends RecyclerView.Adapter<GradeRecycleAdapter.ViewHolder> {
    private List<StudentGradeBean> mContent;
    private Context mContext;

    public GradeRecycleAdapter(List<StudentGradeBean> content, Context context) {
        mContent = content;
        mContext = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_grade, parent, false);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        StudentGradeBean studentGradeBean = mContent.get(position);
        viewHolder.chengji.setText("期末成绩:" + studentGradeBean.getChengji());
        viewHolder.jidian.setText("绩点:" + studentGradeBean.getJidian());
        viewHolder.subject.setText("课程:" + studentGradeBean.getSubject());
        viewHolder.subjectType.setText("课程类型:" + studentGradeBean.getSubjectType());
        viewHolder.xuefen.setText("学分:" + studentGradeBean.getXueFen());
    }

    @Override
    public int getItemCount() {
        return mContent.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView subject;
        public TextView subjectType;
        public TextView xuefen;
        public TextView jidian;
        public TextView chengji;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.subject = (TextView) rootView.findViewById(R.id.subject);
            this.subjectType = (TextView) rootView.findViewById(R.id.subjectType);
            this.xuefen = (TextView) rootView.findViewById(R.id.xuefen);
            this.jidian = (TextView) rootView.findViewById(R.id.jidian);
            this.chengji = (TextView) rootView.findViewById(R.id.chengji);
        }

    }
}
