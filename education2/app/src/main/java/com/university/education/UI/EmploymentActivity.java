package com.university.education.UI;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.university.education.R;
import com.university.education.adapter.EmploymentAdapter;
import com.university.education.base.BaseActivity;
import com.university.education.bean.NewsFragmenBean;
import com.university.education.httpEngine.NewsModule;
import com.university.education.utils.PreferenceUtils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jian on 2017/4/22.
 */

public class EmploymentActivity extends BaseActivity {
    private XRecyclerView mXRecyclerView;
    private List<NewsFragmenBean> mNewsFragmenArticleList = new ArrayList<>();
    private List<NewsFragmenBean> totalArrayList = new ArrayList<>();
    private String mUrl;
    private String mName;
    private NewsModule mNewsModule;
    private boolean isLoadMore;
    private EmploymentAdapter mEmploymentAdapter;
    private int index = 1;

    @Override
    public void initListener() {
        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                index= 1;
                isLoadMore = false;
                connectNet(followNameGetUrl(mName),1);
            }

            @Override
            public void onLoadMore() {
                index ++;
                isLoadMore = true;
                connectNet(followNameGetUrl(mName),index);

            }
        });

    }

    @Override
    public void initData(TextView base_name, ImageView base_activity_pic, ImageView base_activity_back) {
        Intent intent = getIntent();
        mName = intent.getStringExtra("name");
        base_name.setText("招聘就业-" + mName);
        base_activity_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mXRecyclerView.setLayoutManager(layoutManager);
        mNewsModule = new NewsModule(this);
        connectNet(followNameGetUrl(mName),1);

    }

    /**
     * 根据name获取相应的url
     *
     * @param name
     */
    private String followNameGetUrl(String name) {
        switch (name) {
            case "校内公告":
                mUrl = "http://zsjy.sylu.edu.cn/plus/list.php?tid=30&TotalResult=882&PageNo=";
                break;
            case "专场招聘":
                mUrl = "http://zsjy.sylu.edu.cn/plus/list.php?tid=29&TotalResult=2454&PageNo=";
                break;
            case "需求信息":
                mUrl = "http://zsjy.sylu.edu.cn/plus/list.php?tid=9&TotalResult=9627&PageNo=";
                break;
            case "就业指导":
                mUrl = "http://zsjy.sylu.edu.cn/plus/list.php?tid=10&TotalResult=321&PageNo=";
                break;
            case "政策法规":
                mUrl = "http://zsjy.sylu.edu.cn/plus/list.php?tid=12&TotalResult=182&PageNo=";
                break;

        }

        return mUrl;

    }

    private void connectNet(String url,int index) {
        mNewsFragmenArticleList.clear();
        mNewsModule.getPerfectArticle(url+index, new NewsModule.NewsResponseListener() {
            @Override
            public void success(Document document) {
                showContentView();
                Elements main_news_post = document.select("div.main-news-post");
                for (int i = 0; i <main_news_post.size() ; i++) {
                    String url = main_news_post.get(i).select("div.main-posted-by").first().text();
                    String desc = main_news_post.get(i).select("h4").first().text();
                    Node node = main_news_post.get(i).childNode(1);
                    String href = node.childNode(0).attributes().get("href");
                    NewsFragmenBean newsFragmenBean = new NewsFragmenBean(url, href, desc);
                    mNewsFragmenArticleList.add(newsFragmenBean);
                }
                setListData(mNewsFragmenArticleList);
            }
        });
    }

    private void setListData(List<NewsFragmenBean> mNewsFragmenArticleList) {
        if (isLoadMore){
            if (mNewsFragmenArticleList.size()!= 0){
                totalArrayList.addAll(mNewsFragmenArticleList);
                if (mEmploymentAdapter!= null){
                    mEmploymentAdapter.setData(totalArrayList);
                    mEmploymentAdapter.notifyDataSetChanged();
                }else{
                    mEmploymentAdapter = new EmploymentAdapter(this,mName);
                    mEmploymentAdapter.setData(totalArrayList);
                    mXRecyclerView.setAdapter(mEmploymentAdapter);
                }
            }else{
                Toast.makeText(this, "没有更多数据了", Toast.LENGTH_SHORT).show();
            }
            mXRecyclerView.loadMoreComplete();
        }else{
            totalArrayList.clear();
            if (mNewsFragmenArticleList.size()!= 0){
                totalArrayList.addAll(mNewsFragmenArticleList);
                if (mEmploymentAdapter!= null){
                    mEmploymentAdapter.setData(totalArrayList);
                    mEmploymentAdapter.notifyDataSetChanged();
                }else{
                    mEmploymentAdapter = new EmploymentAdapter(this,mName);
                    mEmploymentAdapter.setData(totalArrayList);
                    mXRecyclerView.setAdapter(mEmploymentAdapter);
                }
            }else{
                Toast.makeText(this, "加载为空", Toast.LENGTH_SHORT).show();
            }
            mXRecyclerView.refreshComplete();
        }
    }

    @Override
    public Object getContentView() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.activity_employment, null);
        mXRecyclerView = (XRecyclerView) inflate.findViewById(R.id.recycleview);

        return inflate;
    }
}
