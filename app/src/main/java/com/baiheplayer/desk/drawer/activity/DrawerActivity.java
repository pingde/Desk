package com.baiheplayer.desk.drawer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.baiheplayer.desk.R;
import com.baiheplayer.desk.drawer.Drawer;
import com.baiheplayer.desk.global.Constant;
import com.baiheplayer.desk.global.DeviceManager;
import com.baiheplayer.desk.global.activity.BaseActivity;
import com.baiheplayer.desk.drawer.adapter.DrawerPresent;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2017/3/10.
 */
@ContentView(R.layout.activity_drawer)
public class DrawerActivity extends BaseActivity {
    @ViewInject(R.id.ll_drawer)
    private LinearLayout mDrawerlyout;
    @ViewInject(R.id.ll_light)
    private LinearLayout mLightlyout;

    private DrawerPresent present;
    private String host;
    private Drawer drawer;
    @Override
    public void onView(Bundle savedInstanceState) {
        present = new DrawerPresent(this,mDrawerlyout,mLightlyout);
        present.init(getSupportFragmentManager(),R.id.drawer_container);
    }


    @Event(value = {R.id.rl_back,R.id.img_back})
    private void goBack(View view){
        finish();
    }



    @Event(value = {R.id.ll_drawer,R.id.cb_drawer})
    private void chooseDrawer(View view){
        present.chooseDrawer();
    }
    @Event(value = {R.id.ll_light,R.id.cb_light})
    private void chooseLight(View view){
        present.chooseLight();
    }
}
