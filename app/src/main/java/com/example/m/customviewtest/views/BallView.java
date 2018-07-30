package com.example.m.customviewtest.views;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Date;

public class BallView extends View {
    private static final String TAG = "BallView";
    private static final String PROPERTY_X = "distance_x";
    private static final String PROPERTY_Y = "distance_y";
    private int width;
    private int height;
    private Paint paint;
    private int ballRadius = 50;
    private Path path = new Path();
    int x;
    int y;
    float startX;
    float startY;
    private boolean isChangePosition;
    private float stopY;
    private float stopX;
    private int previousX;
    private int previousY;
    private long previousTime;
    private int moveX;
    private int moveY;
    private long interval;
    private boolean disable;

    public BallView(Context context) {
        super(context);
    }

    public BallView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void init() {
        x = width / 2;
        y = height - ballRadius;
        stopY = 0;
        stopX = 0;
        moveX = 0;
        moveY = 0;
        disable=false;
        invalidate();
    }

    protected void animationBall() {
        paint = new Paint();
        paint.setColor(Color.GREEN);
        PropertyValuesHolder propertyDistanceX = PropertyValuesHolder.ofInt(PROPERTY_X, 0, 150);
        PropertyValuesHolder propertyDistanceY = PropertyValuesHolder.ofInt(PROPERTY_Y, 0, 150);
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setValues(propertyDistanceX, propertyDistanceY);
//        valueAnimator.setInterpolator(new BounceInterpolator());
//        valueAnimator.setInterpolator(new CycleInterpolator(1));
        valueAnimator.setDuration(3000);
        valueAnimator.addUpdateListener(animation -> {
//            stopX = (int) animation.getAnimatedValue(PROPERTY_X);
//            stopY = (int) animation.getAnimatedValue(PROPERTY_Y);
            if (!checkDistance() || (moveX == 0 && moveY == 0)) {
                endMove();
            } else {
                stopX += moveX;
                stopY += moveY;
                runBall();
                invalidate();
            }
         });
        valueAnimator.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        if (disable) {
            return false;
        }
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(event.getX(), event.getY());
                if (isContain(event)) {
                    startX = event.getX();
                    startY = event.getY();
                    previousTime = new Date().getTime();
                    isChangePosition = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(event.getX(), event.getY());
                changePosition(event);
                break;
            case MotionEvent.ACTION_UP:
                isChangePosition = false;
                disable = true;
                runBall();
//                animationBall();
                endMove();
                break;
        }
        invalidate();
        return true;
    }

    private void runBall() {
        postDelayed(() -> {
            if (!checkDistance() || (moveX == 0 && moveY == 0)) {
                endMove();
            } else {
                stopX += moveX;
                stopY += moveY;
                runBall();
                invalidate();
            }

        }, interval);
    }

    private boolean checkDistance() {
        if (x + (stopX + moveX) > width - ballRadius) {
            stopX = 0;
            x = width - ballRadius;
            moveX = moveX * -1;
        }
        if (x + (stopX + moveX) < ballRadius) {
            stopX = 0;
            x = ballRadius;
            moveX = moveX * -1;
        }
        if (y + (stopY + moveY) > height - ballRadius) {
            stopY = 0;
            y = height - ballRadius;
            moveY = moveY * -1;
        }
        if (y + (stopY + moveY) < ballRadius) {
            stopY = 0;
            y = ballRadius;
            moveY = moveY * -1;
        }

        return true;
    }

    private void endMove() {
        x = (int) (x + stopX);
        y = (int) (y + stopY);
        stopX = 0;
        stopY = 0;
    }

    private void changePosition(MotionEvent event) {
        if (isChangePosition) {
            moveX = (int) (event.getX() - previousX);
            moveY = (int) (event.getY() - previousY);
            interval = new Date().getTime() - previousTime;
            Log.i(TAG, "onTouchEvent moveX=" + moveX + " moveY=" + moveY + " interval=" + interval);

            previousTime = new Date().getTime();
            previousX = (int) event.getX();
            previousY = (int) event.getY();
            float vx = event.getX() - startX;
            if ((x + vx) < width - ballRadius && (x + vx) > ballRadius) {
                stopX = vx;
            }
            float vy = event.getY() - startY;
            if ((y + vy) < height - ballRadius && (y + vy) > ballRadius) {
                stopY = vy;
            }
        }
    }

    private boolean isContain(MotionEvent event) {
        return (x + ballRadius > event.getX() && x - ballRadius < event.getX() &&
                y + ballRadius > event.getY() && y - ballRadius < event.getY());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            width = getWidth();
            height = getHeight();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (x == 0) {
            x = width / 2;
            y = height - ballRadius;
        }

        paint = new Paint();
        paint.setColor(Color.RED);

        canvas.drawCircle(x + stopX, y + stopY, ballRadius, paint);

//        paint.setColor(Color.BLACK);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(7);
//        canvas.drawPath(path, paint);
    }
}
