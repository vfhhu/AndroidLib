package xyz.vfhhu.lib.android;

import android.app.Activity;
import android.os.Looper;
import android.widget.Toast;

/**
 * Created by leo on 2017/7/19.
 */

public class MToast {
    private static Toast _toast;
    public static Toast getToast() {
        return _toast;
    }
    public static Toast setToast(Toast _toast) {
        MToast._toast = _toast;
        return MToast._toast;
    }
    public static void show(final Activity act, final CharSequence text,final int duration){
        if(Looper.getMainLooper().getThread() == Thread.currentThread()){
            if(_toast==null)Toast.makeText(act,text, duration).show();
            else _toast.show();
            return;
        }
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(_toast==null)Toast.makeText(act,text, duration).show();
                else _toast.show();

            }
        });
    }
    public static void show(final Activity act, final int resId, final int duration){
        if(Looper.getMainLooper().getThread() == Thread.currentThread()){
            if(_toast==null)Toast.makeText(act,resId, duration).show();
            else _toast.show();
            return;
        }
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(_toast==null)Toast.makeText(act,resId, duration).show();
                else _toast.show();
            }
        });
    }
}
