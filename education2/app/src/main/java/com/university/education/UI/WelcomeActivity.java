package com.university.education.UI;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.university.education.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 启动页
 */

public class WelcomeActivity extends Activity {
    private Timer timer;
    private TimerTask timerTask;
    private long exitTime = 0;
    private ImageView mImageView;
    private LinearLayout tubiao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.welcome);
        mImageView = (ImageView) findViewById(R.id.welcome_default_imageview);
        tubiao = (LinearLayout) findViewById(R.id.tubiao);
        setAnimation();

        timer = new Timer();

    }

    /**
     * 设置动画
     */
    private void setAnimation() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mImageView, "alpha", 1f, 0.2f);
        objectAnimator.setDuration(2000);
        objectAnimator.start();
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                tubiao.setVisibility(View.VISIBLE);
                timerTask = new TimerTask() {

                    @Override
                    public void run() {
                        startActivity(new Intent(WelcomeActivity.this,
                                MainActivity.class));
                        finish();
                    }
                };
                timer.schedule(timerTask, 1000);
            }
        });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
//				Toast.makeText(getApplicationContext(), "再按一次退出程序",
//						Toast.LENGTH_SHORT).show();
//				exitTime = System.currentTimeMillis();
            } else {
//				finish();
//				System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
