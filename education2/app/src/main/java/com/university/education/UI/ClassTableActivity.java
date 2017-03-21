package com.university.education.UI;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.university.education.R;
import com.university.education.adapter.ClassTableAdapter;
import com.university.education.base.BaseActivity;
import com.university.education.bean.SubjectBean;
import com.university.education.bean.WeekBean;
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

public class ClassTableActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBase_activity_back;
    private String mName;
    private String mXuehao;
    private GridView mClass_table_gridview;
    private ClassTableAdapter mClassTableAdapter;


    /*获取课程表数据*/
    private void getClassTable() {
        mName = PreferenceUtils.getString(this, Constants.NAME);
        mXuehao = PreferenceUtils.getString(this, Constants.XUEHAO);
        new MineModule(this).getClassTable(mXuehao, mName, true, new MineModule.MineResponseListener() {
            @Override
            public void success(Document document) {
                setClassTable(document);
            }
        });
    }

    private void getClassTableData(String value) {
        new MineModule(this).getDifClassTable(mXuehao, mName, value, "2015-2016", "1", new MineModule.MineResponseListener() {
            @Override
            public void success(Document document) {
                setClassTable(document);
            }
        });

    }

    private void setClassTable(Document document) {
        showContentView();
        Elements title = document.select("title");
        if (!"现代教学管理信息系统".equals(title.text())) {
            Toast.makeText(ClassTableActivity.this, "登录过期,请重新登录", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ClassTableActivity.this, LoginActivity.class));
            finish();
            return;
        }
//                parseCourse(document);
        Elements select = document.select("table#Table1");
        Elements trs = document.select("tr");
        ArrayList<WeekBean> beanArrayList = new ArrayList<>();
        ArrayList<SubjectBean> subjectBeenList = new ArrayList<>();
        ArrayList<String> subjectItemList = new ArrayList<>();
        WeekBean weekBean = new WeekBean(Constants.WEEK, "日期");
        beanArrayList.add(weekBean);
        subjectItemList.add("第一、二节");
        subjectItemList.add("第三、四节");
        subjectItemList.add("第五、六节");
        subjectItemList.add("第七、八节");
        subjectItemList.add("第九、十节");
        for (int i = 0; i < trs.size(); i++) {
            Elements tds = trs.get(i).select("td");
            for (int j = 0; j < tds.size(); j++) {
                if (i == 2) {

                    //日期
                    if (j > 0) {
                        Element element = tds.get(j);
                        String text = element.text();
                        weekBean = new WeekBean(Constants.WEEK, text);
                        beanArrayList.add(weekBean);
                    }

                }
                if (i == 4) {
                    //第一二节
                    if (j > 1) {
                        Element element = tds.get(j);
                        String text = element.text();
                        SubjectBean subjectBean = new SubjectBean(Constants.SUBJECT, text);

                        subjectBeenList.add(subjectBean);
                    }
                }
                if (i == 6) {
                    //第三四节
                    if (j > 0) {
                        Element element = tds.get(j);
                        String text = element.text();
                        SubjectBean subjectBean = new SubjectBean(Constants.SUBJECT, text);
                        subjectBeenList.add(subjectBean);
                    }
                }
                if (i == 8) {
                    //第五六节
                    if (j > 1) {
                        Element element = tds.get(j);
                        String text = element.text();
                        SubjectBean subjectBean = new SubjectBean(Constants.SUBJECT, text);
                        subjectBeenList.add(subjectBean);
                    }
                }
                if (i == 10) {
                    //第七八节
                    if (j > 0) {
                        Element element = tds.get(j);
                        String text = element.text();
                        SubjectBean subjectBean = new SubjectBean(Constants.SUBJECT, text);
                        subjectBeenList.add(subjectBean);
                    }
                }
                if (i == 12) {
                    //第九十节
                    if (j > 1) {
                        Element element = tds.get(j);
                        String text = element.text();
                        SubjectBean subjectBean = new SubjectBean(Constants.SUBJECT, text);
                        subjectBeenList.add(subjectBean);
                    }
                }
                if (i == 14) {
                    //第十一 十二节
                    if (j>0) {
                        Element element = tds.get(j);
                        String text = element.text();
                        SubjectBean subjectBean = new SubjectBean(Constants.SUBJECT, text);
                        subjectBeenList.add(subjectBean);
                    }
                }
            }


        }
        if (subjectItemList.size() != 0 && subjectBeenList.size() != 0 && beanArrayList.size() != 0) {
            mClassTableAdapter.setWeekBeanList(beanArrayList);
            mClassTableAdapter.setSubjectBeanList(subjectBeenList);
            mClassTableAdapter.setStringList(subjectItemList);
            mClass_table_gridview.setAdapter(mClassTableAdapter);
        }
    }


    @Override
    public void initListener() {

    }

    @Override
    public void initData(TextView base_name, ImageView base_activity_pic, ImageView base_activity_back) {
        getClassTable();
        base_name.setText("个人课表");
    }


    @Override
    public Object getContentView() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.activity_class_table, null);
        mClass_table_gridview = (GridView)inflate.findViewById(R.id.class_table_gridview);
        mClassTableAdapter = new ClassTableAdapter(this);
        mClass_table_gridview.setNumColumns(8);
        return inflate;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.base_activity_back:
                finish();
                break;

        }
    }

}
