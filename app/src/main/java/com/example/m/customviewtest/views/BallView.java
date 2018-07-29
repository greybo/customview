package com.example.m.customviewtest.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class BallView extends View {
    private int width;
    private int height;
    private Paint paint;
    private int ballRadius = 50;

    public BallView(Context context) {
        super(context);
    }

    public BallView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = getWidth();
        height = getHeight();

        int x = width / 2;
        int y = height - ballRadius;
        paint = new Paint();
        paint.setColor(Color.GREEN);
//        paint.setStrokeWidth(10);

        canvas.drawCircle(x, y, ballRadius, paint);

    }
}
