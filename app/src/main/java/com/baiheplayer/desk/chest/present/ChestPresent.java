package com.baiheplayer.desk.chest.present;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiheplayer.desk.R;
import com.baiheplayer.desk.chest.fragment.ChestFragment;
import com.baiheplayer.desk.chest.fragment.DrawerFragment;
import com.baiheplayer.desk.chest.fragment.ShelfFragment;

/**
 * Created by Administrator on 2017/3/21.
 */

public class ChestPresent {
    Context context;
    FragmentManager mManager;
    ChestFragment cFragment;
    DrawerFragment dFragment;
    ShelfFragment sFragment;
    LinearLayout mDrawer;
    LinearLayout mChest;
    LinearLayout mShelf;
    CheckBox cb1;
    CheckBox cb2;
    CheckBox cb3;
    TextView tv1;
    TextView tv2;
    TextView tv3;


    public ChestPresent(Context context, LinearLayout mDrawer, LinearLayout mChest, LinearLayout mShelf) {
        this.context = context;
        this.mDrawer = mDrawer;
        this.mChest = mChest;
        this.mShelf = mShelf;
        dFragment = DrawerFragment.getInstance();
        cFragment = ChestFragment.getInstance();
        sFragment = ShelfFragment.getInstance();
    }

    public void init(FragmentManager fm, int resId) {
        mManager = fm;
        cb1 = (CheckBox) mDrawer.getChildAt(0);
        tv1 = (TextView) mDrawer.getChildAt(1);
        cb2 = (CheckBox) mChest.getChildAt(0);
        tv2 = (TextView) mChest.getChildAt(1);
        cb3 = (CheckBox) mShelf.getChildAt(0);
        tv3 = (TextView) mShelf.getChildAt(1);
        mManager.beginTransaction().add(resId, dFragment).show(dFragment)
                .add(resId, cFragment).hide(cFragment)
                .add(resId, sFragment).hide(sFragment).commit();
    }

    public void chooseDrawer() {
        choose(cb1, tv1);
        unChoose(cb2, tv2);
        unChoose(cb3, tv3);
        mManager.beginTransaction().show(dFragment).hide(cFragment).hide(sFragment).commit();
    }

    public void chooseChest() {
        unChoose(cb1, tv1);
        choose(cb2, tv2);
        unChoose(cb3, tv3);
        mManager.beginTransaction().hide(dFragment).show(cFragment).hide(sFragment).commit();
    }

    public void chooseShelf() {
        unChoose(cb1, tv1);
        unChoose(cb2, tv2);
        choose(cb3, tv3);
        mManager.beginTransaction().hide(dFragment).hide(cFragment).show(sFragment).commit();
    }

    private void choose(CheckBox cb, TextView tv) {
        cb.setChecked(true);
        tv.setTextColor(context.getResources().getColor(R.color.text_green));
    }

    private void unChoose(CheckBox cb, TextView tv) {
        cb.setChecked(false);
        tv.setTextColor(context.getResources().getColor(R.color.text_gray_deep));
    }

    public boolean isInEditMode() {
        return sFragment.isInEditMode();
    }

    public void exitEditMode(){
        sFragment.exitEditMode();
    }
}
