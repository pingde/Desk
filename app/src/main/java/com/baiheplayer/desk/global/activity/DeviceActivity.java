package com.baiheplayer.desk.global.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.baiheplayer.desk.DeskApp;
import com.baiheplayer.desk.R;
import com.baiheplayer.desk.drawer.Drawer;
import com.baiheplayer.desk.global.DeviceManager;
import com.baiheplayer.desk.global.fragment.DeviceFragment;
import com.baiheplayer.desk.global.fragment.MineFragment;
import com.baiheplayer.desk.global.logic.PageChangeImpl;

import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
@ContentView(R.layout.activity_device)
public class DeviceActivity extends BaseActivity {
    @ViewInject(R.id.fl_device)
    private ViewPager mViewPager;
    @ViewInject(R.id.ll_product)
    private LinearLayout mProduct;
    @ViewInject(R.id.ll_mine)
    private LinearLayout mMine;

    private FragmentManager fm ;
    private PageChangeImpl pci;
    public final static String TAG = "DeviceActivity";
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    public void onView(Bundle savedInstanceState) {

//        DeviceFragment df = DeviceFragment.getInstance();
        DeviceFragment df = new DeviceFragment();
        MineFragment mf = MineFragment.getInstance();
        fragments.add(df);
        fragments.add(mf);
        fm = getSupportFragmentManager();
        mViewPager.setAdapter(new AdapterImpl(fm));
        pci = new PageChangeImpl(this,mProduct,mMine);
        mViewPager.addOnPageChangeListener(pci);

    }

    @Event(value = {R.id.ll_product,R.id.cb_product})
    private void chooseProduct(View v){
        mViewPager.setCurrentItem(0);
        pci.displayProduct();
    }

    @Event(value = {R.id.ll_mine,R.id.cb_mine})
    private void chooseMine(View v){
        mViewPager.setCurrentItem(1);
        pci.displayMine();
    }

    private class AdapterImpl extends FragmentStatePagerAdapter {

        public AdapterImpl(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            Intent i= new Intent(Intent.ACTION_MAIN);  //主启动，不期望接收数据
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //新的activity栈中开启，或者已经存在就调到栈前
            i.addCategory(Intent.CATEGORY_HOME);       //添加种类，为设备首次启动显示的页面
            startActivity(i);
        }
        return super.onKeyDown(keyCode, event);
    }
}
