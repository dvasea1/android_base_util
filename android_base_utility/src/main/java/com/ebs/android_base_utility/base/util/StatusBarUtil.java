package com.ebs.android_base_utility.base.util;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by barbaros.vasile on 2/28/2017.
 */

public class StatusBarUtil {

    public static void addStatus(FragmentActivity activity, LinearLayout navBar){
        if(navBar!=null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                RelativeLayout status = new RelativeLayout(activity);
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(getStatusBarHeight(activity), getStatusBarHeight(activity));
                status.setLayoutParams(layoutParams);
                navBar.addView(status, 0);
            }
        }
    }

    public static void setStatusTintColor(FragmentActivity activity){
        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setTintColor(Color.TRANSPARENT);
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = context.getResources().getDimensionPixelSize(resourceId);
            }
        }
        return result;
    }
}
