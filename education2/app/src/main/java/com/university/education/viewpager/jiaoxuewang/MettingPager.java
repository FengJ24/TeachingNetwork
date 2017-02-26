package com.university.education.viewpager.jiaoxuewang;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import com.university.education.R;
import com.university.education.UI.TeachNitificationActivity;
import com.university.education.adapter.TeachNificationAdapter;
import com.university.education.base.BasePager;
import com.university.education.bean.TeachNotificationBean;
import com.university.education.constants.Constants;
import com.university.education.httpEngine.EducationModule;
import com.university.education.view.RefreshLoadMoreListView;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jian on 2017/2/13.
 */

public class MettingPager extends BasePager {
    private Activity mActivity;
    private RefreshLoadMoreListView mRefreshLoadMoreListView;
    private int index = 1;
    private boolean isLoadMore = false;
    private TeachNificationAdapter mTeachNificationAdapter;
    private List<TeachNotificationBean> mNewContent;

    public MettingPager(Activity activity) {
        super(activity);
        mActivity = activity;
    }

    @Override
    protected Object getDetaiilView(Activity activity) {
        View inflate = LayoutInflater.from(activity).inflate(R.layout.pager_teachitifi, null);
        mRefreshLoadMoreListView = (RefreshLoadMoreListView) inflate.findViewById(R.id.listview);
        return inflate;
    }

    @Override
    public void initData() {
        final List<TeachNotificationBean> content = new ArrayList<>();
        mNewContent = new ArrayList<>();
        showLoadingView();
        getFirstData(content, mNewContent);
        mRefreshLoadMoreListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mActivity, TeachNitificationActivity.class);
                intent.putExtra(Constants.TEACH_NOTIFICATION_URL, content.get(position - 1).getUrl());
                mActivity.startActivity(intent);
            }
        });
        mRefreshLoadMoreListView.setOnRefreshListner(new RefreshLoadMoreListView.OnRefreshListner() {
            @Override
            public void onRefresh() {
                isLoadMore = false;
                index = 1;
                getFirstData(content, mNewContent);
            }
        });
        mRefreshLoadMoreListView.setOnLoadingMoreListener(new RefreshLoadMoreListView.OnLoadingMoreListener() {
            @Override
            public void loadingMore() {
                isLoadMore = true;
                index++;
                getPetPagerData(content, mNewContent);
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
                mRefreshLoadMoreListView.hideFootView();


            }

        },20, index);

    }

    /**
     * 请求首页数据
     *
     * @param content 第一页的数据
     */
    private void getFirstData(final List<TeachNotificationBean> content, final List<TeachNotificationBean> newContent) {

        new EducationModule(mActivity).getTeachNotifacationData(20,new EducationModule.EducationResponseListener() {
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
                newContent.clear();
                newContent.addAll(content);
                setDataForListView(newContent);
                hideLoadingView();
                mRefreshLoadMoreListView.onRefreshComplete();
            }
        });

    }

    /**
     * 设置ListView的数据
     *
     * @param adapterContent
     */
    private void setDataForListView(List<TeachNotificationBean> adapterContent) {
        List<TeachNotificationBean> newAdapterContent = adapterContent;
        if (isLoadMore){
            if (mTeachNificationAdapter!=null){
                mTeachNificationAdapter.notifyDataSetChanged();
            }
        }else{
            mTeachNificationAdapter = new TeachNificationAdapter(newAdapterContent);
            mRefreshLoadMoreListView.setAdapter(mTeachNificationAdapter);
            mRefreshLoadMoreListView.onRefreshComplete();
            mRefreshLoadMoreListView.hideFootView();
        }

    }

    @Override
    public void destoryMessage() {

    }
}
