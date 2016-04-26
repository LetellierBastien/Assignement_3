package com.example.english.test;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by english on 20/04/2016.
 */
public class CustomView extends View {

    private int RecColor, labelCol;
    private String circleText;
    private Paint RecPaint;

    public CustomView(Context context , AttributeSet attrs) {
        super(context, attrs);


        RecPaint = new Paint();
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.CustomView, 0, 0);
        try {
            RecColor = a.getInteger(R.styleable.CustomView_circleColor, 0);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int viewWidth = this.getMeasuredWidth();
        int viewHeight = this.getMeasuredHeight();

        RecPaint.setStyle(Paint.Style.FILL);
        RecPaint.setAntiAlias(true);
        RecPaint.setColor(RecColor);
        canvas.drawRect(0, 0, viewWidth, viewHeight, RecPaint);

        RecPaint.setColor(Color.parseColor("#FFFFFF"));

        for (int i=1;i<7;i++)
            canvas.drawLine(0,i*viewHeight/7,viewWidth,i*viewHeight/7,RecPaint);



    }


}
