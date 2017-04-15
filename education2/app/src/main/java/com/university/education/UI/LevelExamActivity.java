package com.university.education.UI;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.university.education.R;
import com.university.education.base.BaseActivity;
import com.university.education.constants.Constants;
import com.university.education.httpEngine.MineModule;
import com.university.education.utils.PreferenceUtils;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Created by jian on 2016/12/28.
 */

public class LevelExamActivity extends BaseActivity {

    private String mName;
    private String mXuehao;
    private MineModule mMineModule;

    @Override
    public void initListener() {

    }

    @Override
    public void initData(TextView base_name, ImageView base_activity_pic, ImageView base_activity_back) {
        base_name.setText("等级考试");
        base_activity_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mMineModule = new MineModule(this);
        mName = PreferenceUtils.getString(this, Constants.NAME);
        mXuehao = PreferenceUtils.getString(this, Constants.XUEHAO);
        requestNet(mName,mXuehao);
    }

    /**
     * 请求网络
     * @param name
     * @param xuehao
     */
    private void requestNet(String name, String xuehao) {
        mMineModule.getLevelExam(xuehao, name, new MineModule.MineResponseListener() {
            @Override
            public void success(Document document) {
                showContentView();
                Elements title = document.select("title");
                if (!"现代教学管理信息系统".equals(title.text())) {
                    Toast.makeText(LevelExamActivity.this, "登录过期,请重新登录", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LevelExamActivity.this, LoginActivity.class));
                    finish();
                    return;
                }

            }
        });
    }


    @Override
    public Object getContentView() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.activity_level_exam, null);
        return inflate;
    }
}
