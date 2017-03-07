package com.university.education.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.university.education.R;
import com.university.education.UI.NewsBanner.FirstBanner;
import com.university.education.UI.NewsFragment.LigongFengcaiActivity;
import com.university.education.UI.NewsFragment.LigongWenyuanActivity;
import com.university.education.UI.NewsFragment.SchoolNewsActivity;
import com.university.education.UI.NewsFragment.StudentActivityActivity;
import com.university.education.UI.NewsFragment.TeachStudyActivity;
import com.university.education.UI.NewsFragment.UniversityCultureActivity;
import com.university.education.UI.WebviewActivity;
import com.university.education.adapter.PerfectArticleAdapter;
import com.university.education.base.BaseFragment;
import com.university.education.base.BaseNewsBannerPager;
import com.university.education.bean.NewsFragmenBean;
import com.university.education.httpEngine.NewsModule;
import com.university.education.view.ListViewForScrollView;

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
    private ArrayList<NewsFragmenBean> mNewsFragmenArticleList;
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
    private ListViewForScrollView listview;

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
        this.listview = (ListViewForScrollView) rootView.findViewById(R.id.listview);
        base_activity_back.setVisibility(View.INVISIBLE);
        base_name.setText("理工要闻");
        return inflate;
    }

    @Override
    public void initDate() {
        mNewsFragmenBeenList = new ArrayList<>();
        mNewsFragmenArticleList = new ArrayList<>();
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
        NewsModule newsModule = new NewsModule(activity);
        newsModule.getSchoolNewsData(new NewsModule.NewsResponseListener() {
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
        newsModule.getPerfectArticle("http://www.sylu.edu.cn/sylusite/slgyw/", new NewsModule.NewsResponseListener() {
            @Override
            public void success(Document document) {
                Element carousel = document.select("div#innerlist").first();
                Element ul = carousel.select("ul").first();
                Elements li = ul.select("li");
                for (int i = 0; i < li.size(); i++) {
                    Node node = li.get(i).childNode(0);
                    String href = node.attributes().get("href");
                    String title = node.attributes().get("title");
                    String span = li.get(i).select("span").first().text();
                    NewsFragmenBean newsFragmenBean = new NewsFragmenBean(span, href, title);
                    mNewsFragmenArticleList.add(newsFragmenBean);
                }
                setListData();
            }
        });
    }

    /**
     * 设置ListView的数据
     */
    private void setListData() {
        final PerfectArticleAdapter perfectArticleAdapter = new PerfectArticleAdapter(activity, mNewsFragmenArticleList);
        listview.setAdapter(perfectArticleAdapter);
        setListViewHeightBasedOnChildren(listview);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < mNewsFragmenArticleList.size(); i++) {
                    mNewsFragmenArticleList.get(i).setTouch(false);
                }
                mNewsFragmenArticleList.get(position).setTouch(true);
                perfectArticleAdapter.notifyDataSetChanged();
                Intent intent = new Intent(activity, WebviewActivity.class);
                intent.putExtra("url", "http://www.sylu.edu.cn" + mNewsFragmenArticleList.get(position).getHref());
                activity.startActivity(intent);
            }
        });
    }

    /**
     * 设置banner类
     */
    private void setBannerData() {
        ArrayList<BaseNewsBannerPager> baseNewsBannerPagers = new ArrayList<>();
        for (int i = 0; i < mNewsFragmenBeenList.size(); i++) {
            baseNewsBannerPagers.add(new FirstBanner(activity, mNewsFragmenBeenList.get(i).getUrl(), mNewsFragmenBeenList.get(i).getDesc(), mNewsFragmenBeenList.get(i).getHref()));
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
        switch (v.getId()) {
            case R.id.school_news:
                activity.startActivity(new Intent(activity, SchoolNewsActivity.class));
                break;
            case R.id.teah_stydy:
                activity.startActivity(new Intent(activity, TeachStudyActivity.class));
                break;
            case R.id.Student_activity:
                activity.startActivity(new Intent(activity, StudentActivityActivity.class));
                break;
            case R.id.university_culture:
                activity.startActivity(new Intent(activity, UniversityCultureActivity.class));
                break;
            case R.id.ligong_fengcai:
                activity.startActivity(new Intent(activity, LigongFengcaiActivity.class));
                break;
            case R.id.dang_zheng:
                activity.startActivity(new Intent(activity, LigongWenyuanActivity.class));
                break;
        }
    }

    public int setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return 0;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
        return params.height;
    }

}
