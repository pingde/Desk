package com.baiheplayer.desk.global.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.baiheplayer.desk.R;
import com.baiheplayer.desk.control.DeviceService;

import com.baiheplayer.desk.drawer.Drawer;
import com.baiheplayer.desk.drawer.activity.DrawerActivity;
import com.baiheplayer.desk.drawer.activity.DrawerSkipActivity;
import com.baiheplayer.desk.global.Constant;
import com.baiheplayer.desk.global.Device;
import com.baiheplayer.desk.global.DeviceManager;
import com.baiheplayer.desk.global.Share;

import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_init)
public class InitActivity extends BaseActivity {

    private final String cLogin = "isLogin";
    private final String cPhone = "phone";
    private final String cPwd = "password";
    private final String cName = "nickname";
    @ViewInject(R.id.pic_init)
    private ImageView mBackground;

    @Override
    public void onView(Bundle savedInstanceState) {
        Constant.isLogin = Share.getBoolean(cLogin, false);
        Constant.USER_PHONE = Share.getString(cPhone, "");
        Constant.USER_PASSWORD = Share.getString(cPwd, "");
        Constant.USER_NAME = Share.getString(cName, "");
        startService(new Intent(this, DeviceService.class));
        Share.init(this);
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.anim_init);
        mBackground.startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (Constant.isLogin) {
                    startActivity(new Intent(InitActivity.this, DeviceActivity.class));
                } else {
                    startActivity(new Intent(InitActivity.this, LoginActivity.class));
                }
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

}
