package com.baiheplayer.desk.chest.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baiheplayer.desk.R;
import com.baiheplayer.desk.chest.Chest;
import com.baiheplayer.desk.control.CmdPool;
import com.baiheplayer.desk.control.UdpHelper;
import com.baiheplayer.desk.drawer.Drawer;
import com.baiheplayer.desk.drawer.view.DrawerRightView;
import com.baiheplayer.desk.global.Constant;
import com.baiheplayer.desk.global.DeviceManager;
import com.baiheplayer.desk.global.Popup;
import com.baiheplayer.desk.global.fragment.BaseFragment;
import com.jungly.gridpasswordview.GridPasswordView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2017/3/21.
 */
@ContentView(R.layout.fragment_drawer_right)
public class Drawer_R extends BaseFragment {

    private static Drawer_R fragment;
    public Drawer_R(){}
    public static Drawer_R getInstance(){
        if(fragment == null){
            fragment = new Drawer_R();
        }
        return fragment;
    }

    private Chest chest;
    private boolean exeTime = false;
    private Context context;
    private View view;
    private Popup popup;
    private TextView popTitle;
    private GridPasswordView popPwd;
    private Button popBtn;
    @ViewInject(R.id.dlv_drawer)
    private DrawerRightView mRight;

    @Override
    public void getData(@Nullable Bundle savedInstanceState) {
        context = getContext();
    }

    @Override
    public void onView() {
        if (Constant.OPT_DEVICE_HOST == null) {
            mRight.setClickable(false);
            Snackbar.make(mRight, "没找到智能设备", Snackbar.LENGTH_SHORT).show();
        } else {
            mRight.setClickable(true);
            chest = (Chest) DeviceManager.getInstance().getDevice(Constant.OPT_DEVICE_HOST);
            refreshUI();
            chest.addCurrentState(new Drawer.ICurrentState() {
                //实时刷新状态
                @Override
                public void onState() {
                    refreshUI();
                }
            });
        }
        view = View.inflate(context,R.layout.pop_input_drawer_pwd,null);
        popTitle = (TextView) view.findViewById(R.id.pop_title);
        popPwd = (GridPasswordView)view.findViewById(R.id.gpv_password);
        popBtn = (Button) view.findViewById(R.id.btn_pwd_ensure);
        popup = new Popup(getActivity(),view);
        popBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cmd = chest.codeForDrawerUnlock(CmdPool.LEFT,popPwd.getPassWord().toString().trim());
                UdpHelper.executeCommand(cmd);
                mRight.exeCmd();
                popup.popDown();
            }
        });
    }

    /**
     * 初始界面
     */
    private void refreshUI() {
        Log.i("chen","初始状态");
        if (chest.alarm.equals(CmdPool.F) || chest.alarm.equals(CmdPool.F_)) {
            mRight.error();
        } else if (chest.isDrawerRightLock()){ // || drawer.lock_l.equals(CmdPool.F_)锁上的
            mRight.lock();
        } else if (chest.isDrawerRightOpen()) {
            mRight.open();
        } else {
            mRight.close();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("chen","view被回收");
        view = null;
    }

    /**
     * 单击选项
     * @param view
     */
    @Event(R.id.dlv_drawer)
    private void clickEvent(View view) {
        exeTime = true;
        if (chest.alarm.equals(CmdPool.F) || chest.alarm.equals(CmdPool.F_)) {    //已故障
            Snackbar.make(view, "抽屉出现故障", Snackbar.LENGTH_SHORT).show();
            exeTime =false;
            return;
        }
        if(!mRight.isRun()){
            if (chest.isDrawerRightLock()) {                               //如果已上锁
                popTitle.setText("请输入右边抽屉密码");
                popup.locateIn(view);
            } else if (chest.isDrawerRightOpen()) {                          //如果开着的，则单击关闭
                String cmd = chest.codeForDrawerOpen(CmdPool.RIGHT, false);
                UdpHelper.executeCommand(cmd);
                mRight.exeCmd();
            } else if (!chest.isDrawerRightOpen()) {                          //如果关着的，则单击打开
                String cmd = chest.codeForDrawerOpen(CmdPool.RIGHT, true);
                UdpHelper.executeCommand(cmd);
                mRight.exeCmd();
            }
        }
        exeTime = false;
    }
    /**
     * 长按事件
     */
    @Event(value = {R.id.dlv_drawer}, type = View.OnLongClickListener.class)
    private boolean running(View view) {
        exeTime = true;
        if (chest.alarm.equals(CmdPool.F) || chest.alarm.equals(CmdPool.F_)) {    //已故障
            Snackbar.make(view, "抽屉出现故障", Snackbar.LENGTH_SHORT).show();
            exeTime = false;
            return true;
        }
        if (chest.isDrawerRightLock()) {                               //如果已上锁
            Snackbar.make(view, "抽屉已上锁", Snackbar.LENGTH_SHORT).show();
        } else if (chest.isDrawerRightOpen()) {                          //如果开着的，提示先关
            Snackbar.make(view, "请先关上抽屉", Snackbar.LENGTH_SHORT).show();
        } else if (!chest.isDrawerRightOpen()) {                          //如果关着的，则单击锁定
            String cmd = chest.codeForDrawerLock(CmdPool.RIGHT);
            UdpHelper.executeCommand(cmd);
            mRight.longPress();
        }
        exeTime = false;
        return true;
    }
}
