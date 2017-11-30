package com.xinshen.attendancesystem.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

/**
 * Created by thinkpad on 2017/10/29.
 */

public class FrameView extends View {


    private Paint mPaint;
    public FrameView(Context context) {
        super(context);
    }

    public FrameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private List<Rect> rectList;
    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(6);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (rectList !=null && rectList.size()>0){
            for (Rect rect : rectList){
                canvas.drawRect(rect,mPaint);
            }
        }
    }
    public void setRectList(List<Rect> rectList) {
        this.rectList = rectList;
        invalidate();
    }

}
