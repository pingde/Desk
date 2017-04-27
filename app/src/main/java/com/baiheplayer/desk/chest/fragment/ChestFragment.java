package com.baiheplayer.desk.chest.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.baiheplayer.desk.R;
import com.baiheplayer.desk.global.fragment.BaseFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/20.
 */
@ContentView(R.layout.fragment_chest)
public class ChestFragment extends BaseFragment {
    private static ChestFragment fragment;

    public ChestFragment() {
    }

    public static ChestFragment getInstance() {
        if (fragment == null) {
            fragment = new ChestFragment();
        }
        return fragment;
    }

    @ViewInject(R.id.vp_drawer_container)
    private ViewPager mViewPager;
    Context context;
    private ChestAdapter adapter;
    private List<Fragment> list;
    Chest_L mChestL;
    Chest_R mChestR;

    @Override
    public void getData(@Nullable Bundle savedInstanceState) {
        context = getContext();
        list = new ArrayList<>();
        mChestL = Chest_L.getInstance();
        mChestR = Chest_R.getInstance();
        list.add(mChestL);
        list.add(mChestR);
        adapter = new ChestAdapter(getChildFragmentManager());
    }

    @Override
    public void onView() {
        mViewPager.setAdapter(adapter);

    }

    private class ChestAdapter extends FragmentStatePagerAdapter{

        public ChestAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }
}
