package com.baiheplayer.desk.drawer.view;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.baiheplayer.desk.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/3/13.
 */

public class DrawerRightView extends RelativeLayout implements Behave{

    boolean isRun = false;
    int pro = 0;    //进度条0-1000；
    private static int trend; //趋势

    private RoundCircleView mRound;
    private ImageView mImage;
    ExecutorService executor;
    ObjectAnimator animator;



    public DrawerRightView(Context context) {
        super(context);
        init(context);
    }

    public DrawerRightView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DrawerRightView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        View view = View.inflate(context, R.layout.widget_combo, null);
        mRound = (RoundCircleView) view.findViewById(R.id.rcv_define);
        mImage = (ImageView) view.findViewById(R.id.img_state);
        this.addView(view);
        executor = Executors.newSingleThreadExecutor();
        animator = ObjectAnimator.ofFloat(mImage, "rotation", 0, 360);
        animator.setDuration(1000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
    }

    @Override
    public void changSize(final boolean isUp) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Log.i("chen", "开始执行");
                int size = mRound.getCircleRadius();
                while (size >= 0 && size <= mRound.CIRCLE_RADIUS_MAX) {
                    mRound.setCircleRadius(size);
                    try {
                        Thread.sleep(25);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (isUp) {
                        size = size + 10;
                    } else {
                        size = size - 10;
                    }
                }
            }
        });
    }

    @Override
    public void close() {
        animator.end();
        mRound.setColor(1);
        changSize(true);
        mImage.setImageResource(R.mipmap.pic_left_1);
        isRun = false;
    }

    @Override
    public void open() {
        animator.end();
        mRound.setColor(2);
        changSize(true);
        mImage.setImageResource(R.mipmap.pic_left_2);
        isRun = false;
    }

    @Override
    public void lock() {
        animator.end();
        mRound.setColor(3);
        changSize(true);
        mImage.setImageResource(R.mipmap.pic_left_3);
        isRun = false;
    }

    @Override
    public void error() {
        animator.end();
        mRound.setColor(4);
        changSize(true);
        mImage.setImageResource(R.mipmap.pic_left_4);
        isRun = false;
    }

    @Override
    public void exeCmd() {
        isRun = true;
        changSize(false);
        mImage.setImageResource(R.mipmap.pic_left_0);
        animator.start();
    }

    @Override
    public void cancel() {

    }

    @Override
    public boolean isRun() {
        return isRun;
    }

    public void longPress() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mRound.longPress = true;
                while (pro <= 1000) {
                    mRound.setProgress(pro);
                    try {
                        Thread.sleep(25);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    pro += 40;
                }
                pro=0;
                mRound.longPress = false;
                new Handler(Looper.getMainLooper()){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        exeCmd();
                    }
                }.sendEmptyMessageDelayed(100,500);
            }
        });
    }





}
