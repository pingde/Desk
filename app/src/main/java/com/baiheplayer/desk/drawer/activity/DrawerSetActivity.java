package com.baiheplayer.desk.drawer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.baiheplayer.desk.R;
import com.baiheplayer.desk.control.CmdPool;
import com.baiheplayer.desk.control.UdpHelper;
import com.baiheplayer.desk.drawer.Drawer;
import com.baiheplayer.desk.global.Constant;
import com.baiheplayer.desk.global.DeviceManager;
import com.baiheplayer.desk.global.Popup;
import com.baiheplayer.desk.global.activity.BaseActivity;
import com.baiheplayer.desk.global.logic.CheckChangeImpl;

import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2017/3/10.
 */
@ContentView(R.layout.activity_drawer_set)
public class DrawerSetActivity extends BaseActivity implements View.OnClickListener {


    @ViewInject(R.id.s_left)
    private Switch mLockLeft;
    @ViewInject(R.id.s_right)
    private Switch mLockRight;
    @ViewInject(R.id.tv_notice1)
    private TextView mLeftText;
    @ViewInject(R.id.tv_notice2)
    private TextView mRightText;
    @ViewInject(R.id.ll_pwd_modify)
    private LinearLayout mModify;
    private Drawer drawer;
    private DeviceManager dm;
    private View mPopView;
    private TextView mTitle;
    private Popup popup;
    private static final String TEXT_CLOSE = "关闭后抽屉密码停用";
    private static final String TEXT_OPEN = "开启后抽屉可被上锁";
    private static final String TEXT_VERIFY = "请输入账号密码验证";

    @Override
    public void onView(Bundle savedInstanceState) {
        dm = DeviceManager.getInstance();
        if (Constant.OPT_DEVICE_HOST != null) {
            drawer = (Drawer) dm.getDevice(Constant.OPT_DEVICE_HOST);
            refreshUI();
            drawer.addCurrentState(new Drawer.ICurrentState() {
                @Override
                public void onState() {
                    refreshUI();
                }
            });
        }

        UdpHelper.getExepool();
        mLockLeft.setOnClickListener(this);
        mLockRight.setOnClickListener(this);
        init();
    }

    private void init() {  //底部弹窗的初始化
        mPopView = View.inflate(this, R.layout.pop_input_account_pwd, null);
        mTitle = (TextView) mPopView.findViewById(R.id.pop_title);
        mTitle.setText(TEXT_VERIFY);
        EditText et = (EditText) mPopView.findViewById(R.id.et_wifi_password);
        CheckBox iv = (CheckBox) mPopView.findViewById(R.id.cb_wifi_seen);
        iv.setOnCheckedChangeListener(new CheckChangeImpl(et));
        Button button = (Button) mPopView.findViewById(R.id.btn_pwd_ensure);
        button.setOnClickListener(this);  //验证界面
        popup = new Popup(this, mPopView);
    }

    private void refreshUI() {
        if (drawer.isLeftHasLock()) {
            mLockLeft.setChecked(true);
            mLeftText.setText(TEXT_CLOSE);
        } else {
            mLockLeft.setChecked(false);
            mLeftText.setText(TEXT_OPEN);
        }
        if (drawer.isRightHasLock()) {
            mLockRight.setChecked(true);
            mRightText.setText(TEXT_CLOSE);
        } else {
            mLockRight.setChecked(false);
            mRightText.setText(TEXT_OPEN);
        }
    }


    @Event(value = {R.id.rl_back, R.id.img_back})
    private void goBack(View view) {
        finish();
    }




    @Event(R.id.ll_pwd_reset)
    private void goToResetPassword(View view) {
        Snackbar.make(mLockLeft,"TO DO",Snackbar.LENGTH_SHORT).show();
//        startActivity(new Intent(this, DrawerResetActivity.class));
    }

    @Event(R.id.ll_pwd_modify)
    private void goToDrawerPwdActivity(View view) {

        popup.locateIn(view);
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.s_left) {
            String cmd = drawer.codeForChangeLock(CmdPool.LEFT, mLockLeft.isChecked());
            if (mLockLeft.isChecked()) {
                mLeftText.setText(TEXT_CLOSE);
            } else {
                mLeftText.setText(TEXT_OPEN);
            }
            UdpHelper.executeCommand(cmd);
        }

        if (v.getId() == R.id.s_right) {
            String cmd = drawer.codeForChangeLock(CmdPool.RIGHT, mLockRight.isChecked());
            if (mLockRight.isChecked()) {
                mRightText.setText(TEXT_CLOSE);
            } else {
                mRightText.setText(TEXT_OPEN);
            }
            UdpHelper.executeCommand(cmd);
        }

        if (v.getId() == R.id.btn_pwd_ensure) {
            startActivity(new Intent(this, DrawerPwdActivity.class));
            popup.popDown();
        }
    }

}
