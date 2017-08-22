package com.ebs.android_base_utility.base.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by Vasile-barbaros-Pc on 9/15/2016.
 */
public class Button extends RelativeLayout {
    private boolean isEnabled = true;

    public Button(Context context) {
        super(context);
        init();
    }
    public Button(Context context, AttributeSet attrs){
        super(context,attrs);
        init();
    }
    public Button(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        init();
    }

    void init(){
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        setIsEnabled(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e){
        if(isEnabled) {
            if (e.getAction() == MotionEvent.ACTION_DOWN) {
                for (int i = 0; i < this.getChildCount(); i++) {
                    getChildAt(i).setAlpha(0.65f);
                }
                this.setAlpha(0.65f);
            } else if (e.getAction() == MotionEvent.ACTION_UP) {
                for (int i = 0; i < this.getChildCount(); i++) {
                    getChildAt(i).setAlpha(1f);
                }
                this.setAlpha(1);
            } else {
                for (int i = 0; i < this.getChildCount(); i++) {
                    getChildAt(i).setAlpha(1f);
                }
                this.setAlpha(1);
            }
        }

        return super.onTouchEvent(e);

    }

    public void setIsEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l);
    }
}