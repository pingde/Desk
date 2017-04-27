package com.baiheplayer.desk.drawer.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.baiheplayer.desk.R;
import com.baiheplayer.desk.global.activity.BaseActivity;
import com.baiheplayer.desk.global.logic.CheckChangeImpl;
import com.baiheplayer.desk.global.Popup;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

/**
 * Created by Administrator on 2017/3/13.
 */
@ContentView(R.layout.activity_drawer_reset)
public class DrawerResetActivity extends BaseActivity implements View.OnClickListener {

    private View mPopView;
    private TextView mTitle;
    private Popup popup;

    @Override
    public void onView(Bundle savedInstanceState) {
        init();
    }

    @Event(value = {R.id.rl_back,R.id.img_back})
    private void goBack(View view){
        finish();
    }

    @Event(R.id.ll_reset_left)
    private void resetLeft(View view) {
        mTitle.setText("请输入左抽屉原密码");
        popup.locateIn(view);

    }

    @Event(R.id.ll_reset_right)
    private void resetRight(View view) {
        mTitle.setText("请输入右抽屉原密码");
        popup.locateIn(view);
    }

    private void init() {
        mPopView = View.inflate(this, R.layout.pop_input_drawer_pwd, null);
        mTitle = (TextView) mPopView.findViewById(R.id.pop_title);
        EditText et = (EditText) mPopView.findViewById(R.id.et_wifi_password);
        CheckBox iv = (CheckBox) mPopView.findViewById(R.id.cb_wifi_seen);
        iv.setOnCheckedChangeListener(new CheckChangeImpl(et));
        Button button = (Button) mPopView.findViewById(R.id.btn_pwd_ensure);
        button.setOnClickListener(this);
        popup = new Popup(this,mPopView);
    }

    @Override
    public void onClick(View v) {

    }
}
