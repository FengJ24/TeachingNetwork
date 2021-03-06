package com.university.education.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.university.education.R;
import com.university.education.UI.WebviewActivity;
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
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.data.setText(mList.get(position).getUrl());
        holder.title.setText(mList.get(position).getDesc());
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WebviewActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("url", "http://www.sylu.edu.cn" + mList.get(position).getHref());
                intent.putExtra("name", mList.get(position).getDesc());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView title;
        public TextView data;
        public LinearLayout item;

        public MyViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.title = (TextView) rootView.findViewById(R.id.title);
            this.data = (TextView) rootView.findViewById(R.id.data);
            this.item = (LinearLayout) rootView.findViewById(R.id.item);
        }

    }
}



