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

public class BallView extends View {
    private static final String TAG = "BallView";
    private static final String PROPERTY_X = "distance_x";
    private static final String PROPERTY_Y = "distance_y";
    private int width;
    private int height;
    private Paint paint;
    private int ballRadius = 50;
    private Path path = new Path();
    int x = 0;
    int y = 0;
    float startX = 0;
    float startY = 0;
    private boolean isChangePosition;
    private float stopY;
    private float stopX;

    public BallView(Context context) {
        super(context);
    }

    public BallView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    protected void animationSquare() {
        paint = new Paint();
        paint.setColor(Color.GREEN);
        PropertyValuesHolder propertyDistanceX = PropertyValuesHolder.ofInt(PROPERTY_X, 0, 150);
        PropertyValuesHolder propertyDistanceY = PropertyValuesHolder.ofInt(PROPERTY_Y, 0, 150);
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setValues(propertyDistanceX, propertyDistanceY);
//        valueAnimator.setInterpolator(new BounceInterpolator());
//        valueAnimator.setInterpolator(new CycleInterpolator(1));
        valueAnimator.setDuration(3000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                stopX = (int) animation.getAnimatedValue(PROPERTY_X);
                stopY = (int) animation.getAnimatedValue(PROPERTY_Y);
                Log.i(TAG, "lengthMoveUp: ");
                invalidate();
            }
        });
        valueAnimator.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction() & MotionEvent.ACTION_MASK;

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(event.getX(), event.getY());
                if (isContain(event)) {
                    Log.i(TAG, "setMotionEvent: " + event.getY() + "  " + (height - ballRadius));
                    startX = event.getX();
                    startY = event.getY();
                    isChangePosition = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(event.getX(), event.getY());
                changePosition(event);
                break;
            case MotionEvent.ACTION_UP:
                isChangePosition = false;
                x = (int) (x + stopX);
                y = (int) (y + stopY);
                stopX = 0;
                stopY = 0;
                break;
        }
        invalidate();
        return true;
    }

    private void changePosition(MotionEvent event) {
        if (isChangePosition) {
            float vx = event.getX() - startX;
            if ((x + vx) < width - ballRadius && (x + vx) > ballRadius) {
                stopX = (int) (event.getX() - startX);
            }
            float vy = event.getY() - startY;
            if ((y + vy) < height - ballRadius && (y + vy) > ballRadius) {
                stopY = (int) (event.getY() - startY);
            }
            Log.i(TAG, "onTouchEvent: " + y + "  " + event.getY() + "  " + (int) (event.getY() - startY));
//                    invalidate();
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
        paint.setColor(Color.GREEN);

        canvas.drawCircle(x + stopX, y + stopY, ballRadius, paint);

//        paint.setColor(Color.BLACK);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(7);
//        canvas.drawPath(path, paint);
    }
}
