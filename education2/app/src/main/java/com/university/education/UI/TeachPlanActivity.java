package com.university.education.UI;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.university.education.R;
import com.university.education.adapter.TeachPlanChooseXueqiAdapter;
import com.university.education.adapter.TeachPlanRecycleAdapter;
import com.university.education.base.BaseActivity;
import com.university.education.bean.TeachPlanBean;
import com.university.education.bean.TeachPlanChooseXueQiBean;
import com.university.education.constants.Constants;
import com.university.education.httpEngine.MineModule;
import com.university.education.utils.PreferenceUtils;
import com.university.education.view.GridViewForScrollView;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jian on 2016/12/28.
 */

public class TeachPlanActivity extends BaseActivity {
    private String mName;
    private String mXuehao;
    private MineModule mMineModule;
    private boolean isFirstRequest = true;
    private String mClassName;
    private String mClassXuefen;
    private String mClassXingZhi;
    private String mClassStartEnd;
    private String mIsXuewei;
    private List<TeachPlanBean> mTeachPlanBeanArrayList;
    private String mView_state;
    private List<TeachPlanChooseXueQiBean> xueqiList;
    private boolean isShow;
    private ProgressBar progressbar;
    private TextView current_xueqi;
    private String mTranlateXueqi;

    @Override
    public void initListener() {

    }

    @Override
    public void initData(TextView base_name, ImageView base_activity_pic, ImageView base_activity_back) {
        base_name.setText("教学计划");
        base_activity_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mMineModule = new MineModule(this);
        xueqiList = new ArrayList<>();
        mName = PreferenceUtils.getString(this, Constants.NAME);
        mXuehao = PreferenceUtils.getString(this, Constants.XUEHAO);
        mTeachPlanBeanArrayList = new ArrayList<>();
//        requestDifTeachPlan(mName, mXuehao, PreferenceUtils.getString(this, Constants.TEACH_PLAN_VIEWSTATE));
        requestNet(mName, mXuehao);
    }

    private void requestNet(String name, String xuehao) {
        mMineModule.getTeachPlan(xuehao, name, new MineModule.MineResponseListener() {
            @Override
            public void success(Document document) {
                setTeachPlanData(document);
            }
        });
    }

    private void requestDifTeachPlan(String name, String xuehao, String viewState, String xueqi) {
        mMineModule.getTeachPlanDif(xuehao, name, viewState, xueqi, new MineModule.MineResponseListener() {
            @Override
            public void success(Document document) {
                setTeachPlanData(document);
            }
        });
    }

    private void setTeachPlanData(Document document) {
        showContentView();
        progressbar.setVisibility(View.GONE);
        lesson_detail.setVisibility(View.VISIBLE);
        Elements title = document.select("title");
        if (!"现代教学管理信息系统".equals(title.text())) {
            Toast.makeText(TeachPlanActivity.this, "登录过期,请重新登录", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(TeachPlanActivity.this, LoginActivity.class));
            finish();
            return;
        }
        if (isFirstRequest) {
            //获取ViewState
            Element first = document.select("[name=__VIEWSTATE]").first();
            mView_state = first.attributes().get("value");
            //获取学期
            Element select = document.select("select#xq").first();
            Elements select1 = select.select("option[value]");
            Element selected = select.select("option[selected]").first();
            mTranlateXueqi = selected.text();
            for (int i = 0; i < select1.size(); i++) {
                if (i==0){
                    continue;
                }
                String text = select1.get(i).text();
                xueqiList.add(new TeachPlanChooseXueQiBean("第" + text + "学期"));
            }
            isFirstRequest = !isFirstRequest;
        }
        //获取具体的课
        mTeachPlanBeanArrayList.clear();
        Element datelist = document.select("table.datelist").first();
        Elements tr = datelist.select("tr");
        for (int i = 0; i < tr.size(); i++) {
            Element element = tr.get(i);
            Elements td = element.select("td");
            if (i == 0) {
                continue;
            }
            if (i == tr.size() - 1) {
                continue;
            }
            for (int j = 0; j < td.size(); j++) {
                if (j == 1) {
                    //课程名字
                    mClassName = td.get(j).text();
                }
                if (j == 2) {
                    //课程学分
                    mClassXuefen = td.get(j).text();
                }
                if (j == 4) {
                    //课程性质
                    mClassXingZhi = td.get(j).text();
                }
                if (j == 14) {
                    //起始结束周
                    mClassStartEnd = td.get(j).text();
                }
                if (j == 16) {
                    //是否学位课
                    mIsXuewei = td.get(j).text();
                }
            }
            mTeachPlanBeanArrayList.add(new TeachPlanBean(mClassName, mClassXuefen, mClassXingZhi, mClassStartEnd, mIsXuewei));
            setListView();
        }
    }

    /**
     * 设置ListView
     */
    private void setListView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        lesson_detail.setLayoutManager(linearLayoutManager);
        TeachPlanRecycleAdapter teachPlanRecycleAdapter = new TeachPlanRecycleAdapter(mTeachPlanBeanArrayList, this);
        lesson_detail.setAdapter(teachPlanRecycleAdapter);
        current_xueqi.setText("第"+mTranlateXueqi+"学期");
        lesson_num.setText(mTeachPlanBeanArrayList.size()+"节课");

    }

    //点击学则学期
    private void chooseXueqi(List<TeachPlanChooseXueQiBean> xueqiList) {
        if (isShow){
            grade_gridview.setVisibility(View.GONE);
            lesson_detail.setVisibility(View.VISIBLE);
        }else{
            TeachPlanChooseXueqiAdapter teachPlanChooseXueqiAdapter = new TeachPlanChooseXueqiAdapter(xueqiList, this, this);
            grade_gridview.setAdapter(teachPlanChooseXueqiAdapter);
            grade_gridview.setNumColumns(3);
            grade_gridview.setVisibility(View.VISIBLE);
            lesson_detail.setVisibility(View.GONE);
        }
        isShow = !isShow;
    }

    @Override
    public Object getContentView() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.activity_teach_plan, null);
        this.rootView = inflate;
        this.choose_sems = (TextView) rootView.findViewById(R.id.choose_sems);
        this.lesson_num = (TextView) rootView.findViewById(R.id.lesson_num);
        this.current_xueqi = (TextView) rootView.findViewById(R.id.current_xueqi);
        this.grade_gridview = (GridViewForScrollView) rootView.findViewById(R.id.grade_gridview);
        this.progressbar = (ProgressBar) rootView.findViewById(R.id.progressbar);
        this.lesson_detail = (RecyclerView) rootView.findViewById(R.id.lesson_detail);
        choose_sems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseXueqi(xueqiList);
            }
        });
        return inflate;
    }

    private View rootView;
    private TextView choose_sems;
    private TextView lesson_num;
    private GridViewForScrollView grade_gridview;
    private RecyclerView lesson_detail;

    /**
     * 学期数据发生变化
     * @param xueqi
     */
    public void callnotifyDataSetChanged(String xueqi) {
        mTranlateXueqi = xueqi;
        grade_gridview.setVisibility(View.GONE);
        progressbar.setVisibility(View.VISIBLE);

        requestDifTeachPlan(mName, mXuehao, mView_state,xueqi);
    }
}
