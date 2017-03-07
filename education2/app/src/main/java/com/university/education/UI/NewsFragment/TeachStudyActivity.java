package com.university.education.UI.NewsFragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.university.education.R;
import com.university.education.base.BaseActivity;
import com.university.education.bean.NewsFragmenBean;
import com.university.education.httpEngine.NewsModule;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jian on 2017/3/7.
 */

public class TeachStudyActivity extends BaseActivity {
    private XRecyclerView mXRecyclerView;
    private List<NewsFragmenBean> mNewsFragmenArticleList;

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        mNewsFragmenArticleList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mXRecyclerView.setLayoutManager(layoutManager);
        NewsModule newsModule = new NewsModule(this);
        newsModule.getPerfectArticle("http://www.sylu.edu.cn/sylusite/jxky/", new NewsModule.NewsResponseListener() {
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

    private void setListData() {
        NewFragmentActivityAdapter newFragmentActivityAdapter = new NewFragmentActivityAdapter(mNewsFragmenArticleList, this);
        mXRecyclerView.setAdapter(newFragmentActivityAdapter);
        mXRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mXRecyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                mXRecyclerView.loadMoreComplete();
            }
        });
    }

    @Override
    public Object getContentView() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.activity_news_fragment, null);
        mXRecyclerView = (XRecyclerView) inflate.findViewById(R.id.recycleview);
        return inflate;
    }
}
