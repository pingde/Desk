package com.baiheplayer.desk.drawer.fragment;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.baiheplayer.desk.R;
import com.baiheplayer.desk.control.CmdPool;
import com.baiheplayer.desk.control.UdpHelper;
import com.baiheplayer.desk.drawer.Drawer;
import com.baiheplayer.desk.global.Constant;
import com.baiheplayer.desk.global.DeviceManager;
import com.baiheplayer.desk.global.fragment.BaseFragment;
import com.baiheplayer.desk.util.ColorUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽屉桌灯带
 * Created by Administrator on 2017/3/10.
 */
@ContentView(R.layout.fragment_light)
public class Drawer2Fragment extends BaseFragment {
    private static String TAG = "Drawer1Fragment";
    private static Drawer2Fragment fragment;
    private Context context;

    public Drawer2Fragment() {

    }

    public static Drawer2Fragment getInstance() {
        if (fragment == null) {
            fragment = new Drawer2Fragment();
        }
        return fragment;
    }

    @ViewInject(R.id.ll_light_background)
    private RelativeLayout mBackground;
    @ViewInject(R.id.rl_more_color)
    private RelativeLayout mMoreColor;
    @ViewInject(R.id.cb_switch)
    private CheckBox mSwitch;
    @ViewInject(R.id.cb_switch_write)
    private CheckBox mNature;
    @ViewInject(R.id.cb_switch_pink)
    private CheckBox mPink;
    @ViewInject(R.id.cb_switch_blue)
    private CheckBox mBlue;
    @ViewInject(R.id.ib_switch_more)
    private CheckBox mMore;
    private Drawable lastColor;
    private List<CheckBox> checkBoxes;
    private Drawer drawer;


    @Override
    public void getData(@Nullable Bundle savedInstanceState) {
        context = getContext();
        checkBoxes = new ArrayList<>();
    }

    @Override
    public void onView() {
        checkBoxes.add(mSwitch);
        checkBoxes.add(mNature);
        checkBoxes.add(mPink);
        checkBoxes.add(mBlue);
        checkBoxes.add(mMore);
        UdpHelper.getExepool();
        if (Constant.OPT_DEVICE_HOST != null) {
            drawer = (Drawer) DeviceManager.getInstance().getDevice(Constant.OPT_DEVICE_HOST);
            refreshUI();
            drawer.addCurrentState(new Drawer.ICurrentState() {
                @Override
                public void onState() {
                    refreshUI();
                }
            });
        }
        initPop();
    }

    PopupWindow popup ;
    private void initPop(){
        View gridLayout = View.inflate(context,R.layout.part_drawer_more_color,null);
        popup = new PopupWindow(gridLayout, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT,true);
        ColorDrawable cd = new ColorDrawable();
        popup.setBackgroundDrawable(cd);

    }



    @Event(value = {R.id.cb_switch, R.id.cb_switch_write, R.id.cb_switch_pink, R.id.cb_switch_blue, R.id.ib_switch_more})
    private void chooselight(View view) {

        if (mMoreColor.getVisibility() == View.VISIBLE) {

            mMoreColor.setVisibility(View.INVISIBLE);
            return;
        }

        switch (view.getId()) {

            case R.id.cb_switch:
                if (mSwitch.isChecked()) {
                    Log.i("chen","checked");
                    displayByAlpha(true);
                    mBackground.setVisibility(View.VISIBLE);
                    openLight(true, drawer.level);
                } else {
                    Log.i("chen","unChecked");
                    displayByAlpha(false);
                    mBackground.setVisibility(View.GONE);
                    mMoreColor.setVisibility(View.INVISIBLE);
                    openLight(false, drawer.level);
                }
                break;
            case R.id.cb_switch_write:
                switchColor(1);
                openLight(true, CmdPool.LV_NA);
                mBackground.setBackgroundResource(ColorUtil.getColorRes(CmdPool.LV_NA));
                break;
            case R.id.cb_switch_pink:
                switchColor(2);
                openLight(true, CmdPool.LV_PI);
                mBackground.setBackgroundResource(ColorUtil.getColorRes(CmdPool.LV_PI));
                break;
            case R.id.cb_switch_blue:
                switchColor(3);
                openLight(true, CmdPool.LV_BL);
                mBackground.setBackgroundResource(ColorUtil.getColorRes(CmdPool.LV_BL));
                break;
            case R.id.ib_switch_more:
                popup.showAtLocation(mSwitch, Gravity.CENTER,0,0);
//                if (mMoreColor.getVisibility() == View.VISIBLE){
//                    mMoreColor.setVisibility(View.INVISIBLE);
//                } else {
//                    mMoreColor.setVisibility(View.VISIBLE);
//                }
                break;
        }
    }

    //实时显示模块的状态
    private void refreshUI() {
        if (drawer.isLightenOpen()) {
            mSwitch.setChecked(true);
            mBackground.setVisibility(View.VISIBLE);
            displayByAlpha(true);
            if (CmdPool.LV_NA.equals(drawer.level)) {
                switchColor(1);
            }
            if (CmdPool.LV_BL.equals(drawer.level)) {
                switchColor(2);
            }
            if (CmdPool.LV_PI.equals(drawer.level)) {
                switchColor(3);
            }
            mBackground.setBackgroundResource(ColorUtil.getColorRes(drawer.level)); //按状态码找颜色值
        } else {
            mSwitch.setChecked(false);
            mBackground.setVisibility(View.GONE);
            displayByAlpha(false);
        }

    }

    private void switchColor(int num) {

        for (int i = 1; i < checkBoxes.size(); i++) {
            if (i == num) {
                checkBoxes.get(i).setChecked(true);
            } else {
                checkBoxes.get(i).setChecked(false);
            }
        }

    }

    private void displayByAnim(boolean able) {
        displayByAlpha(able);
        for (int i = 1; i < checkBoxes.size(); i++) {
            ObjectAnimator oa = null;
            CheckBox cb = checkBoxes.get(i);
            if (able) {
                oa = ObjectAnimator.ofFloat(cb, "alpha", 0.0F, 1.0F);
            } else {
                oa = ObjectAnimator.ofFloat(cb, "alpha", 1.0F, 0.0F);
            }
            oa.setDuration(200);
            oa.start();
        }
    }

    private void openLight(boolean isOpen, String level) {
        String cmd = drawer.codeForLight(isOpen, level);
        UdpHelper.executeCommand(cmd);
    }

    private void displayByAlpha(boolean able) {
        int a = able ? View.VISIBLE : View.GONE;
        mNature.setVisibility(a);
        mPink.setVisibility(a);
        mBlue.setVisibility(a);
        mMore.setVisibility(a);
    }

}
