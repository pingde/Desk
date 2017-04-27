package com.baiheplayer.desk.global.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baiheplayer.desk.R;
import com.baiheplayer.desk.control.CmdPool;
import com.baiheplayer.desk.control.UdpHelper;
import com.baiheplayer.desk.drawer.Drawer;
import com.baiheplayer.desk.drawer.activity.DrawerActivity;
import com.baiheplayer.desk.drawer.activity.DrawerSkipActivity;
import com.baiheplayer.desk.global.Constant;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/3/15.
 */

public class DrawerVH extends RecyclerView.ViewHolder implements View.OnClickListener {
    LinearLayout   simpleOption;
    RelativeLayout disconnect;
    RelativeLayout background;
    RelativeLayout leftDrawer;
    RelativeLayout rightDrawer;
    RelativeLayout lighten;
    ImageView cb_left;
    ImageView cb_right;
    CheckBox cb_light;
    TextView tv_host;
    Drawer drawer;


    Context context;


    public DrawerVH(View itemView) {
        super(itemView);
        simpleOption = (LinearLayout) itemView.findViewById(R.id.ll_simple_opt);
        disconnect = (RelativeLayout) itemView.findViewById(R.id.rl_disconnect);
        background = (RelativeLayout) itemView.findViewById(R.id.drawer_background);
        leftDrawer = (RelativeLayout) itemView.findViewById(R.id.rl_left_drawer);
        rightDrawer = (RelativeLayout) itemView.findViewById(R.id.rl_right_drawer);
        lighten = (RelativeLayout) itemView.findViewById(R.id.rl_light);
        cb_left = (ImageView) itemView.findViewById(R.id.cb_left_drawer);
        cb_right = (ImageView) itemView.findViewById(R.id.cb_right_drawer);
        cb_light = (CheckBox) itemView.findViewById(R.id.cb_light);
        tv_host = (TextView) itemView.findViewById(R.id.tv_host);
    }

    public void onBindViewHolder(Context context, final Drawer drawer) {
        background.setOnClickListener(this);
        leftDrawer.setOnClickListener(this);
        rightDrawer.setOnClickListener(this);
        lighten.setOnClickListener(this);
        cb_left.setOnClickListener(this);
        cb_right.setOnClickListener(this);
        cb_light.setOnClickListener(this);
        this.drawer = drawer;
        this.context = context;
        tv_host.setText(drawer.getHost());
        simpleOption.setVisibility(View.VISIBLE);
        disconnect.setVisibility(View.GONE);
//        background.setClickable(false);
        ready();
        // TODO: 2017/4/10 刷新之后的wifi模块才能显示操作栏
        drawer.addCurrentState(new Drawer.ICurrentState() {
            @Override
            public void onState() {
                ready();
            }
        });
    }

