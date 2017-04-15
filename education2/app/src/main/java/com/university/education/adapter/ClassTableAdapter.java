package com.university.education.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.university.education.R;
import com.university.education.bean.SubjectBean;
import com.university.education.bean.WeekBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jian on 2017/1/20.
 */

public class ClassTableAdapter extends BaseAdapter {
    private List<WeekBean> beanArrayList = new ArrayList<>();
    private List<SubjectBean> subjectBeenList = new ArrayList<>();
    private List<String> subjectItemList = new ArrayList<>();
    final int TYPE_1 = 0;
    final int TYPE_2 = 1;
    final int TYPE_3 = 2;
    private Context mContext;

    public ClassTableAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return 48;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int viewTypeCount = getItemViewType(position);
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            switch (viewTypeCount){
                case TYPE_1:
//                    LayoutInflater.from(mContext).inflate(R.layout.layout_week_info,parent,false);
                    convertView = View.inflate(mContext, R.layout.layout_week_info,null);
                    viewHolder.title = (TextView) convertView.findViewById(R.id.week);
                    break;
                case TYPE_2:
//                    LayoutInflater.from(mContext).inflate(R.layout.layout_item_info,parent,false);
                    convertView = View.inflate(mContext, R.layout.layout_item_info,null);
                    viewHolder.item = (TextView) convertView.findViewById(R.id.item);
                    break;
                case TYPE_3:
//                    LayoutInflater.from(mContext).inflate(R.layout.layout_subject_info,parent,false);
                    convertView = View.inflate(mContext, R.layout.layout_subject_info,null);
                    viewHolder.subject = (TextView) convertView.findViewById(R.id.subject);
                    break;
            }
            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        switch (viewTypeCount){
            case TYPE_1:
                viewHolder.title.setText(beanArrayList.get(position).getText());
                break;
            case TYPE_2:
                viewHolder.item.setText(subjectItemList.get((position/8)-1));
                break;
            case TYPE_3:
                if (position<16){
                    viewHolder.subject.setText(getCourseName(subjectBeenList.get(position-9).getText()));
                }else if (position<24){
                    viewHolder.subject.setText(getCourseName(subjectBeenList.get(position-10).getText()));
                }else if (position<32){
                    viewHolder.subject.setText(getCourseName(subjectBeenList.get(position-11).getText()));
                }else if (position<40){
                    viewHolder.subject.setText(getCourseName(subjectBeenList.get(position-12).getText()));
                }else{
                    viewHolder.subject.setText(getCourseName(subjectBeenList.get(position-13).getText()));
                }

                break;
        }
        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < 8 || position == 0) {
            return TYPE_1;
        } else if (position != 0 && position % 8 == 0) {
            return TYPE_2;
        } else {
            return TYPE_3;
        }
    }

    public void setWeekBeanList(List<WeekBean> beanArrayList) {
        this.beanArrayList = beanArrayList;
    }

    public void setSubjectBeanList(List<SubjectBean> subjectBeenList) {
        this.subjectBeenList = subjectBeenList;
    }

    public void setStringList(List<String> subjectItemList) {
        this.subjectItemList = subjectItemList;
    }

    private String getCourseName(String courseInfo) {
        if (courseInfo != null | !courseInfo.equals("")) {
            String name = courseInfo;
            String[] split = name.split(" ");
            return split[0];
        } else {
            return "";
        }
    }

    public static class ViewHolder {
        public TextView item;
        public TextView subject;
        public TextView title;
    }
}
