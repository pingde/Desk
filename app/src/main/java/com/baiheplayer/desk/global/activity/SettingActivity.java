package com.baiheplayer.desk.global.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baiheplayer.desk.DeskApp;
import com.baiheplayer.desk.R;
import com.baiheplayer.desk.global.Constant;
import com.baiheplayer.desk.global.Popup;
import com.baiheplayer.desk.global.Share;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2017/4/19.
 */
@ContentView(R.layout.activity_setting)
public class SettingActivity extends BaseActivity {





    private Popup popup;
    private View view;


    @Override
    public void onView(Bundle savedInstanceState) {
        view = View.inflate(this,R.layout.pop_exit_account,null);
        Button ensure = (Button)view.findViewById(R.id.ensure);
        Button cancel = (Button)view.findViewById(R.id.cancel);
        TextView mName = (TextView)view.findViewById(R.id.tv_nike_name);
        if(Constant.USER_NAME!=null){
            mName.setText("书桌君");  // TODO: 2017/4/19 显示名称
        }

        ensure.setOnClickListener(click);
        cancel.setOnClickListener(click);
        popup = new Popup(this,view);
    }

    @Event(R.id.ll_exit)
    private void exitAccount(View view){
        popup.locateIn(view);
    }

    @Event(value = {R.id.rl_back,R.id.img_back})
    private void goBack(View view){
        onBackPressed();
    }

    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.ensure){
                Constant.isLogin = false;
                Share.putBoolean("isLogin",false);
                Constant.USER_NAME = "";
                Constant.USER_PHONE = "";
                popup.popDown();
                DeskApp.getInstance().finishAllActivity();
            }

            if(v.getId() == R.id.cancel){
                popup.popDown();
            }
        }
    };



}
