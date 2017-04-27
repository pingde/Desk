package com.baiheplayer.desk.util;

import android.graphics.Color;

import com.baiheplayer.desk.R;
import com.baiheplayer.desk.control.CmdPool;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/3/20.
 */

public class ColorUtil {

    private  static HashMap<String, Integer> hashMap;

    private static void init(){
        hashMap = new HashMap<>();
        hashMap.put(CmdPool.LV_NA,R.color.color_drawer_write);
        hashMap.put(CmdPool.LV_PI,R.color.color_drawer_pink);
        hashMap.put(CmdPool.LV_BL,R.color.color_drawer_blue);
        hashMap.put(CmdPool.LV_0,R.color.color_drawer_0);
        hashMap.put(CmdPool.LV_1,R.color.color_drawer_1);
        hashMap.put(CmdPool.LV_2,R.color.color_drawer_2);
        hashMap.put(CmdPool.LV_3,R.color.color_drawer_3);
        hashMap.put(CmdPool.LV_4,R.color.color_drawer_4);
        hashMap.put(CmdPool.LV_5,R.color.color_drawer_5);
        hashMap.put(CmdPool.LV_6,R.color.color_drawer_6);
        hashMap.put(CmdPool.LV_7,R.color.color_drawer_7);
        hashMap.put(CmdPool.LV_8,R.color.color_drawer_8);
        hashMap.put(CmdPool.LV_9,R.color.color_drawer_9);
        hashMap.put(CmdPool.LV_10,R.color.color_drawer_10);
        hashMap.put(CmdPool.LV_11,R.color.color_drawer_11);
        hashMap.put(CmdPool.LV_12,R.color.color_drawer_12);
        hashMap.put(CmdPool.LV_13,R.color.color_drawer_13);
        hashMap.put(CmdPool.LV_14,R.color.color_drawer_14);
        hashMap.put(CmdPool.LV_15,R.color.color_drawer_15);
    }

    public static int getColorRes(String cmd) {
        if(hashMap == null){
            init();
        }
        if(hashMap.containsKey(cmd)){
            return hashMap.get(cmd);
        }
        return R.color.color_drawer_write;
    }

    public static int getColorCmd(String cmd) {
        if(hashMap == null){
            init();
        }
        if(hashMap.containsKey(cmd)){
            return hashMap.get(cmd);
        }
        return R.color.color_drawer_write;

    }


}
