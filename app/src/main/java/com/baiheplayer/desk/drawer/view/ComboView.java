package com.baiheplayer.desk.drawer.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.baiheplayer.desk.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 将RoundCircleView和一张图片组合而成
 * 样品而已，实际使用DrawerLeftView 和 DrawerRightView
 * Created by Administrator on 2017/3/28.
 */

public class ComboView extends RelativeLayout {

    private RoundCircleView mRound;
    private ImageView mImage;
    ExecutorService executor;

    public ComboView(Context context) {
        super(context);
        init(context);
    }

    public ComboView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ComboView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View view = View.inflate(context, R.layout.widget_combo, null);
        mRound = (RoundCircleView) view.findViewById(R.id.rcv_define);
        mImage = (ImageView) view.findViewById(R.id.img_state);
        this.addView(view);
        executor = Executors.newSingleThreadExecutor();
    }

    public void changSize(final boolean isUp) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Log.i("chen","开始执行");
                int size = mRound.getCircleRadius();
                while (size >= 0 && size<=mRound.CIRCLE_RADIUS_MAX) {
                    mRound.setCircleRadius(size);
                    try {
                        Thread.sleep(25);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(isUp ){
                        size = size+10;
                    } else {
                        size = size-10;
                    }
                    if(size<20){

                    }
                }
            }
        });
    }

    public void clock(){
        mRound.setColor(1);
        mImage.setImageResource(R.mipmap.pic_left_1);
    }
    public void open(){
        mRound.setColor(2);
        mImage.setImageResource(R.mipmap.pic_left_2);
    }

    public void lock(){
        mRound.setColor(3);
        mImage.setImageResource(R.mipmap.pic_left_3);
    }
    public void error(){
        mRound.setColor(4);
        mImage.setImageResource(R.mipmap.pic_left_4);
    }



}
