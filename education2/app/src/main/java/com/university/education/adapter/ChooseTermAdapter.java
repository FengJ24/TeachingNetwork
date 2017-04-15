package com.university.education.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.university.education.R;
import com.university.education.bean.ClassTableChooseBean;
import com.university.education.view.DifClassTableDailog;

import java.util.List;


/**
 * Created by Administrator on 2016/11/23.
 */

public class ChooseTermAdapter extends BaseAdapter {
    private Drawable drawableItem2;
    private List<ClassTableChooseBean> list;
    private DifClassTableDailog mClassTableDailog;
    private Context mContext;
    private Drawable drawableItem;
    private ViewHolder viewHolder;
    private boolean isAllCheck = false;

    public ChooseTermAdapter(List<ClassTableChooseBean> list, DifClassTableDailog classTableDailog, Context context) {
        super();
        this.list = list;
        mClassTableDailog = classTableDailog;
        mContext = context;
        if (list.size() == 3) {
            list.remove(2);
        }
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.layout_item_choose_subject, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        //不是则显示,列表中的数据
        ClassTableChooseBean classTableChooseBean = list.get(position);
        if (classTableChooseBean.isCheck()) {
            viewHolder.tv_subject.setTextColor(Color.parseColor("#FFFFFF"));
            viewHolder.tv_subject.setBackgroundResource(R.drawable.shape_fragment_discover_tv_bg_select);
            //设置左边的图片
            viewHolder.tv_subject.setCompoundDrawables(null, null, null, null);
        } else {
            viewHolder.tv_subject.setTextColor(Color.parseColor("#B4B4B4"));
            viewHolder.tv_subject.setBackgroundResource(R.drawable.shape_fragment_discover_tv_bg);
        }
        viewHolder.tv_subject.setText(classTableChooseBean.getName());

        viewHolder.tv_subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < list.size(); i++) {
                    if (position == i) {
                        list.get(i).setCheck(true);
                    } else {
                        list.get(i).setCheck(false);
                    }
                }
                mClassTableDailog.callSubjectDataChanged(list.get(position).getName());
                notifyDataSetChanged();
            }
        });
        return view;
    }

    public static class ViewHolder {
        public View rootView;
        public TextView tv_subject;
        public LinearLayout item_choose_subject;
        public ImageView choose_subject_icon;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_subject = (TextView) rootView.findViewById(R.id.tv_subject);
            this.item_choose_subject = (LinearLayout) rootView.findViewById(R.id.item_choose_subject);
        }

    }
}
