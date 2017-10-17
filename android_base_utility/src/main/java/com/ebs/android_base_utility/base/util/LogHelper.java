package com.ebs.android_base_utility.base.util;


import com.ebs.android_base_utility.BuildConfig;

/**
 * Created by barbaros.vasile on 9/22/2017.
 */

public class LogHelper {
    public static void log(String tag,Object object){
        if(BuildConfig.DEBUG) {
            System.out.println(tag + " : " + object);
        }
    }
}
