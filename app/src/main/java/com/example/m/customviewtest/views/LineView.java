package com.example.m.customviewtest.views;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.m.customviewtest.R;

public class LineView extends View {

    private static final String TAG = "PageIndicatorView";

    private static final String PROPERTY_LINE = "line";
    private static final String PROPERTY_RADIUS = "radius";
    private static final String PROPERTY_ROTATE = "rotate";
    private Paint paint;
    private int radius = 50, rotate = 0;

    private int lengthMoveUp = 0;
    private int lengthMoveRight = 0;
    private int lengthMoveWord = 0;

    private int lineLengthUp = 450;
    private int lineLengthRight = 450;
    private int lineLengthWord = 150;

    private int x = 0;
    private int y = 0;

    private boolean isLineUpDone;
    private boolean isLineRiteDone;

    public LineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.LineView);
        int count = typedArray.getInt(R.styleable.LineView_priv_count, 0);
        typedArray.recycle();

        init();
    }

    public void init() {
        isLineUpDone = false;
        isLineRiteDone = false;
        lengthMoveUp = 0;
        lengthMoveRight = 0;
        animationSquare();
        paint = new Paint();
        paint.setColor(Color.GREEN);
        PropertyValuesHolder propertyLine = PropertyValuesHolder.ofInt(PROPERTY_LINE, 0, lineLengthUp);
        // ValueAnimator valueAnimator = ValueAnimator.ofInt(5,150);
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setValues( propertyLine);
//        valueAnimator.setInterpolator(new BounceInterpolator());
//        valueAnimator.setInterpolator(new CycleInterpolator(1));
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(animation -> {
            lengthMoveUp = (int) animation.getAnimatedValue(PROPERTY_LINE);
            Log.i(TAG, "lengthMoveUp: " + lengthMoveUp);
            invalidate();
        });
        valueAnimator.start();
    }
    protected void animationSquare() {
          paint = new Paint();
        paint.setColor(Color.GREEN);
        PropertyValuesHolder propertyRadius = PropertyValuesHolder.ofInt(PROPERTY_RADIUS, 0, 150);
        PropertyValuesHolder propertyRotate = PropertyValuesHolder.ofInt(PROPERTY_ROTATE, 0, 360);
        // ValueAnimator valueAnimator = ValueAnimator.ofInt(5,150);
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setValues(propertyRadius, propertyRotate);
//        valueAnimator.setInterpolator(new BounceInterpolator());
//        valueAnimator.setInterpolator(new CycleInterpolator(1));
        valueAnimator.setDuration(3000);
        valueAnimator.addUpdateListener(animation -> {
            radius = (int) animation.getAnimatedValue(PROPERTY_RADIUS);
            rotate = (int) animation.getAnimatedValue(PROPERTY_ROTATE);
            Log.i(TAG, "lengthMoveUp: " + lengthMoveUp);
            invalidate();
        });
        valueAnimator.start();
    }
    protected void animationToRight() {
        PropertyValuesHolder propertyLine = PropertyValuesHolder.ofInt(PROPERTY_LINE, 0, lineLengthRight);
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setValues(propertyLine);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(animation -> {
            lengthMoveRight = (int) animation.getAnimatedValue(PROPERTY_LINE);
            Log.i(TAG, "lengthMoveRight: " + lengthMoveRight);
            invalidate();
        });
        valueAnimator.start();
    }

    protected void animationDrawWord() {
        PropertyValuesHolder propertyLine = PropertyValuesHolder.ofInt(PROPERTY_LINE, 0, lineLengthWord);
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setValues(propertyLine);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(animation -> {
            lengthMoveWord = (int) animation.getAnimatedValue(PROPERTY_LINE);
            invalidate();
        });
        valueAnimator.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint = new Paint();
        paint.setColor(Color.RED);

        Paint paintText = new Paint();
        paintText.setColor(Color.BLACK);
        paintText.setTextSize(50);

        int viewWidth = getWidth() / 2;
        int viewHeight = getHeight() / 2;
        int leftTopX = viewWidth - 150;
        int leftTopY = viewHeight - 150;
        int rightBotX = viewWidth + 150;
        int rightBotY = viewHeight + 150;

//        canvas.rotate(rotate, viewWidth, viewHeight);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        canvas.drawRoundRect(leftTopX, leftTopY + 200, rightBotX, rightBotY + 200, radius, Math.abs(radius-100), paint);
//        canvas.drawText("cvzdcvx", leftTopX, rightBotY + 40, paintText);

        drawLineUp(canvas);
    }


    private void drawLineUp(Canvas canvas) {
        if (x == 0) {
            x = getWidth() / 2 - 350;
            y = getHeight() / 2 + 350;
        }

        if (y - lineLengthUp == y - lengthMoveUp && !isLineUpDone) {
            animationToRight();
            isLineUpDone = true;
            invalidate();
        }

        paint = new Paint();
        paint.setColor(Color.MAGENTA);
        paint.setStrokeWidth(10);

        canvas.drawCircle(x, y, 15, paint);

        canvas.drawLine(x, y, x, y - lengthMoveUp, paint);

        if (isLineUpDone) {
            canvas.drawLine(x - 5, y - lengthMoveUp, x + lengthMoveRight, y - lengthMoveUp, paint);
            if (x + lineLengthRight == x + lengthMoveRight && !isLineRiteDone) {
                isLineRiteDone = true;
                animationDrawWord();
            }
        }

        if (isLineRiteDone) {
            paint.setTextSize(lengthMoveWord);
            canvas.drawText("hello", x, y - lengthMoveUp - 40, paint);
        }
    }


}
