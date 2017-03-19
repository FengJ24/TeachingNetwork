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

public class TeachNotificationPager extends BasePager {
    private Activity mActivity;
    private RefreshLoadMoreListView mRefreshLoadMoreListView;
    private int index = 1;

    public TeachNotificationPager(Activity activity) {
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
        final List<TeachNotificationBean> newContent = new ArrayList<>();
        getFirstData(content, newContent);
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
                index = 1;
                getFirstData(content, newContent);
            }
        });
        mRefreshLoadMoreListView.setOnLoadingMoreListener(new RefreshLoadMoreListView.OnLoadingMoreListener() {
            @Override
            public void loadingMore() {
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
        }, 4, index);

    }

    /**
     * 请求首页数据
     *
     * @param content 第一页的数据
     */
    private void getFirstData(final List<TeachNotificationBean> content, final List<TeachNotificationBean> newContent) {

        new EducationModule(mActivity).getTeachNotifacationData(4, new EducationModule.EducationResponseListener() {

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
        TeachNificationAdapter teachNificationAdapter = new TeachNificationAdapter(content);
        mRefreshLoadMoreListView.setAdapter(teachNificationAdapter);
        mRefreshLoadMoreListView.onRefreshComplete();
        mRefreshLoadMoreListView.hideFootView();

    }

    @Override
    public void destoryMessage() {

    }

}
