package com.baiheplayer.desk.chest.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.baiheplayer.desk.R;
import com.baiheplayer.desk.chest.present.Chest1FragmentAdapter;
import com.baiheplayer.desk.drawer.Drawer;
import com.baiheplayer.desk.drawer.activity.DrawerSetActivity;
import com.baiheplayer.desk.drawer.adapter.Drawer1FragmentAdapter;
import com.baiheplayer.desk.global.fragment.BaseFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2017/3/20.
 */
@ContentView(R.layout.fragment_drawer)    //合用一个fragment_drawer
public class DrawerFragment extends BaseFragment {

    private static DrawerFragment fragment;
    public DrawerFragment(){}

    public static DrawerFragment getInstance(){
        if(fragment == null){
            fragment = new DrawerFragment();
        }
        return fragment;
    }


    @ViewInject(R.id.vp_drawer_container)
    private ViewPager mViewPager;
    @ViewInject(R.id.ll_notice_container)
    private LinearLayout mNotice;

    private Context context;
    private Chest1FragmentAdapter adapter;
    private Drawer drawer;


    @Override
    public void getData(@Nullable Bundle savedInstanceState) {
        context = getContext();
    }

    @Override
    public void onView() {
        adapter = new Chest1FragmentAdapter(getChildFragmentManager());
        adapter.init(context,mViewPager,mNotice);
    }

    /**
     * 跳转到设置界面
     * @param view
     */
    @Event(R.id.img_drawer_set)
    private void goToSetting(View view){
        // TODO: 2017/4/13
//        Intent intent = new Intent(context,DrawerSetActivity.class);
//        startActivity(intent);
        Snackbar.make(mViewPager,"暂无设置",Snackbar.LENGTH_SHORT).show();
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
