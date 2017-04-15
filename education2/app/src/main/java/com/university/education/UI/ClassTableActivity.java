package com.university.education.UI;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.university.education.R;
import com.university.education.adapter.ClassTableAdapter;
import com.university.education.base.BaseActivity;
import com.university.education.bean.ClassTableChooseBean;
import com.university.education.bean.EnentBusTableBean;
import com.university.education.bean.SubjectBean;
import com.university.education.bean.WeekBean;
import com.university.education.constants.Constants;
import com.university.education.httpEngine.MineModule;
import com.university.education.utils.PreferenceUtils;
import com.university.education.view.ClassLessonDetailDailog;
import com.university.education.view.DifClassTableDailog;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jian on 2016/12/28.
 */

public class ClassTableActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBase_activity_back;
    private String mName;
    private String mXuehao;
    private GridView mClass_table_gridview;
    private ClassTableAdapter mClassTableAdapter;
    private boolean isFirstRequest = true;
    private String mSelectXuenian;
    private String mSelectXueqi;
    private TextView class_table_tip;
    private Button showothertable;
    private List<ClassTableChooseBean> mXuenian;
    private List<ClassTableChooseBean> mXueqi;
    private ProgressBar progressbar;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constants.CLASS_TABLE_DIF:
                    EnentBusTableBean enentBusTableBean = (EnentBusTableBean) msg.obj;
                    getClassTableData(PreferenceUtils.getString(ClassTableActivity.this, Constants.CLASS_TABLE_VIEWSTATE), enentBusTableBean.getXuenian(), enentBusTableBean.getXueqi());
                    mClass_table_gridview.setVisibility(View.INVISIBLE);
                    progressbar.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };
    private ArrayList<SubjectBean> mSubjectBeenList;


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

    private void getClassTableData(String value, String xuenian, String xueqi) {
        mName = PreferenceUtils.getString(this, Constants.NAME);
        mXuehao = PreferenceUtils.getString(this, Constants.XUEHAO);
        new MineModule(this).getDifClassTable(mXuehao, mName, value, xuenian, xueqi, new MineModule.MineResponseListener() {
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
        Elements select = document.select("table#Table1");
        if (isFirstRequest) {
            //获取ViewState
            Element first = document.select("[name=__VIEWSTATE]").first();
            String s = first.attributes().get("value");
            PreferenceUtils.putString(ClassTableActivity.this, Constants.CLASS_TABLE_VIEWSTATE, s);
            //获取学年和学期
            Element mXueNian = document.select("select#xnd").first();
            Elements select1 = mXueNian.select("option[value]");
            for (int i = 0; i < select1.size(); i++) {
                String text = select1.get(i).text();
                mXuenian.add(new ClassTableChooseBean(text, false));
            }
            Element mXueQi = document.select("select#xqd").first();
            Elements select2 = mXueQi.select("option[value]");
            for (int i = 0; i < select2.size(); i++) {
                String text = select2.get(i).text();
                mXueqi.add(new ClassTableChooseBean(text, false));
            }
            isFirstRequest = false;
        }
        //获取选中学年和学期
        Elements select1 = document.select("option[selected]");
        mSelectXuenian = select1.get(0).text();
        mSelectXueqi = select1.get(1).text();
        setModifyData();
        //获取详细课表
        Elements trs = document.select("tr");
        ArrayList<WeekBean> beanArrayList = new ArrayList<>();
        mSubjectBeenList = new ArrayList<>();
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
                        mSubjectBeenList.add(subjectBean);
                    }
                }
                if (i == 6) {
                    //第三四节
                    if (j > 0) {
                        Element element = tds.get(j);
                        String text = element.text();
                        SubjectBean subjectBean = new SubjectBean(Constants.SUBJECT, text);
                        mSubjectBeenList.add(subjectBean);
                    }
                }
                if (i == 8) {
                    //第五六节
                    if (j > 1) {
                        Element element = tds.get(j);
                        String text = element.text();
                        SubjectBean subjectBean = new SubjectBean(Constants.SUBJECT, text);
                        mSubjectBeenList.add(subjectBean);
                    }
                }
                if (i == 10) {
                    //第七八节
                    if (j > 0) {
                        Element element = tds.get(j);
                        String text = element.text();
                        SubjectBean subjectBean = new SubjectBean(Constants.SUBJECT, text);
                        mSubjectBeenList.add(subjectBean);
                    }
                }
                if (i == 12) {
                    //第九十节
                    if (j > 1) {
                        Element element = tds.get(j);
                        String text = element.text();
                        SubjectBean subjectBean = new SubjectBean(Constants.SUBJECT, text);
                        mSubjectBeenList.add(subjectBean);
                    }
                }
                if (i == 14) {
                    //第十一 十二节
                    if (j > 0) {
                        Element element = tds.get(j);
                        String text = element.text();
                        SubjectBean subjectBean = new SubjectBean(Constants.SUBJECT, text);
                        mSubjectBeenList.add(subjectBean);
                    }
                }
            }


        }
        if (subjectItemList.size() != 0 && mSubjectBeenList.size() != 0 && beanArrayList.size() != 0) {
            mClassTableAdapter.setWeekBeanList(beanArrayList);
            mClassTableAdapter.setSubjectBeanList(mSubjectBeenList);
            mClassTableAdapter.setStringList(subjectItemList);
            mClass_table_gridview.setAdapter(mClassTableAdapter);
            setListViewHeightBasedOnChildren(mClass_table_gridview);
            mClass_table_gridview.setVisibility(View.VISIBLE);
            progressbar.setVisibility(View.GONE);
        }
    }

    private void setModifyData() {
        class_table_tip.setText(mSelectXuenian + "学年第" + mSelectXueqi + "学期");
    }


    @Override
    public void initListener() {
        final ClassLessonDetailDailog classLessonDetailDailog = new ClassLessonDetailDailog(this);
        mClass_table_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < 16) {
                    classLessonDetailDailog.setLessDetail(mSubjectBeenList.get(position - 9).getText());
                } else if (position < 24) {
                    classLessonDetailDailog.setLessDetail(mSubjectBeenList.get(position - 10).getText());
                } else if (position < 32) {
                    classLessonDetailDailog.setLessDetail(mSubjectBeenList.get(position - 11).getText());
                } else if (position < 40) {
                    classLessonDetailDailog.setLessDetail(mSubjectBeenList.get(position - 12).getText());
                } else if (position<48){
                    classLessonDetailDailog.setLessDetail(mSubjectBeenList.get(position - 13).getText());
                }

            }
        });
    }

    @Override
    public void initData(TextView base_name, ImageView base_activity_pic, ImageView base_activity_back) {
        mXuenian = new ArrayList<>();
        mXueqi = new ArrayList<>();
        base_name.setText("个人课表");
        progressbar.setVisibility(View.GONE);
        base_activity_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getClassTable();
    }


    @Override
    public Object getContentView() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.activity_class_table, null);
        mClass_table_gridview = (GridView) inflate.findViewById(R.id.class_table_gridview);
        class_table_tip = (TextView) inflate.findViewById(R.id.class_table_tip);
        showothertable = (Button) inflate.findViewById(R.id.showothertable);
        progressbar = (ProgressBar) inflate.findViewById(R.id.progressbar);
        showothertable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showothertable();
            }
        });
        mClassTableAdapter = new ClassTableAdapter(this);
        mClass_table_gridview.setNumColumns(8);
        return inflate;
    }

    /**
     * 显示其它课表 选择年级选择科目
     */
    private void showothertable() {
        DifClassTableDailog difClassTableDailog = new DifClassTableDailog(mHandler, this, mXuenian, mXueqi);
        if (!difClassTableDailog.isShowing()) {
            difClassTableDailog.show();
        }
    }

    public int setListViewHeightBasedOnChildren(GridView gridView) {
        // 获取ListView对应的Adapter
        ListAdapter adapter = gridView.getAdapter();
        if (adapter == null) {
            // pre-condition
            return 0;
        }
        int totalHeight = 0;
        int totalWidth = 0;
        for (int i = 0, len = adapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = adapter.getView(i, null, gridView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
//            totalWidth += listItem.getMeasuredWidth(); // 统计所有子项的总高度
        }
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = (int) (totalHeight / 7.7);
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        gridView.setLayoutParams(params);
        return params.height;
    }

}
