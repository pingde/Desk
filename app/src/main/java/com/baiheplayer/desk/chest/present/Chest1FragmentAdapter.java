package com.baiheplayer.desk.chest.present;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiheplayer.desk.R;
import com.baiheplayer.desk.chest.fragment.Drawer_L;
import com.baiheplayer.desk.chest.fragment.Drawer_R;
import com.baiheplayer.desk.drawer.fragment.Drawer1L_F;
import com.baiheplayer.desk.drawer.fragment.Drawer1R_F;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/13.
 */

public class Chest1FragmentAdapter extends
        FragmentStatePagerAdapter implements ViewPager.OnPageChangeListener {

    private FragmentManager fm;
    private ViewPager mViewPager;
    private LinearLayout mNotice;
    private TextView tv_l;
    private TextView tv_r;

    private Drawer_L drawer1L;
    private Drawer_R drawer1R;
    private List<Fragment> fragments;
    private Context context;

    public Chest1FragmentAdapter(FragmentManager fm) {
        super(fm);
        this.fm = fm;
    }

    public void init(Context context, ViewPager vp, LinearLayout ll) {
        this.context = context;
        mViewPager = vp;
        mNotice = ll;


        fragments = new ArrayList<>();
        drawer1L = Drawer_L.getInstance();
        drawer1R = Drawer_R.getInstance();
        fragments.add(drawer1L);
        fragments.add(drawer1R);
        mViewPager.setAdapter(this);
        tv_l = (TextView) mNotice.findViewById(R.id.tv_l);
        tv_r = (TextView) mNotice.findViewById(R.id.tv_r);
        mViewPager.addOnPageChangeListener(this);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void turnToLeft() {
        chooseLeft();
        mViewPager.setCurrentItem(0);
    }

    public void turnToRight() {
        chooseRight();
        mViewPager.setCurrentItem(1);
    }

    private void chooseLeft() {
        tv_l.setTextColor(context.getResources().getColor(R.color.text_green));
        tv_r.setTextColor(context.getResources().getColor(R.color.text_gray_deep));

    }

    private void chooseRight() {
        tv_l.setTextColor(context.getResources().getColor(R.color.text_gray_deep));
        tv_r.setTextColor(context.getResources().getColor(R.color.text_green));

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position){
            case 0:
                chooseLeft();
                break;
            case 1:
                chooseRight();
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
