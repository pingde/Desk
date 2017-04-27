package com.baiheplayer.desk.chest.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.baiheplayer.desk.R;
import com.baiheplayer.desk.chest.present.ChestPresent;
import com.baiheplayer.desk.global.activity.BaseActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2017/3/20.
 */
@ContentView(R.layout.activity_chest)
public class ChestActivity extends BaseActivity {

    @ViewInject(R.id.chest_container)
    private FrameLayout mContainer;
    @ViewInject(R.id.ll_drawer)
    private LinearLayout mDrawer;
    @ViewInject(R.id.ll_chest)
    private LinearLayout mChest;
    @ViewInject(R.id.ll_shelf)
    private LinearLayout mShelf;

    ChestPresent present;

    @Override
    public void onView(Bundle savedInstanceState) {
        present = new ChestPresent(this, mDrawer, mChest, mShelf);
        present.init(getSupportFragmentManager(),R.id.chest_container);
    }

    @Event(value = {R.id.ll_drawer,R.id.cb_drawer})
    private void chooseDrawer(View view){
        present.chooseDrawer();
    }
    @Event(value = {R.id.ll_chest,R.id.cb_chest})
    private void chooseChest(View view){
        present.chooseChest();
    }
    @Event(value = {R.id.ll_shelf,R.id.cb_shelf})
    private void chooseShelf(View view){
        present.chooseShelf();
    }

    @Override
    public void onBackPressed() {
        if(present.isInEditMode()){
            present.exitEditMode();
        } else {
            super.onBackPressed();
        }
    }
}
