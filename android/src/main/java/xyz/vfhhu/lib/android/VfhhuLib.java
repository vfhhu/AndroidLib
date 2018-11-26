package xyz.vfhhu.lib.android;

import android.content.Context;
import android.content.SharedPreferences;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * Created by leo3x on 2018/11/22.
 */

public class VfhhuLib {
    private static boolean debug=false;
    private static VfhhuLib _instance;
    private VfhhuLib(){
        Logger.clearLogAdapters();
        Logger.addLogAdapter(new AndroidLogAdapter());
    }
    public static VfhhuLib init(){
        if(_instance==null)_instance=new VfhhuLib();
        return _instance;
    }

    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean debug) {
        VfhhuLib.debug = debug;
    }


}
