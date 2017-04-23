package com.university.education.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.university.education.R;
import com.university.education.UI.TeachNitificationActivity;
import com.university.education.bean.TeachNotificationBean;
import com.university.education.constants.Constants;

import java.util.List;

/**
 * Created by jian on 2017/2/14.
 */
public class TeachNificationRecycleAdapter extends RecyclerView.Adapter<TeachNificationRecycleAdapter.ViewHolder> {
    private List<TeachNotificationBean> mContent;
    private Context mContext;
    private String mType;

    public TeachNificationRecycleAdapter(List<TeachNotificationBean> content, Context context, String type) {
        mContent = content;
        mContext = context;
        mType = type;
    }


    @Override
    public TeachNificationRecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_teachnitification, parent, false);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    public void setData(List<TeachNotificationBean> content) {
        mContent = content;
    }

    @Override
    public void onBindViewHolder(TeachNificationRecycleAdapter.ViewHolder holder, final int position) {
        holder.content.setText(mContent.get(position).getTitle());
        holder.date.setText(mContent.get(position).getDate());
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, TeachNitificationActivity.class);
                intent.putExtra(Constants.TEACH_NOTIFICATION_URL, mContent.get(position).getUrl());
                intent.putExtra("type", mType);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mContent.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView content;
        public TextView date;
        public FrameLayout item;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.content = (TextView) rootView.findViewById(R.id.content);
            this.date = (TextView) rootView.findViewById(R.id.date);
            this.item = (FrameLayout) rootView.findViewById(R.id.item);
        }

    }
}
