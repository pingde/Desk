package com.baiheplayer.desk.global.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ImageView;

import com.baiheplayer.desk.R;
import com.baiheplayer.desk.global.activity.SettingActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;


@ContentView(R.layout.fragment_mine)
public class MineFragment extends BaseFragment {
    private static MineFragment mineFragment;

    public MineFragment() {
    }

    public static MineFragment getInstance() {
        if (mineFragment == null) {
            mineFragment = new MineFragment();
        }
        return mineFragment;
    }


    @ViewInject(R.id.img_avatar)
    private ImageView mAvatar;

    private Context context;


    @Override
    public void getData(@Nullable Bundle savedInstanceState) {
        context = getContext();
    }

    @Override
    public void onView() {

    }
    /**
     * 设置
     */
    @Event(R.id.ll_set)
    private void gotoSet(View view) {
        startActivity(new Intent(context, SettingActivity.class));
    }

    /**
     * 点击昵称
     * @param view
     */
    @Event(R.id.ll_nickname)
    private void clickNickName(View view){
        showInfo("昵称 TODO");
    }
    /**
     * 点击电话
     * @param view
     */
    @Event(R.id.ll_phone)
    private void clickPhone(View view){
        showInfo("号码 TODO");
    }
    /**
     * 点击邮箱
     * @param view
     */
    @Event(R.id.ll_email)
    private void clickEmail(View view){
        showInfo("邮箱 TODO");
    }


    private void showInfo(String info){
        Snackbar.make(mAvatar,info,Snackbar.LENGTH_SHORT).show();
    }
}
