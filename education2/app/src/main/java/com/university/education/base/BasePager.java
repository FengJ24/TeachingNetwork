package com.university.education.base;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.university.education.R;

public abstract class BasePager {
    public Activity mActivity;
    public View view;
    private Dialog loadingDialog;
    private final FrameLayout mFrameLayout;

    public BasePager(Activity activity) {
        this.mActivity = activity;
        view = View.inflate(activity, R.layout.layout_base_pager, null);
        mFrameLayout = (FrameLayout) view.findViewById(R.id.container);
        mFrameLayout.addView(setContentView(getDetaiilView(mActivity)));
    }

    //1.共同的头抽取出来
    //2.xml--->view方法
    protected abstract Object getDetaiilView(Activity activity);

    //3.给view中的控件填充数据的方法
    public abstract void initData();

    //4.当界面不显示的时候需要移除的监听器
    public abstract void destoryMessage();

    //7.根据网络链接情况的不同显示的不同布局
    public void hideLoadingView() {
        if (loadingDialog != null) {
            if (loadingDialog.isShowing()) {
                loadingDialog.dismiss();
            }
        }
    }

    public Dialog createLoadingDialog(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.loading, null);// 得到加载view
        // 创建自定义样式dialog
        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);
        loadingDialog.setContentView(v);// 设置布局
        /**
         * 设置Dialog的宽度为屏幕宽度的61.8%，高度为自适应
         */
        WindowManager.LayoutParams lp = loadingDialog.getWindow().getAttributes();
        lp.width = lp.WRAP_CONTENT;
        lp.height = lp.WRAP_CONTENT;
        loadingDialog.getWindow().setAttributes(lp);
        return loadingDialog;

    }

    @NonNull
    public void showLoadingView() {
        if (loadingDialog == null) {
            loadingDialog = createLoadingDialog(mActivity);
        }
        loadingDialog.show();
    }

    /**
     * 添加内容布局
     */
    public View setContentView(Object layoutIdOrView) {
        if (layoutIdOrView == null) {
            throw new IllegalArgumentException(
                    "layoutIdOrView参数不能为null，可以是一个布局id，也可以是一个View对象");
        }
        if (layoutIdOrView instanceof View) { // 如果layoutIdOrView是一个View
            return (View) layoutIdOrView;
        } else {
            int layoutId = (Integer) layoutIdOrView;
            View inflate = LayoutInflater.from(mActivity).inflate(layoutId, mFrameLayout, false);
            return inflate;
        }

    }

}

