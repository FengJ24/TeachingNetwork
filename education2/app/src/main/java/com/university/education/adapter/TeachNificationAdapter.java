package com.university.education.adapter;

import android.view.View;
import android.widget.TextView;

import com.university.education.R;
import com.university.education.base.MyBasicAdapter;
import com.university.education.bean.TeachNotificationBean;

import java.util.List;

/**
 * Created by jian on 2017/2/14.
 */
public class TeachNificationAdapter extends MyBasicAdapter<TeachNotificationBean> {
    public TeachNificationAdapter(List<TeachNotificationBean> content) {
        super(content);
    }

    @Override
    public Object initViewHolderAndFindViewById(int position, View convertView) {
        return new ViewHolder(convertView);
    }

    @Override
    public void showData(int position, Object holder, List<TeachNotificationBean> adapterList) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.content.setText(adapterList.get(position).getTitle());
        viewHolder.date.setText(adapterList.get(position).getDate());
    }

    @Override
    public int getLayoutId(int position) {
        return R.layout.item_teachnitification;
    }

    public static class ViewHolder {
        public View rootView;
        public TextView content;
        public TextView date;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.content = (TextView) rootView.findViewById(R.id.content);
            this.date = (TextView) rootView.findViewById(R.id.date);
        }

    }
}
