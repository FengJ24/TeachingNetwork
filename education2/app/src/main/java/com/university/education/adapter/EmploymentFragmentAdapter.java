package com.university.education.adapter;

import android.view.View;
import android.widget.TextView;

import com.university.education.R;
import com.university.education.base.MyBasicAdapter;
import com.university.education.bean.EmploymentFragmenBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jian on 2017/2/14.
 */
public class EmploymentFragmentAdapter extends MyBasicAdapter<EmploymentFragmenBean> {
    public EmploymentFragmentAdapter(ArrayList<EmploymentFragmenBean> content) {
        super(content);
    }

    @Override
    public Object initViewHolderAndFindViewById(int position, View convertView) {
        return new ViewHolder(convertView);
    }

    @Override
    public void showData(int position, Object holder, List<EmploymentFragmenBean> adapterList) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.content.setText(adapterList.get(position).getDesc());
    }

    @Override
    public int getLayoutId(int position) {
        return R.layout.item_emloyment;
    }

    public static class ViewHolder {
        public View rootView;
        public TextView content;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.content = (TextView) rootView.findViewById(R.id.content);
        }

    }
}
