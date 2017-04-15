package com.university.education.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.university.education.R;
import com.university.education.base.MyBasicAdapter;
import com.university.education.bean.ClassTableChooseBean;
import com.university.education.view.DifClassTableDailog;

import java.util.List;

/**
 * Created by Administrator on 2016/11/23.
 */

public class ChooseYearAdapter extends MyBasicAdapter<ClassTableChooseBean> {
    private final Drawable drawableItem2;
    private Drawable drawableItem;
    private DifClassTableDailog mClassTableDailog;
    private Context mContext;

    public ChooseYearAdapter(List<ClassTableChooseBean> list, DifClassTableDailog classTableDailog, Context context) {
        super(list);
        mClassTableDailog = classTableDailog;
        mContext = context;
        drawableItem = context.getResources().getDrawable(R.drawable.shape_fragment_discover_tv_bg);
        drawableItem.setBounds(0, 0, drawableItem.getMinimumWidth(), drawableItem.getMinimumHeight());
        drawableItem2 = context.getResources().getDrawable(R.drawable.shape_fragment_discover_tv_bg_select);
        drawableItem2.setBounds(0, 0, drawableItem2.getMinimumWidth(), drawableItem2.getMinimumHeight());
    }

    @Override
    public Object initViewHolderAndFindViewById(int position, View convertView) {
        ViewHolder viewHolder = new ViewHolder(convertView);
        return viewHolder;
    }

    @Override
    public void showData(final int position, Object holder, final List<ClassTableChooseBean> adapterList) {
        ViewHolder viewHolder = (ViewHolder) holder;
        if (adapterList.get(position).isCheck()) {
            viewHolder.tv_grade.setTextColor(Color.parseColor("#FFFFFF"));
            viewHolder.tv_grade.setBackground(drawableItem2);
        }else{
            viewHolder.tv_grade.setTextColor(Color.parseColor("#B4B4B4"));
            viewHolder.tv_grade.setBackground(drawableItem);
        }
        viewHolder.tv_grade.setText(adapterList.get(position).getName());

        viewHolder.tv_grade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterList.get(position).setCheck(true);
                for (int i = 0; i < adapterList.size(); i++) {
                    if (i != position) {
                        adapterList.get(i).setCheck(false);
                    }
                }
                mClassTableDailog.callnotifyDataSetChanged(adapterList.get(position).getName());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getLayoutId(int position) {
        return R.layout.layout_item_choose_gradet;
    }


    public static class ViewHolder {
        public View rootView;
        public TextView tv_grade;
        public LinearLayout item_choose_grade;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_grade = (TextView) rootView.findViewById(R.id.tv_grade);
            this.item_choose_grade = (LinearLayout) rootView.findViewById(R.id.item_choose_grade);
        }

    }
}
