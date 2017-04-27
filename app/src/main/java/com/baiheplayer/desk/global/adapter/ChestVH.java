package com.baiheplayer.desk.global.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baiheplayer.desk.R;
import com.baiheplayer.desk.chest.Chest;
import com.baiheplayer.desk.chest.activity.ChestActivity;
import com.baiheplayer.desk.control.CmdPool;
import com.baiheplayer.desk.control.UdpHelper;
import com.baiheplayer.desk.drawer.Drawer;
import com.baiheplayer.desk.global.Constant;

/**
 * Created by Administrator on 2017/3/15.
 */

public class ChestVH extends RecyclerView.ViewHolder implements View.OnClickListener {

    Context context;
    Chest chest;
    TextView mHost;
    ImageView mBackground;
    ImageView drawerLeft;
    ImageView drawerRight;
    ImageView chestLeft;
    ImageView chestRight;


    public ChestVH(View itemView) {
        super(itemView);
        mBackground = (ImageView) itemView.findViewById(R.id.chest_background);
        drawerLeft = (ImageView) itemView.findViewById(R.id.img_left_drawer);
        drawerRight = (ImageView) itemView.findViewById(R.id.img_right_drawer);
        chestLeft = (ImageView) itemView.findViewById(R.id.img_left_chest);
        chestRight = (ImageView) itemView.findViewById(R.id.img_right_chest);
        drawerLeft.setOnClickListener(this);
        drawerRight.setOnClickListener(this);
        chestLeft.setOnClickListener(this);
        chestRight.setOnClickListener(this);
        mHost = (TextView) itemView.findViewById(R.id.tv_host);
    }

    public void onBindViewHolder(Context context, Chest chest) {
        this.context = context;
        this.chest = chest;
        mBackground.setOnClickListener(this);
        mHost.setText(chest.getHost());
        refreshUI();
        // TODO: 2017/4/10 刷新之后的wifi模块才能显示操作栏
        chest.addCurrentState(new Drawer.ICurrentState() {
            @Override
            public void onState() {
                refreshUI();
            }
        });
    }


    private void refreshUI() {
        // TODO: 2017/4/12 检测设备连接
//        if(chest.isFlush){
//            simpleOption.setVisibility(View.VISIBLE);
//            disconnect.setVisibility(View.GONE);
//            background.setClickable(true);
//        } else {
//            simpleOption.setVisibility(View.GONE);
//            disconnect.setVisibility(View.VISIBLE);
//            background.setClickable(false);
//        }

        if (chest.alarm.equals(CmdPool.F) || chest.alarm.equals(CmdPool._F)) {
            drawerLeft.setImageResource(R.drawable.ic_drawer_left_4);     //故障
        } else if (chest.isDrawerLeftLock()) {
            drawerLeft.setImageResource(R.drawable.ic_drawer_left_3);     //上锁
        } else if (chest.isDrawerLeftOpen()) {
            drawerLeft.setImageResource(R.drawable.ic_drawer_left_2);     //抽屉开
        } else if (!chest.isDrawerLeftOpen()) {
            drawerLeft.setImageResource(R.drawable.ic_drawer_left_1);     //抽屉关
        }

        if (chest.alarm.equals(CmdPool.F) || chest.alarm.equals(CmdPool.F_)) {
            drawerRight.setImageResource(R.drawable.ic_drawer_right_4);     //故障
        } else if (chest.isDrawerRightLock()) {
            drawerRight.setImageResource(R.drawable.ic_drawer_right_3);     //上锁
        } else if (chest.isDrawerRightOpen()) {
            drawerRight.setImageResource(R.drawable.ic_drawer_right_2);     //抽屉开
        } else if (!chest.isDrawerRightOpen()) {
            drawerRight.setImageResource(R.drawable.ic_drawer_right_1);     //抽屉关
        }

        if (chest.isChestLeftOpen()) {
            chestLeft.setImageResource(R.drawable.ic_chest_left_2);
        } else {
            chestLeft.setImageResource(R.drawable.ic_chest_left_1);
        }

        if (chest.isChestRightOpen()) {
            chestRight.setImageResource(R.drawable.ic_chest_right_2);
        } else {
            chestRight.setImageResource(R.drawable.ic_chest_right_1);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chest_background:
                goToChestDetail();
                break;
            case R.id.rl_left_drawer:
            case R.id.img_left_drawer:
                optionLeftDrawer();
                break;
            case R.id.rl_right_drawer:
            case R.id.img_right_drawer:
                optionRightDrawer();
                break;
            case R.id.rl_left_chest:
            case R.id.img_left_chest:
                optionLeftChest();
                break;
            case R.id.rl_right_chest:
            case R.id.img_right_chest:
                optionRightChest();
                break;
        }
    }

