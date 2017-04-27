package com.baiheplayer.desk.drawer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.baiheplayer.desk.R;
import com.baiheplayer.desk.global.activity.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

/**
 * Created by Administrator on 2017/3/13.
 */
@ContentView(R.layout.activity_drawer_set_skip)
public class DrawerSkipActivity extends BaseActivity {
    private String host;

    @Override
    public void onView(Bundle savedInstanceState) {

    }

    @Event(R.id.btn_drawer_skip_ensure)
    private void ensureSkip(View view){
        Intent intent = new Intent(this,DrawerPwdActivity.class);
        startActivity(intent);
        finish();
    }

    @Event(R.id.btn_drawer_skip_cancel)
    private void cancelSkip(View view){
        Intent intent = new Intent(this,DrawerActivity.class);
        startActivity(intent);
        finish();
    }
}
