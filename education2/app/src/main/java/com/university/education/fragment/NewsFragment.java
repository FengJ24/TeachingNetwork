package com.university.education.fragment;

import android.app.Activity;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.university.education.R;
import com.university.education.UI.NewsBanner.FirstBanner;
import com.university.education.base.BaseFragment;
import com.university.education.base.BaseNewsBannerPager;
import com.university.education.bean.NewsFragmenBean;
import com.university.education.httpEngine.NewsModule;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by jian on 2016/12/25.
 */

public class NewsFragment extends BaseFragment implements View.OnClickListener {
    private Activity mActivity;
    private View rootView;
    private ImageView base_activity_back;
    private TextView base_name;
    private ImageView base_activity_pic;
    private LinearLayout base_activity_title;
    private ViewPager viewpager;
    private LinearLayout school_news;
    private LinearLayout teah_stydy;
    private LinearLayout Student_activity;
    private LinearLayout university_culture;
    private LinearLayout ligong_fengcai;
    private LinearLayout dang_zheng;
    private ArrayList<NewsFragmenBean> mNewsFragmenBeenList;
    private final int SHOW_NEXT_PAGE = 1;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case SHOW_NEXT_PAGE:
                    showNextPage();
                    break;
            }
        }
    };
    private boolean isSend;

    @Override
    public View initView(Activity activity) {
        mActivity = activity;
        View inflate = LayoutInflater.from(activity).inflate(R.layout.fragment_news, null);
        this.rootView = inflate;
        this.base_activity_back = (ImageView) rootView.findViewById(R.id.base_activity_back);
        this.base_name = (TextView) rootView.findViewById(R.id.base_name);
        this.base_activity_pic = (ImageView) rootView.findViewById(R.id.base_activity_pic);
        this.base_activity_title = (LinearLayout) rootView.findViewById(R.id.base_activity_title);
        this.viewpager = (ViewPager) rootView.findViewById(R.id.viewpager);
        this.school_news = (LinearLayout) rootView.findViewById(R.id.school_news);
        this.teah_stydy = (LinearLayout) rootView.findViewById(R.id.teah_stydy);
        this.Student_activity = (LinearLayout) rootView.findViewById(R.id.Student_activity);
        this.university_culture = (LinearLayout) rootView.findViewById(R.id.university_culture);
        this.ligong_fengcai = (LinearLayout) rootView.findViewById(R.id.ligong_fengcai);
        this.dang_zheng = (LinearLayout) rootView.findViewById(R.id.dang_zheng);
        base_activity_back.setVisibility(View.INVISIBLE);
        base_name.setText("理工要闻");
        return inflate;
    }

    @Override
    public void initDate() {
        mNewsFragmenBeenList = new ArrayList<>();
        netWork();

    }

    protected void showNextPage() {
        int currentItem = viewpager.getCurrentItem();    // 获取ViewPager当前显示的位置
        viewpager.setCurrentItem(++currentItem);        // 显示下一页
        handler.sendEmptyMessageDelayed(SHOW_NEXT_PAGE, 3000);    // 3秒钟后再显示下一页
    }

    /**
     * 网络请求
     */
    private void netWork() {
        new NewsModule(activity).getSchoolNewsData(new NewsModule.NewsResponseListener() {
            @Override
            public void success(Document document) {
                Element carousel = document.select("div#carousel").first();
                Elements li = carousel.select("li");
                for (int i = 0; i < li.size(); i++) {
                    String desc = li.get(i).text();
                    Node node = li.get(i).childNode(0);
                    String href = node.attributes().get("href");
                    String src = node.childNode(0).attributes().get("src");
                    NewsFragmenBean newsFragmenBean = new NewsFragmenBean(src, href, desc);
                    mNewsFragmenBeenList.add(newsFragmenBean);
                }
                setBannerData();
            }
        });
    }

    /**
     * 设置banner类
     */
    private void setBannerData() {
        ArrayList<BaseNewsBannerPager> baseNewsBannerPagers = new ArrayList<>();
        for (int i = 0; i < mNewsFragmenBeenList.size(); i++) {
            baseNewsBannerPagers.add(new FirstBanner(activity, mNewsFragmenBeenList.get(i).getUrl(), mNewsFragmenBeenList.get(i).getDesc(),mNewsFragmenBeenList.get(i).getHref()));
        }
        setViewPager(baseNewsBannerPagers);
        baseNewsBannerPagers.get(0).initData();
        showNextPage();
    }

    /**
     * 设置ViewPager
     *
     * @param baseNewsBannerPagers
     */
    private void setViewPager(final ArrayList<BaseNewsBannerPager> baseNewsBannerPagers) {
        MyViewPagerAdaper myViewPagerAdaper = new MyViewPagerAdaper(baseNewsBannerPagers);
        viewpager.setAdapter(myViewPagerAdaper);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (ViewPager.SCROLL_STATE_DRAGGING == state) {
                    handler.removeMessages(SHOW_NEXT_PAGE);
                    isSend = false;
                } else {
                    if (!isSend) {
                        handler.sendEmptyMessageDelayed(SHOW_NEXT_PAGE, 3000);
                        isSend = true;
                    }
                }
            }
        });
    }

    @Override
    public void initListener() {
        school_news.setOnClickListener(this);
        teah_stydy.setOnClickListener(this);
        Student_activity.setOnClickListener(this);
        university_culture.setOnClickListener(this);
        ligong_fengcai.setOnClickListener(this);
        dang_zheng.setOnClickListener(this);
    }

    private class MyViewPagerAdaper extends PagerAdapter {
        private ArrayList<BaseNewsBannerPager> mPagerArrayList;

        public MyViewPagerAdaper(ArrayList<BaseNewsBannerPager> baseBannerPagers) {
            this.mPagerArrayList = baseBannerPagers;
        }

        @Override
        public int getCount() {
            return mPagerArrayList.size() == 0 ? 0 : mPagerArrayList.size() * 1000 * 1000;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position = position % mPagerArrayList.size();
            container.addView(mPagerArrayList.get(position).view);
            mPagerArrayList.get(position).initData();
            return mPagerArrayList.get(position).view;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }

    @Override
    public void onClick(View v) {

    }


}
