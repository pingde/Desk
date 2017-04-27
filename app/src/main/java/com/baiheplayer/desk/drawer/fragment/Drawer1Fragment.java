package com.baiheplayer.desk.drawer.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.baiheplayer.desk.R;
import com.baiheplayer.desk.drawer.Drawer;
import com.baiheplayer.desk.drawer.activity.DrawerSetActivity;
import com.baiheplayer.desk.global.fragment.BaseFragment;
import com.baiheplayer.desk.drawer.adapter.Drawer1FragmentAdapter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2017/3/10.
 */
@ContentView(R.layout.fragment_drawer)
public class Drawer1Fragment extends BaseFragment {
    private static String TAG = "Drawer1Fragment";
    private static Drawer1Fragment fragment;
    private int num = 0;

    public Drawer1Fragment() {
    }

    public static Drawer1Fragment getInstance() {
        if (fragment == null) {
            fragment = new Drawer1Fragment();
        }
        Log.i("chen", "拿到 fragment" + fragment.hashCode());
        return fragment;
    }

    @ViewInject(R.id.vp_drawer_container)
    private ViewPager mViewPager;
    @ViewInject(R.id.ll_notice_container)
    private LinearLayout mNotice;

    private Context context;
    private Drawer1FragmentAdapter adapter;
    private Drawer drawer;
    @Override
    public void getData(@Nullable Bundle savedInstanceState) {
        context = getContext();

    }

    @Override
    public void onView() {
        adapter = new Drawer1FragmentAdapter(getChildFragmentManager());
        adapter.init(context,mViewPager,mNotice);
    }

    /**
     * 跳转到设置界面
     * @param view
     */
    @Event(R.id.img_drawer_set)
    private void goToSetting(View view){
        Intent intent = new Intent(context,DrawerSetActivity.class);
        startActivity(intent);
    }

    @Event(value = {R.id.img_turn_left,R.id.img_turn_right})
    private void switchDrawer(View view){
        switch (view.getId()){
            case R.id.img_turn_left:
                adapter.turnToLeft();
                break;
            case R.id.img_turn_right:
                adapter.turnToRight();
                break;
        }
    }

    public void suitUp(Drawer drawer){
        this.drawer = drawer;

    }
}
