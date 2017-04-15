package com.university.education.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.university.education.R;
import com.university.education.bean.TeachPlanBean;

import java.util.List;

/**
 * Created by jian on 2017/2/14.
 */
public class TeachPlanRecycleAdapter extends RecyclerView.Adapter<TeachPlanRecycleAdapter.ViewHolder> {
    private List<TeachPlanBean> mContent;
    private Context mContext;

    public TeachPlanRecycleAdapter(List<TeachPlanBean> content, Context context) {
        mContent = content;
        mContext = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_teach_plan, parent, false);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
       holder.class_name.setText("课程名称:"+mContent.get(position).getClassName());
       holder.class_xingzhi.setText("课程性质:"+mContent.get(position).getClassXindZhi());
       holder.class_xuefen.setText("学分:"+mContent.get(position).getClassXueFen());
       holder.qishizhou.setText("起始结束周:"+mContent.get(position).getClassStartEnd());
       holder.isxuewei.setText("是否学位课:"+mContent.get(position).getIsXuewei());
    }

    @Override
    public int getItemCount() {
        return mContent.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View rootView;
        public TextView class_name;
        public TextView class_xuefen;
        public TextView class_xingzhi;
        public TextView qishizhou;
        public TextView isxuewei;

        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.class_name = (TextView) rootView.findViewById(R.id.class_name);
            this.class_xuefen = (TextView) rootView.findViewById(R.id.class_xuefen);
            this.class_xingzhi = (TextView) rootView.findViewById(R.id.class_xingzhi);
            this.qishizhou = (TextView) rootView.findViewById(R.id.qishizhou);
            this.isxuewei = (TextView) rootView.findViewById(R.id.isxuewei);
        }

    }
}
