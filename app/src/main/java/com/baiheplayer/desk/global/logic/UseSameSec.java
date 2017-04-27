package com.baiheplayer.desk.global.logic;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.CompoundButton;

/**
 * Created by Administrator on 2017/2/22.
 */

public class UseSameSec implements CompoundButton.OnCheckedChangeListener {
    private FragmentManager fm;
    private Fragment f1;
    private Fragment f2;

    public UseSameSec() {
    }

    public UseSameSec(FragmentManager fm, Fragment f1, Fragment f2) {
        this.fm = fm;
        this.f1 = f1;
        this.f2 = f2;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        FragmentTransaction ft = fm.beginTransaction();
        if(isChecked){
            ft.show(f1).hide(f2).commit();
        } else {
            ft.show(f2).hide(f1).commit();
        }
    }
}
