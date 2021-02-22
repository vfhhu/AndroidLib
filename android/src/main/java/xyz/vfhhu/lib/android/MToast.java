package xyz.vfhhu.lib.android;

import android.app.Activity;
import android.os.Looper;
import android.widget.Toast;

/**
 * Created by leo on 2017/7/19.
 */

public class MToast {
    public static void showoast(final Activity act,final Toast _toast) {
        if(Looper.myLooper() == Looper.getMainLooper()) {
            _toast.show();
        }else{
            act.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    _toast.show();
                }
            });
        }
    }
    public static void show(final Activity act, final CharSequence text,final int duration){
        if(Looper.getMainLooper().getThread() == Thread.currentThread()){
            Toast.makeText(act,text, duration).show();
            return;
        }
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(act,text, duration).show();
            }
        });
    }
    public static void show(final Activity act, final int resId, final int duration){
        if(Looper.getMainLooper().getThread() == Thread.currentThread()){
            Toast.makeText(act,resId, duration).show();
            return;
        }
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(act,resId, duration).show();
            }
        });
    }
}
