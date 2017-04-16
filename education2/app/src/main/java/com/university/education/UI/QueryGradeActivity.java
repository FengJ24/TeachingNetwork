package com.university.education.UI;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.university.education.R;
import com.university.education.adapter.GradeRecycleAdapter;
import com.university.education.base.BaseActivity;
import com.university.education.bean.ClassTableChooseBean;
import com.university.education.bean.EnentBusTableBean;
import com.university.education.bean.StudentGradeBean;
import com.university.education.constants.Constants;
import com.university.education.httpEngine.MineModule;
import com.university.education.utils.PreferenceUtils;
import com.university.education.view.GradeChooseXuenianXueqiView;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jian on 2016/12/28.
 */

public class QueryGradeActivity extends BaseActivity {

    private String mName;
    private String mXuehao;
    private RecyclerView mStudent_grade_list;
    private List<ClassTableChooseBean> xuenianList;
    private List<ClassTableChooseBean> xueqiList;
    private View rootView;
    private TextView choose_sems;
    private TextView current_xueqi;
    private TextView lesson_num;
    private ProgressBar progressbar;
    private FrameLayout grade_content;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constants.CLASS_TABLE_DIF:
                    mEnentBusTableBean = (EnentBusTableBean) msg.obj;
                    progressbar.setVisibility(View.VISIBLE);
                    getAllYearData(mEnentBusTableBean.getXuenian(), mEnentBusTableBean.getXueqi(), "学期成绩", mValue);
                    break;
            }
        }
    };
    private String mValue;
    private ArrayList<StudentGradeBean> mGradeBeanArrayList;
    private GradeRecycleAdapter mAdapter;
    private GradeChooseXuenianXueqiView mGradeChooseXuenianXueqiView;
    private EnentBusTableBean mEnentBusTableBean;

    @Override
    public void initListener() {

    }

    @Override
    public void initData(TextView base_name, ImageView base_activity_pic, ImageView base_activity_back) {
        base_name.setText("个人成绩");
        xuenianList = new ArrayList<>();
        xueqiList = new ArrayList<>();
        mGradeBeanArrayList = new ArrayList<>();
        base_activity_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        connectNetData();
    }

    @Override
    public Object getContentView() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.activity_query_grade, null);
        this.rootView = inflate;
        mStudent_grade_list = (RecyclerView) rootView.findViewById(R.id.student_grade_list);
        this.choose_sems = (TextView) rootView.findViewById(R.id.choose_sems);
        this.current_xueqi = (TextView) rootView.findViewById(R.id.current_xueqi);
        this.lesson_num = (TextView) rootView.findViewById(R.id.lesson_num);
        this.progressbar = (ProgressBar) rootView.findViewById(R.id.progressbar);
        this.grade_content = (FrameLayout) rootView.findViewById(R.id.grade_content);
        choose_sems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGradeChooseXuenianXueqiView!=null){
                    if (mGradeChooseXuenianXueqiView.isShow()){
                        mGradeChooseXuenianXueqiView.dismiss();
                        mStudent_grade_list.setVisibility(View.VISIBLE);
                    }else{
                        mGradeChooseXuenianXueqiView.show(progressbar);
                        mStudent_grade_list.setVisibility(View.GONE);
                    }
                }
            }
        });
        return inflate;
    }

    /**
     * 连接网路获取个人成绩数据
     **/
    private void connectNetData() {
        mName = PreferenceUtils.getString(this, Constants.NAME);
        mXuehao = PreferenceUtils.getString(this, Constants.XUEHAO);
        new MineModule(this).getStudentGradde(mXuehao, mName, true, new MineModule.MineResponseListener() {
            @Override
            public void success(Document document) {
                Elements title = document.select("title");
                if ("登录".equals(title.text())) {
                    Toast.makeText(QueryGradeActivity.this, "登录过期,请重新登录", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(QueryGradeActivity.this, LoginActivity.class));
                    finish();
                    return;
                }
                Element select = document.select("[name=__VIEWSTATE]").first();
                mValue = select.attr("value");
                Element ddlXN = document.select("select#ddlXN").first();
                Elements select1 = ddlXN.select("option[value]");
                if (select1.size() > 6) {
                    for (int i = select1.size() - 5; i < select1.size(); i++) {
                        if (i==0){
                            continue;
                        }
                        xuenianList.add(new ClassTableChooseBean(select1.get(i).text(), false));
                    }
                } else {
                    for (int i = 0; i < select1.size(); i++) {
                        xuenianList.add(new ClassTableChooseBean(select1.get(i).text(), false));
                    }
                }
                Element ddlXQ = document.select("select#ddlXQ").first();
                Elements ddlXQ_option = ddlXQ.select("option[value]");
                for (int i = 0; i < ddlXQ_option.size(); i++) {
                    if (i == 0){
                        continue;
                    }
                    if (i==3){
                        continue;
                    }
                    xueqiList.add(new ClassTableChooseBean(ddlXQ_option.get(i).text(), false));
                }
                showContentView();

                mGradeChooseXuenianXueqiView = new GradeChooseXuenianXueqiView(mHandler, QueryGradeActivity.this, xuenianList, xueqiList);
                mGradeChooseXuenianXueqiView.setonViewShowListener(new GradeChooseXuenianXueqiView.onViewShowListener() {
                    @Override
                    public void showed() {
                        current_xueqi.setVisibility(View.INVISIBLE);
                        lesson_num.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void dismissed() {
                        if (mEnentBusTableBean!= null){
                            current_xueqi.setVisibility(View.VISIBLE);
                            lesson_num.setVisibility(View.VISIBLE);
                            lesson_num.setText("第"+mEnentBusTableBean.getXueqi()+"学期");
                            current_xueqi.setText(mEnentBusTableBean.getXuenian()+"学年");
                        }

                    }
                });
                mGradeChooseXuenianXueqiView.show(progressbar);
                //获取历年成绩数据
//                getAllYearData("", "", "历年成绩", mValue);
                progressbar.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 获取历年成绩数据
     **/
    public void getAllYearData(String xuenian, String xueqi, String type, String value) {
        new MineModule(this).getAllYearGradde(mXuehao, mName, xuenian, xueqi, type, value, true, new MineModule.MineResponseListener() {

            @Override
            public void success(Document document) {

                if (mEnentBusTableBean!= null){
                    current_xueqi.setVisibility(View.VISIBLE);
                    lesson_num.setVisibility(View.VISIBLE);
                    lesson_num.setText("第"+mEnentBusTableBean.getXueqi()+"学期");
                    current_xueqi.setText(mEnentBusTableBean.getXuenian()+"学年");
                }

                showContentView();
                progressbar.setVisibility(View.GONE);
                mStudent_grade_list.setVisibility(View.VISIBLE);
                Elements title = document.select("title");
                if (!"现代教学管理信息系统".equals(title.text())) {
                    Toast.makeText(QueryGradeActivity.this, "登录过期,请重新登录", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(QueryGradeActivity.this, LoginActivity.class));
                    finish();
                    return;
                }
                mGradeBeanArrayList.clear();
                Elements body = document.select("table.datelist");
                Element select = document.select("[name=__VIEWSTATE]").first();
                mValue = select.attr("value");
                //获取所有信息
                Elements tr = body.select("tr");
                for (int i = 1; i < tr.size(); i++) {
                    Element element = tr.get(i);
                    Elements td = element.select("td");
                    String xuenian = td.get(0).text();
                    String xueqi = td.get(1).text();
                    String xsubject = td.get(3).text();
                    String subjectType = td.get(4).text();
                    String xuefen = td.get(6).text();
                    String jidian = td.get(7).text();
                    String cehngji = td.get(12).text();
                    StudentGradeBean studentGradeBean = new StudentGradeBean();
                    studentGradeBean.setChengji(cehngji);
                    studentGradeBean.setJidian(jidian);
                    studentGradeBean.setSubject(xsubject);
                    studentGradeBean.setSubjectType(subjectType);
                    studentGradeBean.setXueFen(xuefen);
                    studentGradeBean.setXuenian(xuenian);
                    studentGradeBean.setXueqi(xueqi);
                    mGradeBeanArrayList.add(studentGradeBean);
                }
                setRecycleVIew(mGradeBeanArrayList);
            }
        });
    }

    private void setRecycleVIew(ArrayList<StudentGradeBean> gradeBeanArrayList) {
        if (mAdapter==null){
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(QueryGradeActivity.this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mStudent_grade_list.setLayoutManager(linearLayoutManager);
            mAdapter = new GradeRecycleAdapter(gradeBeanArrayList, QueryGradeActivity.this);
            mStudent_grade_list.setAdapter(mAdapter);
        }else{
            mAdapter.notifyDataSetChanged();
        }
    }

}
