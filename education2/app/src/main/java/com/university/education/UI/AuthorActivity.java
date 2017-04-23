package com.university.education.UI;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.university.education.R;
import com.university.education.base.BaseActivity;

/**
 * Created by jian on 2017/4/23.
 */

public class AuthorActivity extends BaseActivity {

    private TextView mZuopin;
    private TextView mAuthor;

    @Override
    public void initListener() {

    }

    @Override
    public void initData(TextView base_name, ImageView base_activity_pic, ImageView base_activity_back) {
        showContentView();
        base_activity_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        base_name.setText("关于作品/作者");
        mZuopin.setText("         应用的一切数据来自沈阳理工大学官网(http://www.sylu.edu.cn/sylusite/)，做这个应用的初衷是方便大家" +
                "及时查看校园网的信息，通过微信、QQ分享，传递信息。\n" +
                "         应用是采用Jsoup抓取网络数据，模拟网页登陆，利用okhttp+RxJava2进行网络请求。\n" +
                "         如果同学们对作品有建议的地方请加Q859224240进行反馈。");
        mAuthor.setText("         一名即将毕业的大四狗，爱篮球，爱编程的有志青年。");
    }

    @Override
    public Object getContentView() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.activity_author, null);
        mZuopin = (TextView) inflate.findViewById(R.id.zuopin);
        mAuthor = (TextView) inflate.findViewById(R.id.author_1);

        return inflate;
    }
}
