package com.baiheplayer.desk.drawer.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiheplayer.desk.R;
import com.baiheplayer.desk.drawer.fragment.Drawer1Fragment;
import com.baiheplayer.desk.drawer.fragment.Drawer2Fragment;

/**
 * 管理Drawer的界面控制
 * Created by Administrator on 2017/3/10.
 */

public class DrawerPresent {
    private Context context;
    private FragmentManager mManager;
    private Drawer1Fragment d1f;
    private Drawer2Fragment d2f;
    private LinearLayout l1;
    private LinearLayout l2;
    private CheckBox cb1;
    private CheckBox cb2;
    private TextView tv1;
    private TextView tv2;

    public DrawerPresent(Context context,LinearLayout l1, LinearLayout l2){
        this.context = context;
        this.l1 = l1;
        this.l2 = l2;
        d1f = Drawer1Fragment.getInstance();
        d2f = Drawer2Fragment.getInstance();

    }

    public void init(FragmentManager fm,int id){
        mManager = fm;
        cb1 = (CheckBox)l1.getChildAt(0);
        tv1 = (TextView) l1.getChildAt(1);
        cb2 = (CheckBox) l2.getChildAt(0);
        tv2 = (TextView) l2.getChildAt(1);
        mManager.beginTransaction().add(id,d1f).show(d1f).add(id,d2f).hide(d2f).commit();
    }



    public void chooseDrawer(){
        cb1.setChecked(true);
        tv1.setTextColor(context.getResources().getColor(R.color.green_shallow));
        cb2.setChecked(false);
        tv2.setTextColor(context.getResources().getColor(R.color.text_gray_deep));
        mManager.beginTransaction().show(d1f).hide(d2f).commit();
    }
    public void chooseLight(){
        cb1.setChecked(false);
        tv1.setTextColor(context.getResources().getColor(R.color.text_gray_deep));
        cb2.setChecked(true);
        tv2.setTextColor(context.getResources().getColor(R.color.green_shallow));
        mManager.beginTransaction().show(d2f).hide(d1f).commit();
    }
}
