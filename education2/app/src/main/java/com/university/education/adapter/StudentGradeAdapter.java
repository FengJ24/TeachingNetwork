package com.university.education.adapter;

import android.view.View;
import android.widget.TextView;

import com.university.education.R;
import com.university.education.base.MyBasicAdapter;
import com.university.education.bean.StudentGradeBean;

import java.util.List;

/**
 * Created by jian on 2017/1/7.
 */
public class StudentGradeAdapter extends MyBasicAdapter<StudentGradeBean> {
    public StudentGradeAdapter(List<StudentGradeBean> list) {
        super(list);
    }

    @Override
    public Object initViewHolderAndFindViewById(int position, View convertView) {
        ViewHolder viewHolder = new ViewHolder(convertView);
        return viewHolder;
    }

    @Override
    public void showData(int position, Object holder, List<StudentGradeBean> adapterList) {
        ViewHolder viewHolder = (ViewHolder) holder;
        StudentGradeBean studentGradeBean = adapterList.get(position);
        viewHolder.chengji.setText("期末成绩:" + studentGradeBean.getChengji());
        viewHolder.jidian.setText("绩点:" + studentGradeBean.getJidian());
        viewHolder.subject.setText("课程:" + studentGradeBean.getSubject());
        viewHolder.subjectType.setText("课程类型:" + studentGradeBean.getSubjectType());
        viewHolder.xuenian.setText("学年:" + studentGradeBean.getXuenian());
        viewHolder.xuefen.setText("学分:" + studentGradeBean.getXueFen());
        viewHolder.xueqi.setText("第" + studentGradeBean.getXueqi() + "学期");

    }

    @Override
    public int getLayoutId(int position) {
        return R.layout.item_grade;
    }

    public static class ViewHolder {
        public View rootView;
        public TextView xuenian;
        public TextView xueqi;
        public TextView subject;
        public TextView subjectType;
        public TextView xuefen;
        public TextView jidian;
        public TextView chengji;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.xuenian = (TextView) rootView.findViewById(R.id.xuenian);
            this.xueqi = (TextView) rootView.findViewById(R.id.xueqi);
            this.subject = (TextView) rootView.findViewById(R.id.subject);
            this.subjectType = (TextView) rootView.findViewById(R.id.subjectType);
            this.xuefen = (TextView) rootView.findViewById(R.id.xuefen);
            this.jidian = (TextView) rootView.findViewById(R.id.jidian);
            this.chengji = (TextView) rootView.findViewById(R.id.chengji);
        }

    }
}
