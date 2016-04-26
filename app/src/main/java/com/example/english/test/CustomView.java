package com.example.english.test;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.location.Location;
import android.os.Debug;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Timer;

/**
 * Created by english on 20/04/2016.
 */
public class CustomView extends View {

    private int RecColor, labelCol;
    private String circleText;
    private Paint RecPaint;
    private ArrayList<Location> locaTab;
    private int count=0;

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

    public void initLocaTab (ArrayList<Location> tab)
    {
        locaTab = tab;
        invalidate();
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

        if (locaTab != null)
        {
            RecPaint.setColor(Color.parseColor("#00FF00"));
            float lastSpeed =0;

            for(int i =0;i<locaTab.size();i++)
            {
                canvas.drawLine((i-1)*viewWidth/100,viewHeight-lastSpeed*viewHeight/60,i*viewWidth/100,viewHeight-locaTab.get(i).getSpeed()*3.6f*viewHeight/60,RecPaint);
                lastSpeed = locaTab.get(i).getSpeed()*3.6f;
            }
        }


    }


}
