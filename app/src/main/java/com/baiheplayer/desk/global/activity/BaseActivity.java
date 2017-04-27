package com.baiheplayer.desk.global.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.baiheplayer.desk.DeskApp;
import com.zhy.autolayout.AutoLayoutActivity;

import org.greenrobot.eventbus.EventBus;
import org.xutils.x;

/**
 * Created by Administrator on 2017/2/21.
 */

public abstract class BaseActivity extends AutoLayoutActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DeskApp.getInstance().addActivity(this);
        x.view().inject(this);
        onView(savedInstanceState);
    }

    public abstract void onView(Bundle savedInstanceState);

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
