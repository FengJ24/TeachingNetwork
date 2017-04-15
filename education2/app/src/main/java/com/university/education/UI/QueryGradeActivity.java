package com.university.education.UI;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.university.education.R;
import com.university.education.adapter.StudentGradeAdapter;
import com.university.education.base.BaseActivity;
import com.university.education.bean.StudentGradeBean;
import com.university.education.constants.Constants;
import com.university.education.httpEngine.MineModule;
import com.university.education.utils.PreferenceUtils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by jian on 2016/12/28.
 */

public class QueryGradeActivity extends BaseActivity {

    private String mName;
    private String mXuehao;
    private ListView mStudent_grade_list;

    @Override
    public void initListener() {

    }

    @Override
    public void initData(TextView base_name, ImageView base_activity_pic, ImageView base_activity_back) {
        base_name.setText("个人成绩");
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
        mStudent_grade_list = (ListView) inflate.findViewById(R.id.student_grade_list);
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
                String value = select.attr("value");
                //将个人成绩中的ViewState存储
                PreferenceUtils.putString(QueryGradeActivity.this, Constants.STUDENG_GRADE_VIEWSTATE, value);
                //获取历年成绩数据
                getAllYearData(value);
            }
        });
    }

    /**
     * 获取历年成绩数据
     **/
    public void getAllYearData(String value) {
        new MineModule(this).getAllYearGradde(mXuehao, mName, value, true, new MineModule.MineResponseListener() {

            @Override
            public void success(Document document) {
                showContentView();
                Elements title = document.select("title");
                if (!"现代教学管理信息系统".equals(title.text())) {
                    Toast.makeText(QueryGradeActivity.this, "登录过期,请重新登录", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(QueryGradeActivity.this, LoginActivity.class));
                    finish();
                    return;
                }
                ArrayList<StudentGradeBean> gradeBeanArrayList = new ArrayList<>();
                Elements body = document.select("table.datelist");
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
                    gradeBeanArrayList.add(studentGradeBean);
                }
                mStudent_grade_list.setAdapter(new StudentGradeAdapter(gradeBeanArrayList));
            }
        });
    }
}
