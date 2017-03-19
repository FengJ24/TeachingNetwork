package com.university.education.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.university.education.R;
import com.university.education.bean.NewsFragmenBean;

import java.util.List;

/**
 * Created by jian on 2017/3/7.
 */
public class NewFragmentActivityAdapter extends RecyclerView.Adapter<NewFragmentActivityAdapter.MyViewHolder> {
    private List<NewsFragmenBean> mList;
    private Context mContext;

    public NewFragmentActivityAdapter(List<NewsFragmenBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_newfragment_activity, null);
        MyViewHolder myViewHolder = new MyViewHolder(inflate);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.data.setText(mList.get(position).getUrl());
        holder.title.setText(mList.get(position).getDesc());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView title;
        public TextView data;

        public MyViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.title = (TextView) rootView.findViewById(R.id.title);
            this.data = (TextView) rootView.findViewById(R.id.data);
        }

    }
}



