package com.ww;

/**
 * Description:
 * Date: 2023/3/22
 * Author: weiwei
 */
public class Log {

    private static boolean DEBUG = BuildConfig.DEBUG;

    public static void D(String tag, String msg) {
        if (DEBUG) android.util.Log.d(tag, msg);
    }

    public static void E(String tag, String msg) {
        if (DEBUG) android.util.Log.e(tag, msg);
    }

}
