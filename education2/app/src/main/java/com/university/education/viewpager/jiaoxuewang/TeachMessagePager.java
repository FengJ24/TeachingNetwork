package com.university.education.viewpager.jiaoxuewang;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.university.education.R;
import com.university.education.adapter.TeachNificationRecycleAdapter;
import com.university.education.base.BasePager;
import com.university.education.bean.TeachNotificationBean;
import com.university.education.httpEngine.EducationModule;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import static com.university.education.R.id.listview;

/**
 * Created by jian on 2017/2/13.
 */

public class TeachMessagePager extends BasePager {
    private Activity mActivity;
    private XRecyclerView mRefreshLoadMoreListView;
    private int index = 1;
    private TeachNificationRecycleAdapter mTeachNificationRecycleAdapter;
    private boolean isLoadMore;

    public TeachMessagePager(Activity activity) {
        super(activity);
        mActivity = activity;
    }

    @Override
    protected Object getDetaiilView(Activity activity) {
        View inflate = LayoutInflater.from(activity).inflate(R.layout.pager_teachitifi, null);
        mRefreshLoadMoreListView = (XRecyclerView) inflate.findViewById(listview);
        return inflate;
    }

    @Override
    public void initData() {
        final List<TeachNotificationBean> content = new ArrayList<>();
        final List<TeachNotificationBean> newContent = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRefreshLoadMoreListView.setLayoutManager(linearLayoutManager);
        getFirstData(content, newContent);
        mRefreshLoadMoreListView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                isLoadMore = false;
                index = 1;
                getFirstData(content, newContent);
            }

            @Override
            public void onLoadMore() {
                isLoadMore = true;
                index++;
                getPetPagerData(content, newContent);
            }
        });

    }

    /**
     * 加载更多
     *
     * @param newContent 数据源
     * @param content
     */
    private void getPetPagerData(final List<TeachNotificationBean> newContent, final List<TeachNotificationBean> content) {
        EducationModule educationModule = new EducationModule(mActivity);

        educationModule.getTeachNotifacationLoadMoreData(new EducationModule.EducationResponseListener() {

            @Override
            public void onSuccess(Document document) {

                Elements listmain = document.select("p.atitle");
                content.clear();
                for (int i = 0; i < listmain.size(); i++) {
                    Element href = listmain.get(i).select("a[href]").first();
                    String url = href.attributes().get("href");
                    String title = href.text();
                    String date = listmain.get(i).select("i.idate").first().text();
                    TeachNotificationBean teachNotificationBean = new TeachNotificationBean(title, date, url);
                    content.add(teachNotificationBean);
                }
                newContent.addAll(content);
                setDataForListView(newContent);
            }
        }, 8, index);

    }

    /**
     * 请求首页数据
     *
     * @param content 第一页的数据
     */
    private void getFirstData(final List<TeachNotificationBean> content, final List<TeachNotificationBean> newContent) {

        new EducationModule(mActivity).getTeachNotifacationData(8, new EducationModule.EducationResponseListener() {
            @Override
            public void onSuccess(Document document) {
                if (switchShowView(document)) {
                    Elements listmain = document.select("p.atitle");
                    content.clear();
                    for (int i = 0; i < listmain.size(); i++) {
                        Element href = listmain.get(i).select("a[href]").first();
                        String url = href.attributes().get("href");
                        String title = href.text();
                        String date = listmain.get(i).select("i.idate").first().text();
                        TeachNotificationBean teachNotificationBean = new TeachNotificationBean(title, date, url);
                        content.add(teachNotificationBean);
                    }
                    newContent.clear();
                    newContent.addAll(content);
                    setDataForListView(newContent);
                }
            }
        });

    }

    /**
     * 设置ListView的数据
     *
     * @param content
     */
    private void setDataForListView(List<TeachNotificationBean> content) {
        List<TeachNotificationBean> newAdapterContent = content;
        if (isLoadMore) {
            if (mTeachNificationRecycleAdapter != null) {
                mTeachNificationRecycleAdapter.setData(content);
                mTeachNificationRecycleAdapter.notifyDataSetChanged();
            }else{
                mTeachNificationRecycleAdapter = new TeachNificationRecycleAdapter(newAdapterContent, mActivity,"教学信息");
                mRefreshLoadMoreListView.setAdapter(mTeachNificationRecycleAdapter);
            }
        } else {
            if (mTeachNificationRecycleAdapter != null) {
                mTeachNificationRecycleAdapter.setData(content);
                mTeachNificationRecycleAdapter.notifyDataSetChanged();
            }else{
                mTeachNificationRecycleAdapter = new TeachNificationRecycleAdapter(newAdapterContent, mActivity,"教学信息");
                mRefreshLoadMoreListView.setAdapter(mTeachNificationRecycleAdapter);
            }
        }
        mRefreshLoadMoreListView.refreshComplete();
        mRefreshLoadMoreListView.loadMoreComplete();

    }

    @Override
    public void destoryMessage() {

    }
}
