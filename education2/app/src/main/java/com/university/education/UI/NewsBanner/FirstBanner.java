package com.university.education.UI.NewsBanner;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.university.education.R;
import com.university.education.base.BaseNewsBannerPager;

/**
 * Created by jian on 2017/3/5.
 */

public class FirstBanner extends BaseNewsBannerPager {
    private String mUrl;
    private String mIconDesc;
    private ImageView banner_icon;
    private TextView icon_desc;

    public FirstBanner(Activity activity, String url, String iconDesc) {
        super(activity);
        mUrl = url;
        mIconDesc = iconDesc;
    }

    @Override
    protected Object getDetaiilView(Activity activity) {
        View inflate = LayoutInflater.from(activity).inflate(R.layout.layout_news_banner, null);
        banner_icon = (ImageView) inflate.findViewById(R.id.banner_icon);
        icon_desc = (TextView) inflate.findViewById(R.id.icon_desc);
        return inflate;
    }

    @Override
    public void initData() {
        icon_desc.getBackground().setAlpha(50);//0~255透明度值
        icon_desc.setText(mIconDesc);
        ImageLoader.getInstance().displayImage(mUrl, banner_icon);
    }
}