    private void ready() {
        // TODO: 2017/4/12 检测设备连接
//        if(drawer.isFlush){
//            simpleOption.setVisibility(View.VISIBLE);
//            disconnect.setVisibility(View.GONE);
//            background.setClickable(true);
//        } else {
//            simpleOption.setVisibility(View.GONE);
//            disconnect.setVisibility(View.VISIBLE);
//            background.setClickable(false);
//        }

        if (drawer.alarm.equals(CmdPool.F) || drawer.alarm.equals(CmdPool._F)) {
            cb_left.setImageResource(R.drawable.ic_drawer_left_4);     //故障
        } else if (drawer.isLeftLock()) {
            cb_left.setImageResource(R.drawable.ic_drawer_left_3);     //上锁
        } else if (drawer.isLeftOpen()) {
            cb_left.setImageResource(R.drawable.ic_drawer_left_2);     //抽屉开
        } else if (!drawer.isLeftOpen()) {
            cb_left.setImageResource(R.drawable.ic_drawer_left_1);     //抽屉关
        }

        if (drawer.alarm.equals(CmdPool.F) || drawer.alarm.equals(CmdPool.F_)) {
            cb_right.setImageResource(R.drawable.ic_drawer_right_4);     //故障
        } else if (drawer.isRightLock()) {
            cb_right.setImageResource(R.drawable.ic_drawer_right_3);     //上锁
        } else if (drawer.isRightOpen()) {
            cb_right.setImageResource(R.drawable.ic_drawer_right_2);     //抽屉开
        } else if (!drawer.isRightOpen()) {
            cb_right.setImageResource(R.drawable.ic_drawer_right_1);     //抽屉关
        }

        if (drawer.light.equals(CmdPool.A)) {
            cb_light.setChecked(true);
        } else if (drawer.light.equals(CmdPool.O)) {
            cb_light.setChecked(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.drawer_background:
                goToDrawerDetail();
                break;
            case R.id.rl_left_drawer:
            case R.id.cb_left_drawer:
                optionLeftDrawer();
                break;
            case R.id.rl_right_drawer:
            case R.id.cb_right_drawer:
                optionRightDrawer();
                break;
            case R.id.rl_light:
            case R.id.cb_light:
                optionLightSwitch();
                break;
        }
    }

    /**
     * 前往下一个界面
     */
    private void goToDrawerDetail() {
        Intent intent = new Intent();
        if (drawer.lock_s.equals(CmdPool.O)) {        // TODO: 2017/3/16  其实这里是没有设置密码
            intent.setClass(context, DrawerSkipActivity.class);
        } else {
            intent.setClass(context, DrawerActivity.class);
        }
        Constant.OPT_DEVICE_HOST = drawer.getHost(); //设备ip保持
        context.startActivity(intent);
    }

    /**
     * 对左抽屉的点击事件
     */
    private void optionLeftDrawer() {

        if (drawer.alarm.equals(CmdPool.F) || drawer.alarm.equals(CmdPool.F_)) {
            Snackbar.make(background, "it is error.", Snackbar.LENGTH_SHORT).show();
            return;
        } else if (drawer.isLeftLock()) {
            Snackbar.make(background, "it is lock.", Snackbar.LENGTH_SHORT).show();
            return;
        } else if (drawer.isLeftOpen()) {
            String cmd = drawer.codeForDrawerOpen(CmdPool.LEFT, false);  //关命令
            UdpHelper.executeCommand(cmd);
            cb_left.setImageResource(R.drawable.ic_drawer_left_1);
        } else {
            String cmd = drawer.codeForDrawerOpen(CmdPool.LEFT, true);   //开命令
            UdpHelper.executeCommand(cmd);
            cb_left.setImageResource(R.drawable.ic_drawer_left_2);
        }

    }

    /**
     * 对右抽屉的点击事件
     */
    private void optionRightDrawer() {

        if (drawer.alarm.equals(CmdPool.F) || drawer.alarm.equals(CmdPool._F)) {
            Snackbar.make(background, "it is error.", Snackbar.LENGTH_SHORT).show();
            return;
        } else if (drawer.isRightLock()) {
            Snackbar.make(background, "it is lock.", Snackbar.LENGTH_SHORT).show();
        } else if (drawer.isRightOpen()) {
            String cmd = drawer.codeForDrawerOpen(CmdPool.RIGHT, false);
            UdpHelper.executeCommand(cmd);
            cb_right.setImageResource(R.drawable.ic_drawer_right_2);
        } else {
            String cmd = drawer.codeForDrawerOpen(CmdPool.RIGHT, true);
            UdpHelper.executeCommand(cmd);
            cb_right.setImageResource(R.drawable.ic_drawer_right_1);
        }
    }

    /**
     * 灯光点击控制事件+
     *
     *
     */
    private void optionLightSwitch() {

        if (drawer.isLightenOpen()) {
            String cmd = drawer.codeForLight(false);
            UdpHelper.executeCommand(cmd);
            cb_light.setChecked(false);
        } else {
            String cmd = drawer.codeForLight(true);
            UdpHelper.executeCommand(cmd);
            cb_light.setChecked(true);
        }
    }
}
