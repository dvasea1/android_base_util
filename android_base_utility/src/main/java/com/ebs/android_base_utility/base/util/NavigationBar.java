package com.ebs.android_base_utility.base.util;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by barbaros.vasile on 11/3/2017.
 */

public class NavigationBar extends LinearLayout {
    public NavigationBar(Context context) {
        super(context);
        init();
    }

    public NavigationBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NavigationBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setOrientation(LinearLayout.VERTICAL);
    }
}
