package com.xinshen.attendancesystem.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

/**
 * Created by thinkpad on 2017/12/4.
 */

public class AlertView extends android.support.v7.widget.AppCompatTextView {

    public AlertView(Context context) {
        this(context,null);
    }

    public AlertView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AlertView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setTextSize(26);
        setGravity(Gravity.CENTER);
        setTextColor(Color.WHITE);
        setBackgroundColor(Color.BLACK);
        ValueAnimator animator = ValueAnimator.ofInt(5,0);
        animator.setDuration(5000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int curVal = (int) animation.getAnimatedValue();
                setText("请对准摄像头\n距离采集人脸信息还有\n\n"+curVal+"S");
                if (curVal == 0){
                    setVisibility(GONE);
                }
            }
        });
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }

}
