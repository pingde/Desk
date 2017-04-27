package com.baiheplayer.desk.chest.activity;

import android.os.Bundle;
import android.view.View;

import com.baiheplayer.desk.R;
import com.baiheplayer.desk.global.activity.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

/**
 * Created by Administrator on 2017/3/24.
 */
@ContentView(R.layout.activity_add)
public class AddActivity extends BaseActivity {
    @Override
    public void onView(Bundle savedInstanceState) {

    }

    @Event(value = {R.id.rl_back,R.id.img_back})
    private void goBack(View view){
        onBackPressed();
    }
}
