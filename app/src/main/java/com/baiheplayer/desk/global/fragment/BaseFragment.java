package com.baiheplayer.desk.global.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import org.xutils.x;

/**
 * Created by Administrator on 2017/2/21.
 */

public abstract class BaseFragment extends Fragment implements View.OnTouchListener{
    private boolean injected = false;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        injected = true;
        return x.view().inject(this,inflater,container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(!injected){
            x.view().inject(this,this.getView());
        }
        onView();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;    //拦截事件
    }

    public abstract void getData(@Nullable Bundle savedInstanceState);
    public abstract void onView();

}
