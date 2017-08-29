package xyz.vfhhu.lib.android;


import android.util.Log;

/**
 * Created by leo on 2017/7/19.
 */

public class MLog {
    private static boolean SHOW_LOG = BuildConfig.DEBUG;

    public static boolean isShowLog() {
        return SHOW_LOG;
    }

    public static void setShowLog(boolean showLog) {
        SHOW_LOG = showLog;
    }

    private static String getClassName(Object obj) {
        return obj.getClass().getPackage().getName()+"."+obj.getClass().getSimpleName();
    }

    public static void v(Object obj, String msg) {
        if (SHOW_LOG) Log.v(getClassName(obj), msg);
    }

    public static void d(Object obj, String msg) {
        if (SHOW_LOG) Log.d(getClassName(obj), msg);
    }

    public static void i(Object obj, String msg) {
        if (SHOW_LOG) Log.i(getClassName(obj), msg);
    }

    public static void w(Object obj, String msg) {
        if (SHOW_LOG) Log.w(getClassName(obj), msg);
    }

    public static void e(Object obj, String msg) {
        if (SHOW_LOG) Log.e(getClassName(obj), msg);
    }

    public static void v(String tag, String msg) {
        if (SHOW_LOG) Log.v(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (SHOW_LOG) Log.d(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (SHOW_LOG) Log.i(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (SHOW_LOG) Log.w(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (SHOW_LOG) Log.e(tag, msg);
    }

    public static void v(Class<?> cls, String msg) {
        if (SHOW_LOG) Log.v(cls.getSimpleName(), msg);
    }

    public static void d(Class<?> cls, String msg) {
        if (SHOW_LOG) Log.d(cls.getSimpleName(), msg);
    }

    public static void i(Class<?> cls, String msg) {
        if (SHOW_LOG) Log.i(cls.getSimpleName(), msg);
    }

    public static void w(Class<?> cls, String msg) {
        if (SHOW_LOG) Log.w(cls.getSimpleName(), msg);
    }

    public static void e(Class<?> cls, String msg) {
        if (SHOW_LOG) Log.e(cls.getSimpleName(), msg);
    }
}
