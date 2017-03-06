package com.university.education.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.university.education.R;
import com.university.education.bean.NewsFragmenBean;

import java.util.ArrayList;

public class PerfectArticleAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context mContext;
    private ArrayList<NewsFragmenBean> mNewsFragmenArticleList;

    public PerfectArticleAdapter(Context context, ArrayList<NewsFragmenBean> mNewsFragmenArticleList) {
        mContext = context;
        this.mNewsFragmenArticleList = mNewsFragmenArticleList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mNewsFragmenArticleList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_children, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.second_textview.setText(mNewsFragmenArticleList.get(position).getDesc());
        viewHolder.catlog_time_all.setText(mNewsFragmenArticleList.get(position).getUrl());
        if (position == 0) {
            viewHolder.iv_line_top.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            viewHolder.iv_line_dow.setBackgroundColor(mContext.getResources().getColor(R.color.bg_color1));
        } else if (position == mNewsFragmenArticleList.size() - 1) {
            viewHolder.iv_line_top.setBackgroundColor(mContext.getResources().getColor(R.color.bg_color1));
            viewHolder.iv_line_dow.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        } else {
            viewHolder.iv_line_top.setBackgroundColor(mContext.getResources().getColor(R.color.bg_color1));
            viewHolder.iv_line_dow.setBackgroundColor(mContext.getResources().getColor(R.color.bg_color1));
        }
        if (mNewsFragmenArticleList.get(position).isTouch()) {
            viewHolder.second_textview.setTextColor(mContext.getResources().getColor(R.color.subject_title_color));
            viewHolder.catlog_time_all.setTextColor(mContext.getResources().getColor(R.color.subject_title_color));
        } else {
            viewHolder.second_textview.setTextColor(mContext.getResources().getColor(R.color.tongyi_textcolor));
            viewHolder.catlog_time_all.setTextColor(mContext.getResources().getColor(R.color.tongyi_textcolor));
        }
        return convertView;
    }


    public static class ViewHolder {
        public View rootView;
        public LinearLayout catlog_time_imageVie;
        public ImageView iv_line_top;
        public ImageView circle_imageview;
        public ImageView iv_line_dow;
        public LinearLayout div_line;
        public TextView second_textview;
        public TextView catlog_time_all;
        public LinearLayout catlog_item_layout;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.catlog_time_imageVie = (LinearLayout) rootView.findViewById(R.id.catlog_time_imageVie);
            this.iv_line_top = (ImageView) rootView.findViewById(R.id.iv_line_top);
            this.circle_imageview = (ImageView) rootView.findViewById(R.id.circle_imageview);
            this.iv_line_dow = (ImageView) rootView.findViewById(R.id.iv_line_dow);
            this.div_line = (LinearLayout) rootView.findViewById(R.id.div_line);
            this.second_textview = (TextView) rootView.findViewById(R.id.second_textview);
            this.catlog_time_all = (TextView) rootView.findViewById(R.id.catlog_time_all);
            this.catlog_item_layout = (LinearLayout) rootView.findViewById(R.id.catlog_item_layout);
        }

    }

    //把毫秒转换成时间格式
    private String calculatTime(int milliSecondTime) {

        int hour = milliSecondTime / (60 * 60 * 1000);
        int minute = (milliSecondTime - hour * 60 * 60 * 1000) / (60 * 1000);
        int seconds = (milliSecondTime - hour * 60 * 60 * 1000 - minute * 60 * 1000) / 1000;

        if (seconds >= 60) {
            seconds = seconds % 60;
            minute += seconds / 60;
        }
        if (minute >= 60) {
            minute = minute % 60;
            hour += minute / 60;
        }
        String sh = "";
        String sm = "";
        String ss = "";
        if (hour < 10) {
            sh = "0" + String.valueOf(hour);
        } else {
            sh = String.valueOf(hour);
        }
        if (minute < 10) {
            sm = "0" + String.valueOf(minute);
        } else {
            sm = String.valueOf(minute);
        }
        if (seconds < 10) {
            ss = "0" + String.valueOf(seconds);
        } else {
            ss = String.valueOf(seconds);
        }
        if (hour >= 10) {
            return sm + ":" + ss + ":";
        } else {
            return sh + ":" + sm + ":" + ss;
        }

    }
}
