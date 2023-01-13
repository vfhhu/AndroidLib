package xyz.vfhhu.lib.android.utils;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by leo3x on 2018/11/26.
 */

public class AppStorage {
    private static String getSpKey(Context ct){
//        return ct.getPackageName()+"_"+ BuildConfig.APPLICATION_ID;
        return ct.getPackageName()+"_libvf";
    }
    public static JSONArray getSaveJSONArray(Context ct, String tag, JSONArray defultV){
        tag=tag+"_JSONArray";
        try {
            return new JSONArray(getSaveString( ct,  tag, "{}"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return defultV;
    }
    public static void setSaveJSONArray(Context ct, String tag, JSONArray j){
        tag=tag+"_JSONArray";
        setSaveString(ct, tag, j.toString());
    }
    public static JSONObject getSaveJSONObject(Context ct, String tag, JSONObject defultV){
        tag=tag+"_JSONObject";
        try {
            return new JSONObject(getSaveString( ct,  tag, "{}"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return defultV;
    }
    public static void setSaveJSONObject(Context ct, String tag, JSONObject j){
        tag=tag+"_JSONObject";
        setSaveString(ct, tag, j.toString());
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



    public  static void remove(Context ct,String tag){
        remove(ct,tag,false);
    }
    public  static void remove(Context ct,String tag,boolean is_commit){
        SharedPreferences preferences = ct.getSharedPreferences(getSpKey(ct), Context.MODE_PRIVATE);
        if(is_commit) preferences.edit().remove(tag).commit();
        else preferences.edit().remove(tag).apply();
    }
    public  static void clear(Context ct){
        clear(ct,false);
    }
    public  static void clear(Context ct,boolean is_commit){
        SharedPreferences preferences = ct.getSharedPreferences(getSpKey(ct), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        if(is_commit) editor.commit();
        else editor.apply();

    }
}
