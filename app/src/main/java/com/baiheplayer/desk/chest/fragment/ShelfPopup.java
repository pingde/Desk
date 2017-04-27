package com.baiheplayer.desk.chest.fragment;

import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.baiheplayer.desk.R;

/**
 * 书架的管理弹窗
 * Created by Administrator on 2017/3/22.
 */

public class ShelfPopup {
    PopupWindow pop;
    View view;

    public ShelfPopup(View v){
        view = v;
        pop = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,true);
        ColorDrawable cd = new ColorDrawable();
        pop.setBackgroundDrawable(cd);
        pop.setOutsideTouchable(true);
    }


    public void showIn(View v){
        pop.showAtLocation(view,Gravity.NO_GRAVITY,(int)(v.getX()*0.725),(int)(v.getY()+v.getHeight()*2.3));
    }

    public void dismiss(){
        pop.dismiss();
    }

    public void dispear(){
        view = null;
    }
}
