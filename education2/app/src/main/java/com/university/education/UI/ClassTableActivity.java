package com.university.education.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.university.education.R;
import com.university.education.adapter.ClassTableAdapter;
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

public class ClassTableActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mBase_activity_back;
    private String mName;
    private String mXuehao;
    private GridView mClass_table_gridview;
    private ClassTableAdapter mClassTableAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_table);
        initView();
        getClassTable();
    }

    /*获取课程表数据*/
    private void getClassTable() {
        mName = PreferenceUtils.getString(this, Constants.NAME);
        mXuehao = PreferenceUtils.getString(this, Constants.XUEHAO);
        new MineModule(this).getClassTable(mXuehao, mName, true, new MineModule.MineResponseListener() {
            @Override
            public void success(Document document) {
                setClassTable(document);
//                Elements title = document.select("title");
//                if (!"现代教学管理信息系统".equals(title.text())) {
//                    Toast.makeText(ClassTableActivity.this, "登录过期,请重新登录", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(ClassTableActivity.this, LoginActivity.class));
//                    finish();
//                    return;
//                }
//                Element select = document.select("[name=__VIEWSTATE]").first();
//                String value = select.attr("value");
//                //将个人成绩中的ViewState存储
//                PreferenceUtils.putString(ClassTableActivity.this, Constants.CLASS_TABLE_VIEWSTATE, value);
//                //获取课程表数据
//                getClassTableData(value);
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

    private void initView() {
        ((TextView) findViewById(R.id.base_name)).setText("个人课表");
        mClass_table_gridview = (GridView) findViewById(R.id.class_table_gridview);
        mClassTableAdapter = new ClassTableAdapter(this);
//        mClass_table_gridview.setAdapter(mClassTableAdapter);
        mClass_table_gridview.setNumColumns(8);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.base_activity_back:
                finish();
                break;

        }
    }
//    public String parseCourse(Document doc) {
//        StringBuilder result = new StringBuilder();
//        Elements elements = doc.select("table#Table1");
//        Element element = elements.get(0).child(0);
//        //移除一些无用数据
//        element.child(1).remove();
//        element.child(1).child(0).remove();
//        element.child(5).child(0).remove();
//        element.child(9).child(0).remove();
//        int rowNum = element.childNodeSize() - 1;
//
//        for (int i = 1; i < rowNum; i++) {
//            Element row = element.child(i);
//            int columnNum = row.childNodeSize() - 2;
//            for (int j = 1; j < columnNum; j++) {
//                Element column = row.child(j);
//
//                if (!column.html().equals("&nbsp;")) {
//                    result.append(column.html()+ "\n\n");
//                    splitCourse(column.html());//所提取课程里面可能包含多节课，进行分割
//                }
//            }
//        }
//        String s = result.toString();
//        return result.toString();
//    }
//    /**
//     *
//     * 提取课程格式，可能包含多节课
//     * @param str
//     * @return
//     */
//    private int splitCourse(String str) {
//        String pattern = "<br /><br />";
//        String[] split = str.split(pattern);
//        if (split.length > 1) {// 如果大于一节课
//            for (int i = 0; i < split.length; i++) {
//                if(!(split[i].startsWith("<br />")&&split[i].endsWith("<br />"))){
//                    storeCourseByResult(split[i]);//保存单节课
//                }
//                else{
//                    //<br />文化地理（网络课程）<br />周日第10节{第17-17周}<br />李宏伟<br />
//                    //以上格式的特殊处理
//                    int brLength="<br />".length();
//                    String substring = split[i].substring(brLength, split[i].length()-brLength);
//                    storeCourseByResult(substring);//保存单节课
//                }
//            }
//            return split.length;
//        } else {
//            storeCourseByResult(str);//保存
//            return 1;
//        }
//    }
//    /**
//     * 根据传进来的课程格式转换为对应的实体类并保存
//     * @param sub
//     * @return
//     */
//    private Course storeCourseByResult(String sub) {
//        //周二第1,2节{第4-16周}		二,1,2,4,16,null
//        //{第2-10周|3节/周}		null,null,null,2,10,3节/周
//        //周二第1,2节{第4-16周|双周}	二,1,2,4,16,双周
//        //周二第1节{第4-16周}		二,1,null,4,16,null
//        //周二第1节{第4-16周|双周}	二,1,null,4,16,双周
//        // str格式如上，这里只是简单考虑每个课都只有两节课，实际上有三节和四节，模式就要改动，其他匹配模式请自行修改
//        // String reg="周.第(\\d{1,2}),(\\d{1,2})节\\{第(\\d{1,2})-(\\d{1,2})周\\}";
//        //String reg = "周(.)第(\\d{1,2}),(\\d{1,2})节\\{第(\\d{1,2})-(\\d{1,2})周\\|?((.周))?\\}";
//        String reg = "周?(.)?第?(\\d{1,2})?,?(\\d{1,2})?节?\\{第(\\d{1,2})-(\\d{1,2})周\\|?((.*周))?\\}";
//
//        String splitPattern = "<br />";
//        System.out.println(sub);
//        String[] temp = sub.split(splitPattern);
//        Pattern pattern = Pattern.compile(reg);
//        Matcher matcher = pattern.matcher(temp[1]);
//        matcher.matches();
//        Course course = new Course();
//        course.setCourseName(temp[0]);
//        course.setCourseTime(temp[1]);
//        course.setTeacher(temp[2]);
//        try{
//            //数组肯能越界，即没有教师
//            course.setClasssroom(temp[3]);
//        }catch(ArrayIndexOutOfBoundsException e){
//            course.setClasssroom("无教师");
//        }
//        System.out.println(temp[1]);
////        course.setDayOfWeek(CommonUtil.getDayOfWeek(matcher.group(1)));
//        course.setStartSection(Integer.parseInt(matcher.group(2)));
//        if(null!=matcher.group(3))
//            course.setEndSection(Integer.parseInt(matcher.group(3)));
//        else
//            course.setEndSection(Integer.parseInt(matcher.group(2)));
//        course.setStartWeek(Integer.parseInt(matcher.group(4)));
//        course.setEndWeek(Integer.parseInt(matcher.group(5)));
//        String t = matcher.group(6);
////        setEveryWeekByChinese(t, course);
////        save(course);
//        return course;
//    }

}
