package com.university.education.fragment;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.university.education.R;
import com.university.education.UI.EmploymentActivity;
import com.university.education.UI.WebviewActivity;
import com.university.education.adapter.EmploymentFragmentAdapter;
import com.university.education.base.BaseFragment;
import com.university.education.bean.EmploymentFragmenBean;
import com.university.education.httpEngine.EmploymentModule;
import com.university.education.view.ListViewForScrollView;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by jian on 2016/12/25.
 */

public class SchoolSceneFragment extends BaseFragment implements View.OnClickListener{
    private Activity mActivity;
    private View mInflate;
    private View rootView;
    private ImageView base_activity_back;
    private TextView base_name;
    private ImageView base_activity_pic;
    private LinearLayout base_activity_title;
    private FrameLayout school_notice;
    private ListViewForScrollView listview_school_notice;
    private ListViewForScrollView listview_particular_employ;
    private ListViewForScrollView listview_need_message;
    private ListViewForScrollView listview_career_guidice;
    private ListViewForScrollView listview_polices_regulation;
    private FrameLayout particular_employ;
    private FrameLayout need_message;
    private FrameLayout career_guidice;
    private FrameLayout polices_regulation;
    private EmploymentModule mEmploymentModule;
    private ArrayList<EmploymentFragmenBean> mSchoolNoticeList;
    private ArrayList<EmploymentFragmenBean> mParticularEmployList;
    private ArrayList<EmploymentFragmenBean> mNeedMessageeList;
    private ArrayList<EmploymentFragmenBean> mCareerGuidiceList;
    private ArrayList<EmploymentFragmenBean> mPolicesRegulationList;

    @Override
    public View initView(Activity activity) {
        mActivity = activity;
        mInflate = LayoutInflater.from(activity).inflate(R.layout.fragment_employment, null);
        this.rootView = mInflate;
        this.base_activity_back = (ImageView) rootView.findViewById(R.id.base_activity_backed);
        this.base_name = (TextView) rootView.findViewById(R.id.base_name);
        this.base_activity_pic = (ImageView) rootView.findViewById(R.id.base_activity_pic);
        this.base_activity_title = (LinearLayout) rootView.findViewById(R.id.base_activity_title);
        this.listview_school_notice = (ListViewForScrollView) rootView.findViewById(R.id.listview_school_notice);
        this.school_notice = (FrameLayout) rootView.findViewById(R.id.school_notice);
        this.listview_particular_employ = (ListViewForScrollView) rootView.findViewById(R.id.listview_particular_employ);
        this.particular_employ = (FrameLayout) rootView.findViewById(R.id.particular_employ);
        this.listview_need_message = (ListViewForScrollView) rootView.findViewById(R.id.listview_need_message);
        this.need_message = (FrameLayout) rootView.findViewById(R.id.need_message);
        this.listview_career_guidice = (ListViewForScrollView) rootView.findViewById(R.id.listview_career_guidice);
        this.career_guidice = (FrameLayout) rootView.findViewById(R.id.career_guidice);
        this.listview_polices_regulation = (ListViewForScrollView) rootView.findViewById(R.id.listview_polices_regulation);
        this.polices_regulation = (FrameLayout) rootView.findViewById(R.id.polices_regulation);
        return mInflate;

    }

    @Override
    public void initDate() {
        setView();
        connetNet();
    }

    /**
     * 联网请求数据
     */
    private void connetNet() {
        mEmploymentModule.getEmployData(new EmploymentModule.EmploymentResponseListener() {
            @Override
            public void success(Document document) {
                if (switchShowView(document)) {
                    setSchoolNotice(document);
                    setParticularEmploy(document);
                    setNeedMessagee(document);
                    setCareerGuidice(document);
                    setPolicesRegulation(document);
                }
            }
        });
    }

    /**
     * 校内公告
     *
     * @param document
     */
    private void setSchoolNotice(Document document) {
        Element carousel = document.select("div.indexhot_2").first();
        Elements li = carousel.select("li");
        for (int i = 0; i < li.size(); i++) {
            String desc = li.get(i).text();
            Node node = li.get(i).childNode(0);
            String href = node.attributes().get("href");
            EmploymentFragmenBean employmentFragmenBean = new EmploymentFragmenBean(href, desc);
            mSchoolNoticeList.add(employmentFragmenBean);
        }
        EmploymentFragmentAdapter employmentFragmentAdapter = new EmploymentFragmentAdapter(mSchoolNoticeList);
        listview_school_notice.setAdapter(employmentFragmentAdapter);
        setListViewHeightBasedOnChildren(listview_school_notice);

    }

    /**
     * 专场招聘
     *
     * @param document
     */
    private void setParticularEmploy(Document document) {
        Element carousel = document.select("ul.content-main-top-list2").first();
        Elements li = carousel.select("li");
        for (int i = 0; i < li.size(); i++) {
            String desc = li.get(i).text();
            Node node = li.get(i).childNode(0);
            String href = node.attributes().get("href");
            EmploymentFragmenBean employmentFragmenBean = new EmploymentFragmenBean(href, desc);
            mParticularEmployList.add(employmentFragmenBean);
        }
        EmploymentFragmentAdapter employmentFragmentAdapter = new EmploymentFragmentAdapter(mParticularEmployList);
        listview_particular_employ.setAdapter(employmentFragmentAdapter);
        setListViewHeightBasedOnChildren(listview_particular_employ);
    }

