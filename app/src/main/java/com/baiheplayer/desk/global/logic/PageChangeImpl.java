package com.baiheplayer.desk.global.logic;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiheplayer.desk.R;

/**
 * Created by Administrator on 2017/3/9.
 */

public class PageChangeImpl implements ViewPager.OnPageChangeListener {
    private Context context;
    private LinearLayout l1;
    private LinearLayout l2;
    private CheckBox cb1;
    private TextView tv1;
    private CheckBox cb2;
    private TextView tv2;

    public PageChangeImpl(Context context,LinearLayout l1, LinearLayout l2){
        this.context = context;
        this.l1 = l1;
        this.l2 = l2;
        init();
    }

    private void init(){
        cb1 = (CheckBox)l1.getChildAt(0);
        tv1 = (TextView)l1.getChildAt(1);
        cb2 = (CheckBox)l2.getChildAt(0);
        tv2 = (TextView)l2.getChildAt(1);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        switch (position){
            case 0:
                displayProduct();
                break;
            case 1:
                displayMine();
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {


    }


    public void displayProduct(){
        cb1.setChecked(true);
        tv1.setTextColor(context.getResources().getColor(R.color.green_shallow));
        cb2.setChecked(false);
        tv2.setTextColor(context.getResources().getColor(R.color.text_gray_deep));
    }

    public void displayMine(){
        cb1.setChecked(false);
        tv1.setTextColor(context.getResources().getColor(R.color.text_gray_deep));
        cb2.setChecked(true);
        tv2.setTextColor(context.getResources().getColor(R.color.green_shallow));
    }
}
