package com.baiheplayer.desk.chest.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.baiheplayer.desk.R;
import com.baiheplayer.desk.global.fragment.BaseFragment;

import org.xutils.view.annotation.ContentView;

/**
 * Created by Administrator on 2017/3/20.
 */
@ContentView(R.layout.fragment_chest_right)
public class Chest_R extends BaseFragment {

    private static Chest_R fragment;
    public Chest_R(){}
    public static Chest_R getInstance(){
        if(fragment == null){
            fragment = new Chest_R();
        }
        return fragment;
    }


    @Override
    public void getData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onView() {

    }

}
