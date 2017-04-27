package com.baiheplayer.desk.drawer.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.baiheplayer.desk.R;

/**
 * Created by Administrator on 2017/3/28.
 */

public class RoundCircleView extends View {

    private Paint paint;
    private int roundColor;
    private int roundRadius;
    private int circleColor;
    private int circleRadius;
    private int centre;
    int ROUND_RADIUS_MAX;
    int CIRCLE_RADIUS_MAX;
    int progress = 0;
    boolean longPress = true;
    private boolean isFirstDraw = true;
    private final int OFF_COLOR = getResources().getColor(R.color.drawer_off);
    private final int ON_COLOR = getResources().getColor(R.color.drawer_on);
    private final int ERROR_COLOR_CIRCLE = getResources().getColor(R.color.drawer_error);
    private final int ERROR_COLOR_ROUND = getResources().getColor(R.color.drawer_error2);

    public RoundCircleView(Context context) {
        super(context, null);
    }

    public RoundCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RoundCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);

    }

    private void init(Context context, @Nullable AttributeSet attrs) {
//        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundCircleView);
//        roundColor = mTypedArray.getColor(R.styleable.RoundCircleView_round_color, Color.GRAY);
//        roundRadius = (int) mTypedArray.getDimension(R.styleable.RoundCircleView_round_radius,ROUND_RADIUS);
//        circleColor = mTypedArray.getColor(R.styleable.RoundCircleView_circle_color,Color.GRAY);
//        circleRadius = (int)mTypedArray.getDimension(R.styleable.RoundCircleView_circle_radius,CIRCLE_RADIUS);
//        mTypedArray.recycle();
        paint = new Paint();
        paint.setAntiAlias(true);
        roundColor = OFF_COLOR;
        circleColor = OFF_COLOR;
//        circleRadius = 216;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        centre = getWidth() / 2;
        ROUND_RADIUS_MAX = (int) (centre * 0.85);
        CIRCLE_RADIUS_MAX = (int) (centre * 0.78);

        if (isFirstDraw) {
            roundRadius = ROUND_RADIUS_MAX;
            circleRadius = CIRCLE_RADIUS_MAX;
            isFirstDraw = false;
        }

        paint.setColor(roundColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        canvas.drawCircle(centre, centre, roundRadius, paint);   //绘制外圆环
        if(longPress){
            paint.setColor(Color.GRAY);
            paint.setStrokeWidth(10);  //
            RectF oval = new RectF(centre - roundRadius, centre - roundRadius, centre + roundRadius, centre + roundRadius);
            canvas.drawArc(oval, -90, 360 * progress / 1000, false, paint);
        }

        if(circleRadius>10){
            paint.setColor(circleColor);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(centre, centre, circleRadius, paint);//绘制中心圆
        }


    }

    public int getCircleRadius() {
        return circleRadius;
    }

    public void setCircleRadius(int num) {
        if (num < CIRCLE_RADIUS_MAX && num >= 0) {
            circleRadius = num;
        }
        if (num < 0) {
            circleRadius = 0;
        }
        if (num > CIRCLE_RADIUS_MAX) {
            circleRadius = CIRCLE_RADIUS_MAX;
        }
        postInvalidate();
    }

    public void setColor(int num){
        if(num==1){
            roundColor = OFF_COLOR;
            circleColor = OFF_COLOR;
        }
        if(num==2){
            roundColor = ON_COLOR;
            circleColor = ON_COLOR;
        }
        if(num==3){
            roundColor = OFF_COLOR;
            circleColor = OFF_COLOR;
        }
        if(num==4){
            roundColor = ERROR_COLOR_ROUND;
            circleColor = ERROR_COLOR_CIRCLE;
        }
        postInvalidate();
    }

    public void setProgress(int pro){
        if(pro>=1000){
            progress = 1000;
        }
        if(pro<=0){
            progress = 0;
        }
        progress = pro;
        postInvalidate();
    }

}
