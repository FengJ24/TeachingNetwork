package com.university.education.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.university.education.R;
import com.university.education.UI.AuthorActivity;
import com.university.education.UI.ClassTableActivity;
import com.university.education.UI.QueryGradeActivity;
import com.university.education.UI.TeachPlanActivity;
import com.university.education.UI.WebviewActivity;
import com.university.education.base.BaseFragment;
import com.university.education.bean.EventBusBean;
import com.university.education.constants.Constants;
import com.university.education.httpEngine.MineModule;
import com.university.education.utils.PreferenceUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


/**
 * Created by jian on 2016/12/25.
 */

public class MeFragment extends BaseFragment implements View.OnClickListener {
    private Activity mActivity;
    private TextView mXuehao;
    private TextView mName;
    private TextView base_name;
    private ImageView basic_activity_back;
    private LinearLayout class_table;
    private LinearLayout query_grade;
    private LinearLayout level_exam;
    private LinearLayout teach_plan;
    private LinearLayout exit;
    private String mXuehao1;
    private LinearLayout siliuji;
    private LinearLayout project;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View initView(Activity activity) {
        mActivity = activity;

        View inflate = LayoutInflater.from(activity).inflate(R.layout.fragment_me, null);
        mXuehao = (TextView) inflate.findViewById(R.id.xuehao);
        mName = (TextView) inflate.findViewById(R.id.name);
        base_name = (TextView) inflate.findViewById(R.id.base_name);
        class_table = (LinearLayout) inflate.findViewById(R.id.class_table);
        query_grade = (LinearLayout) inflate.findViewById(R.id.query_grade);
        level_exam = (LinearLayout) inflate.findViewById(R.id.level_exam);
        teach_plan = (LinearLayout) inflate.findViewById(R.id.teach_plan);
        siliuji = (LinearLayout) inflate.findViewById(R.id.siliuji);
        project = (LinearLayout) inflate.findViewById(R.id.project);
        exit = (LinearLayout) inflate.findViewById(R.id.exit);
        basic_activity_back = (ImageView) inflate.findViewById(R.id.base_activity_backed);
        return inflate;
    }

    @Override
    public void initDate() {
        base_name.setText("个人中心");
        basic_activity_back.setVisibility(View.GONE);
        setData();
        mStateLayout.showContentView();

    }

    private void setData() {
        String name = PreferenceUtils.getString(mActivity, Constants.NAME);
        mXuehao1 = PreferenceUtils.getString(mActivity, Constants.XUEHAO);
        mXuehao.setText("学号: " + mXuehao1);
        mName.setText("姓名: " + name);
    }

    @Override
    public void initListener() {
        class_table.setOnClickListener(this);
        query_grade.setOnClickListener(this);
        level_exam.setOnClickListener(this);
        teach_plan.setOnClickListener(this);
        exit.setOnClickListener(this);
        siliuji.setOnClickListener(this);
        project.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.class_table:
                intent.setClass(activity, ClassTableActivity.class);
                startActivity(intent);
                break;
            case R.id.query_grade:
                intent.setClass(activity, QueryGradeActivity.class);
                startActivity(intent);
                break;
            case R.id.teach_plan:
                intent.setClass(activity, TeachPlanActivity.class);
                startActivity(intent);
                break;
            case R.id.level_exam:
                //学信网
                intent.setClass(activity, WebviewActivity.class);
                intent.putExtra("url","https://account.chsi.com.cn/passport/login?service=https%3A%2F%2Fmy.chsi.com.cn%2Farchive%2Fj_spring_cas_security_check");
                intent.putExtra("title","个人学信网");
                intent.putExtra("name","个人学信网");
                startActivity(intent);
                break;
            case R.id.exit:
                Toast.makeText(mActivity, "退出登录", Toast.LENGTH_SHORT).show();
                exitLogin();
                break;
            case R.id.siliuji:
                //四六级
                intent.setClass(activity, WebviewActivity.class);
                intent.putExtra("url","http://cet.superdaxue.com/");
                intent.putExtra("title","四六级查询");
                intent.putExtra("name","四六级查询");
                startActivity(intent);
                break;
            case R.id.project:
               //关于作品
                intent.setClass(activity, AuthorActivity.class);
                startActivity(intent);
                break;
        }


    }

    private void exitLogin() {
        new MineModule(mActivity).exit_Login(mXuehao1, new MineModule.MineResponseListener() {
            @Override
            public void success(Document document) {
                Elements title = document.select("title");
                if ("登录".equals(title.text())) {
                    Toast.makeText(mActivity, "退出成功", Toast.LENGTH_SHORT).show();
                    PreferenceUtils.putString(mActivity, Constants.USERNAME, "");
                    PreferenceUtils.putString(mActivity, Constants.PASSWORD, "");
                    EventBus.getDefault().post(new EventBusBean(Constants.LOGIN_BACK, ""));
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshData(EventBusBean eventBusBean){
        if (eventBusBean.getType().equals(Constants.LOGIN_SUCCESS)){
            setData();
        }

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
