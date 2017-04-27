package com.baiheplayer.desk.chest.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.baiheplayer.desk.R;
import com.baiheplayer.desk.drawer.view.ComboView;
import com.baiheplayer.desk.global.fragment.BaseFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2017/3/20.
 */
@ContentView(R.layout.fragment_chest_left)
public class Chest_L extends BaseFragment {

    @ViewInject(R.id.rcv_define)
    private ComboView mRound;

    private static Chest_L fragment;
    public Chest_L(){}
    public static Chest_L getInstance(){
        if(fragment == null){
            fragment = new Chest_L();
        }
        return fragment;
    }

    @Override
    public void getData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onView() {

    }

    @Event(R.id.rcv_define)
    private void click(View view){
        mRound.open();
    }
}
