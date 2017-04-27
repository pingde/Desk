package com.baiheplayer.desk.drawer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;

import com.baiheplayer.desk.R;
import com.baiheplayer.desk.control.CmdPool;
import com.baiheplayer.desk.control.UdpHelper;
import com.baiheplayer.desk.drawer.Drawer;
import com.baiheplayer.desk.global.Constant;
import com.baiheplayer.desk.global.DeviceManager;
import com.baiheplayer.desk.global.activity.BaseActivity;
import com.jungly.gridpasswordview.GridPasswordView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2017/3/13.
 */
@ContentView(R.layout.activity_drawer_set_pwd)
public class DrawerPwdActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {
    @ViewInject(R.id.cb_left_pwd)
    private CheckBox cbLeft;
    @ViewInject(R.id.cb_right_pwd)
    private CheckBox cbRight;
    @ViewInject(R.id.gpv_pwd_left)
    private GridPasswordView mLeftPassword;
    @ViewInject(R.id.gpv_pwd_right)
    private GridPasswordView mRightPassword;


    private Drawer drawer;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // TODO: 2017/4/6 指定Constant的 
//            startActivity(new Intent(DrawerPwdActivity.this, DrawerActivity.class));
            onBackPressed();
        }
    };

    @Override
    public void onView(Bundle savedInstanceState) {
        UdpHelper.getExepool();
        drawer = (Drawer) DeviceManager.getInstance().getDevice(Constant.OPT_DEVICE_HOST);
        mLeftPassword.setPasswordVisibility(true);
        mRightPassword.setPasswordVisibility(true);
        cbLeft.setOnCheckedChangeListener(this);
        cbRight.setOnCheckedChangeListener(this);
    }

    @Event(R.id.btn_drawer_set_ensure)
    private void ensurePwd(View view) {
        int type = 0;
        String strLeft = mLeftPassword.getPassWord();
        String strRight = mRightPassword.getPassWord();
        if(cbLeft.isChecked() && cbRight.isChecked()){
            type = CmdPool.BOTH;
        } else if(cbLeft.isChecked()){
            type = CmdPool.LEFT;
        } else if(cbRight.isChecked()){
            type = CmdPool.RIGHT;
        } else {
            type = CmdPool.NONE;
        }

        if (strLeft == null) {
            strLeft = "AAAA";
        }

        if (strRight == null) {
            strRight = "AAAA";
        }

        String cmd = drawer.setPassword(CmdPool.DEVICE_ID, strLeft, strRight,type);
        UdpHelper.executeCommand(cmd);
        handler.sendEmptyMessageDelayed(100, 3000);
    }

    @Event(R.id.btn_drawer_set_skip)
    private void skipPwd(View view) {
        handler.sendEmptyMessage(100);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (buttonView.getId() == R.id.cb_left_pwd) {
            if (isChecked)
                buttonView.setText("设置左抽屉密码");
            else
                buttonView.setText("取消左抽屉密码");

        }
        if (buttonView.getId() == R.id.cb_right_pwd) {
            if (isChecked)
                buttonView.setText("设置右抽屉密码");
            else
                buttonView.setText("取消右抽屉密码");
        }


    }
}
