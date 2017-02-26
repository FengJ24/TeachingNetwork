package com.university.education.fragment;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.university.education.R;
import com.university.education.UI.ClassTableActivity;
import com.university.education.UI.LevelExamActivity;
import com.university.education.UI.QueryGradeActivity;
import com.university.education.UI.TeachPlanActivity;
import com.university.education.base.BaseFragment;
import com.university.education.bean.EventBusBean;
import com.university.education.constants.Constants;
import com.university.education.httpEngine.MineModule;
import com.university.education.utils.PreferenceUtils;

import org.greenrobot.eventbus.EventBus;
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
    private LinearLayout class_table;
    private LinearLayout query_grade;
    private LinearLayout level_exam;
    private LinearLayout teach_plan;
    private LinearLayout exit;
    private String mXuehao1;

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
        exit = (LinearLayout) inflate.findViewById(R.id.exit);
        return inflate;
    }

    @Override
    public void initDate() {
        base_name.setText("个人中心");
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
            case R.id.level_exam:
                intent.setClass(activity, LevelExamActivity.class);
                startActivity(intent);
                break;
            case R.id.teach_plan:
                intent.setClass(activity, TeachPlanActivity.class);
                startActivity(intent);
                break;
            case R.id.exit:
                Toast.makeText(mActivity, "退出登录", Toast.LENGTH_SHORT).show();
                exitLogin();
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
}
