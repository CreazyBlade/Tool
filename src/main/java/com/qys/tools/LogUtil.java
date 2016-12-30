package com.qys.tools;

import android.util.Log;

/**
 * Created by Yishuai on 2016/6/15.
 */
public class LogUtil {
    private static boolean isDebug=true;

    public static void setIsDebug(boolean isDebug) {
        LogUtil.isDebug = isDebug;
    }

    public static void i(String tag,String msg){
        if(isDebug)
            Log.i(tag,msg);
    }

    public static void d(String tag,String msg){
        if(isDebug)
            Log.d(tag,msg);
    }

    public static void e(String tag,String msg){
        if(isDebug)
            Log.e(tag,msg);
    }
}
