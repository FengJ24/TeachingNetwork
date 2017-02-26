package com.university.education.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class MyBasicAdapter<T> extends BaseAdapter {

    public List<T> adapterList;

    public MyBasicAdapter(List<T> list) {
        this.adapterList = list;
    }

    public List<T> getList() {
        return adapterList;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return adapterList.size();
    }

    @Override
    public T getItem(int position) {
        // TODO Auto-generated method stub
        return adapterList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Object holder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(),
                    getLayoutId(position), null);
            holder = initViewHolderAndFindViewById(position, convertView);

            convertView.setTag(holder);
        } else {
            holder = convertView.getTag();
        }

        // 展示数据
        showData(position, holder, adapterList);

        return convertView;
    }

    /**
     * 初始化ViewHolder和初始化控件
     */
    public abstract Object initViewHolderAndFindViewById(int position,
                                                         View convertView);

    /**
     * 展示数据
     */
    public abstract void showData(int position, Object holder, List<T> adapterList);

    /**
     * 获得布局的ID
     */
    public abstract int getLayoutId(int position);

}
