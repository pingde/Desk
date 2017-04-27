package com.baiheplayer.desk.global;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.baiheplayer.desk.R;

/**
 * Created by Administrator on 2017/3/13.
 */

public class Popup implements PopupWindow.OnDismissListener{
    private Activity activity;
    private WindowManager.LayoutParams mParams;
    private PopupWindow popup;
    public Popup(Activity activity,View view){
        this.activity = activity;
        popup = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,true);
        popup.setOutsideTouchable(true);

        popup.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popup.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        ColorDrawable cd = new ColorDrawable();
        popup.setBackgroundDrawable(cd);
        popup.setAnimationStyle(R.style.dialog_anim);
        mParams = activity.getWindow().getAttributes();
        popup.setOnDismissListener(this);
    }

    public void locateIn(View v){
        mParams.alpha = 0.6f;
        activity.getWindow().setAttributes(mParams);
        popup.showAtLocation(v, Gravity.BOTTOM,0,0);
    }

    public void popDown(){
        popup.dismiss();
    }

    @Override
    public void onDismiss() {
        mParams.alpha = 1.0f;
        activity.getWindow().setAttributes(mParams);
    }
}
