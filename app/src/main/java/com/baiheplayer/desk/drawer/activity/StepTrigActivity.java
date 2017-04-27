package com.baiheplayer.desk.drawer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.baiheplayer.desk.R;
import com.baiheplayer.desk.global.activity.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

/**
 * Created by Administrator on 2017/3/10.
 */
@ContentView(R.layout.activity_drawer_step_trigger)
public class StepTrigActivity extends BaseActivity {
    @Override
    public void onView(Bundle savedInstanceState) {
    }

    @Event(value = {R.id.rl_back,R.id.img_back})
    private void goBack(View view){
        finish();
    }

    @Event(R.id.btn_trigger_next)
    private void stepNext(View view){
        startActivity(new Intent(this,StepWifiActivity.class));
        finish();
    }


}
