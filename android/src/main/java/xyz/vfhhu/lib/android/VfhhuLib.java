package xyz.vfhhu.lib.android;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by leo3x on 2018/11/22.
 */

public class VfhhuLib {
    private static boolean debug=false;


    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean debug) {
        VfhhuLib.debug = debug;
    }

    private static String getSpKey(Context ct){
        return ct.getPackageName()+"_"+BuildConfig.APPLICATION_ID;
    }

    public static void setSaveString(Context ct, String tag, String data){
//        synchronized (tag){
//            SharedPreferences sharedPref = ct.getSharedPreferences(getSpKey(ct), Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPref.edit();
//            editor.putString(tag, data);
//            editor.commit();
//        }
        setSaveString(ct, tag, data,false);
    }
    public static void setSaveString(Context ct, String tag, String data, boolean is_commit){
        SharedPreferences sharedPref = ct.getSharedPreferences(getSpKey(ct), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(tag, data);
        if(is_commit){
            editor.commit();
        }else{
            editor.apply();
        }
    }
    public static String getSaveString(Context ct, String tag, String defultV){
        SharedPreferences sharedPref = ct.getSharedPreferences(getSpKey(ct), Context.MODE_PRIVATE);
        return sharedPref.getString(tag, defultV);
    }


    public static void setSaveInt(Context ct, String tag, int data){
//        synchronized (tag) {
//            SharedPreferences sharedPref = ct.getSharedPreferences(getSpKey(ct), Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPref.edit();
//            editor.putInt(tag, data);
//            editor.commit();
//        }
        setSaveInt(ct, tag, data,false);
    }
    public static void setSaveInt(Context ct, String tag, int data, boolean is_commit){
        SharedPreferences sharedPref = ct.getSharedPreferences(getSpKey(ct), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(tag, data);
        if(is_commit){
            editor.commit();
        }else{
            editor.apply();
        }
    }
    public static int getSaveInt(Context ct, String tag, int defultV){
        SharedPreferences sharedPref = ct.getSharedPreferences(getSpKey(ct), Context.MODE_PRIVATE);
        return sharedPref.getInt(tag, defultV);
    }


    public static void setSaveLong(Context ct, String tag, long data){
//        synchronized (tag){
//            SharedPreferences sharedPref = ct.getSharedPreferences(getSpKey(ct), Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPref.edit();
//            editor.putLong(tag, data);
//            editor.commit();
//        }
        setSaveLong(ct, tag, data,false);
    }
    public static void setSaveLong(Context ct, String tag, long data, boolean is_commit){
        SharedPreferences sharedPref = ct.getSharedPreferences(getSpKey(ct), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(tag, data);
        if(is_commit){
            editor.commit();
        }else{
            editor.apply();
        }
    }
    public static long getSaveLong(Context ct, String tag, long defultV){
        SharedPreferences sharedPref = ct.getSharedPreferences(getSpKey(ct), Context.MODE_PRIVATE);
        return sharedPref.getLong(tag, defultV);
    }



    public static void setSaveFloat(Context ct, String tag, float data){
//        synchronized (tag){
//            SharedPreferences sharedPref = ct.getSharedPreferences(getSpKey(ct), Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPref.edit();
//            editor.putFloat(tag, data);
//            editor.commit();
//        }
        setSaveFloat(ct, tag, data,false);
    }
    public static void setSaveFloat(Context ct, String tag, float data, boolean is_commit){
        SharedPreferences sharedPref = ct.getSharedPreferences(getSpKey(ct), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putFloat(tag, data);
        if(is_commit){
            editor.commit();
        }else{
            editor.apply();
        }
    }
    public static float getSaveFloat(Context ct, String tag, float defultV){
        SharedPreferences sharedPref = ct.getSharedPreferences(getSpKey(ct), Context.MODE_PRIVATE);
        return sharedPref.getFloat(tag, defultV);
    }



    public static void setSaveBoolean(Context ct, String tag, boolean data){
//        synchronized (tag){
//            SharedPreferences sharedPref = ct.getSharedPreferences(getSpKey(ct), Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPref.edit();
//            editor.putBoolean(tag, data);
//            editor.commit();
//        }
        setSaveBoolean(ct, tag, data,false);
    }
    public static void setSaveBoolean(Context ct, String tag, boolean data, boolean is_commit){
        SharedPreferences sharedPref = ct.getSharedPreferences(getSpKey(ct), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(tag, data);
        if(is_commit){
            editor.commit();
        }else{
            editor.apply();
        }
    }
    public static boolean getSaveBoolean(Context ct, String tag, boolean defultV){
        SharedPreferences sharedPref = ct.getSharedPreferences(getSpKey(ct), Context.MODE_PRIVATE);
        return sharedPref.getBoolean(tag, defultV);
    }




    public  static void clear(Context ct){
        SharedPreferences preferences = ct.getSharedPreferences(getSpKey(ct), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();

    }

}