    /**
     * 需求信息
     *
     * @param document
     */
    private void setNeedMessagee(Document document) {
        Element carousel = document.select("ul.content-main-top-list2").get(1);
        Elements li = carousel.select("li");
        for (int i = 0; i < li.size(); i++) {
            String desc = li.get(i).text();
            Node node = li.get(i).childNode(0);
            String href = node.attributes().get("href");
            EmploymentFragmenBean employmentFragmenBean = new EmploymentFragmenBean(href, desc);
            mNeedMessageeList.add(employmentFragmenBean);
        }
        EmploymentFragmentAdapter employmentFragmentAdapter = new EmploymentFragmentAdapter(mNeedMessageeList);
        listview_need_message.setAdapter(employmentFragmentAdapter);
        setListViewHeightBasedOnChildren(listview_need_message);
    }

    /**
     * 就业指导
     *
     * @param document
     */
    private void setCareerGuidice(Document document) {
        Element carousel = document.select("ul.content-main-top-list2").get(2);
        Elements li = carousel.select("li");
        for (int i = 0; i < li.size(); i++) {
            String desc = li.get(i).text();
            Node node = li.get(i).childNode(0);
            String href = node.attributes().get("href");
            EmploymentFragmenBean employmentFragmenBean = new EmploymentFragmenBean(href, desc);
            mCareerGuidiceList.add(employmentFragmenBean);
        }
        EmploymentFragmentAdapter employmentFragmentAdapter = new EmploymentFragmentAdapter(mCareerGuidiceList);
        listview_career_guidice.setAdapter(employmentFragmentAdapter);
        setListViewHeightBasedOnChildren(listview_career_guidice);
    }

    /**
     * 政策法规
     *
     * @param document
     */
    private void setPolicesRegulation(Document document) {
        Element carousel = document.select("ul.content-main-top-list2").get(3);
        Elements li = carousel.select("li");
        for (int i = 0; i < li.size(); i++) {
            String desc = li.get(i).text();
            Node node = li.get(i).childNode(0);
            String href = node.attributes().get("href");
            EmploymentFragmenBean employmentFragmenBean = new EmploymentFragmenBean(href, desc);
            mPolicesRegulationList.add(employmentFragmenBean);
        }
        EmploymentFragmentAdapter employmentFragmentAdapter = new EmploymentFragmentAdapter(mPolicesRegulationList);
        listview_polices_regulation.setAdapter(employmentFragmentAdapter);
        setListViewHeightBasedOnChildren(listview_polices_regulation);

    }

    private void setView() {
        base_activity_back.setVisibility(View.INVISIBLE);
        base_name.setText("就业招聘");
        mEmploymentModule = new EmploymentModule(mActivity);
        mSchoolNoticeList = new ArrayList<>();
        mParticularEmployList = new ArrayList<>();
        mNeedMessageeList = new ArrayList<>();
        mCareerGuidiceList = new ArrayList<>();
        mPolicesRegulationList = new ArrayList<>();


    }

    @Override
    public void initListener() {
        school_notice.setOnClickListener(this);
        particular_employ.setOnClickListener(this);
        need_message.setOnClickListener(this);
        career_guidice.setOnClickListener(this);
        polices_regulation.setOnClickListener(this);
        listview_career_guidice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(activity, WebviewActivity.class);
                intent.putExtra("url", "http://zsjy.sylu.edu.cn" + mCareerGuidiceList.get(position).getHref());
                intent.putExtra("name", mCareerGuidiceList.get(position).getDesc());

                activity.startActivity(intent);
            }
        });
        listview_school_notice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(activity, WebviewActivity.class);
                intent.putExtra("url", "http://zsjy.sylu.edu.cn" + mSchoolNoticeList.get(position).getHref());
                intent.putExtra("name", mCareerGuidiceList.get(position).getDesc());
                activity.startActivity(intent);
            }
        });
        listview_particular_employ.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(activity, WebviewActivity.class);
                intent.putExtra("name", mCareerGuidiceList.get(position).getDesc());
                intent.putExtra("url", "http://zsjy.sylu.edu.cn" + mParticularEmployList.get(position).getHref());
                activity.startActivity(intent);
            }
        });
        listview_need_message.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(activity, WebviewActivity.class);
                intent.putExtra("name", mCareerGuidiceList.get(position).getDesc());
                intent.putExtra("url", "http://zsjy.sylu.edu.cn" + mNeedMessageeList.get(position).getHref());
                activity.startActivity(intent);
            }
        });
        listview_polices_regulation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(activity, WebviewActivity.class);
                intent.putExtra("name", mCareerGuidiceList.get(position).getDesc());
                intent.putExtra("url", "http://zsjy.sylu.edu.cn" + mPolicesRegulationList.get(position).getHref());
                activity.startActivity(intent);
            }
        });
    }
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(activity, EmploymentActivity.class);
        switch (v.getId()) {
            case R.id.school_notice:
                //校内公告
                intent.putExtra("name","校内公告");
                break;
            case R.id.particular_employ:
                //专场招聘
                intent.putExtra("name","专场招聘");
                break;
            case R.id.need_message:
                //需求信息
                intent.putExtra("name","需求信息");
                break;
            case R.id.career_guidice:
                //就业指导
                intent.putExtra("name","就业指导");
                break;
            case R.id.polices_regulation:
                //政策法规
                intent.putExtra("name","政策法规");
                break;

        }
        activity.startActivity(intent);

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
