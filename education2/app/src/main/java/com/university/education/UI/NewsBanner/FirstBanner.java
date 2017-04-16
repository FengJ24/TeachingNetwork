package com.university.education.UI.NewsBanner;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.university.education.R;
import com.university.education.UI.WebviewActivity;
import com.university.education.base.BaseNewsBannerPager;

/**
 * Created by jian on 2017/3/5.
 */

public class FirstBanner extends BaseNewsBannerPager {
    private String mUrl;
    private String mIconDesc;
    private String mHref;
    private ImageView banner_icon;
    private TextView icon_desc;

    public FirstBanner(Activity activity, String url, String iconDesc,String href) {
        super(activity);
        mUrl = url;
        mIconDesc = iconDesc;
        mHref = href;
    }

    @Override
    protected Object getDetaiilView(final Activity activity) {
        View inflate = LayoutInflater.from(activity).inflate(R.layout.layout_news_banner, null);
        banner_icon = (ImageView) inflate.findViewById(R.id.banner_icon);
        icon_desc = (TextView) inflate.findViewById(R.id.icon_desc);
        inflate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, WebviewActivity.class);
                intent.putExtra("url", "http://www.sylu.edu.cn" + mHref);
                intent.putExtra("name", mIconDesc);
                activity.startActivity(intent);
            }
        });
        return inflate;
    }

    @Override
    public void initData() {
        icon_desc.getBackground().setAlpha(90);//0~255透明度值
        icon_desc.setText(mIconDesc);
        ImageLoader.getInstance().displayImage("http://www.sylu.edu.cn/sylusite/" + mUrl, banner_icon);
    }
}