    /**
     * 前往下一个界面
     */
    private void goToChestDetail() {
        Intent intent = new Intent();
        intent.setClass(context, ChestActivity.class);
        Constant.OPT_DEVICE_HOST = chest.getHost(); //设备ip保持
        context.startActivity(intent);
    }

    /**
     * 对左抽屉的点击事件
     */
    private void optionLeftDrawer() {

        if (chest.alarm.equals(CmdPool.F) || chest.alarm.equals(CmdPool.F_)) {
            Snackbar.make(mBackground, "it is error.", Snackbar.LENGTH_SHORT).show();
            return;
        } else if (chest.isDrawerLeftLock()) {
            Snackbar.make(mBackground, "it is lock.", Snackbar.LENGTH_SHORT).show();
            return;
        } else if (chest.isDrawerLeftOpen()) {
            String cmd = chest.codeForDrawerOpen(CmdPool.LEFT, false);  //关命令
            UdpHelper.executeCommand(cmd);
            drawerLeft.setImageResource(R.drawable.ic_drawer_left_1);
        } else {
            String cmd = chest.codeForDrawerOpen(CmdPool.LEFT, true);   //开命令
            UdpHelper.executeCommand(cmd);
            drawerLeft.setImageResource(R.drawable.ic_drawer_left_2);
        }

    }

    /**
     * 对右抽屉的点击事件
     */
    private void optionRightDrawer() {

        if (chest.alarm.equals(CmdPool.F) || chest.alarm.equals(CmdPool._F)) {
            Snackbar.make(mBackground, "it is error.", Snackbar.LENGTH_SHORT).show();
            return;
        } else if (chest.isDrawerRightLock()) {
            Snackbar.make(mBackground, "it is lock.", Snackbar.LENGTH_SHORT).show();
        } else if (chest.isDrawerRightOpen()) {
            String cmd = chest.codeForDrawerOpen(CmdPool.RIGHT, false);
            UdpHelper.executeCommand(cmd);
            drawerRight.setImageResource(R.drawable.ic_drawer_right_2);
        } else {
            String cmd = chest.codeForDrawerOpen(CmdPool.RIGHT, true);
            UdpHelper.executeCommand(cmd);
            drawerRight.setImageResource(R.drawable.ic_drawer_right_1);
        }
    }

    /**
     * 对左柜子的点击事件
     */
    private void optionLeftChest() {
        if (chest.isChestLeftOpen()) {
            chest.codeForChestOpen(CmdPool.LEFT,false);
            chestLeft.setImageResource(R.drawable.ic_chest_left_1);
        } else {
            chest.codeForChestOpen(CmdPool.LEFT,true);
            chestLeft.setImageResource(R.drawable.ic_chest_left_2);
        }
    }

    /**
     * 对右柜子的点击事件
     */
    private void optionRightChest() {
        if (chest.isChestRightOpen()) {
            chest.codeForChestOpen(CmdPool.RIGHT,false);
            chestRight.setImageResource(R.drawable.ic_chest_right_1);
        } else {
            chest.codeForChestOpen(CmdPool.RIGHT,true);
            chestRight.setImageResource(R.drawable.ic_chest_right_2);
        }
    }
}
