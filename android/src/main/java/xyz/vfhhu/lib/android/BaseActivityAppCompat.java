package xyz.vfhhu.lib.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentTransaction;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.orhanobut.logger.Logger;

import xyz.vfhhu.lib.android.base_interface.ActivityResultCallback;
import xyz.vfhhu.lib.android.base_interface.CheckPermissionCallback;

/**
 * Created by leo3x on 2019/3/22.
 */

public class BaseActivityAppCompat extends AppCompatActivity {
    public String TAG;
    public Context ctx;
    public Activity act;
    public BaseActivityAppCompat act_base;
    private boolean active  = false;
    public Handler _handler_main;
    public Handler _handler;
    private ActivityResultCallback onActivityResultCallback;

    private int TAG_PERMISSION=9090;
    private boolean isCheckPerrmision=false;
    private CheckPermissionCallback checkPermissionCallback;
    private String[] _PERMISSIONS_REQUEST={};

    private Fragment _currFragment;
    private Toast _toast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = act = act_base = this;
        TAG=getClass().getSimpleName();
        _handler_main=new Handler(this.getMainLooper());
        _handler=new Handler();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (onActivityResultCallback != null) {
            onActivityResultCallback.onActivityResult(requestCode, resultCode, data);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        active =false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        active =true;
        if(isCheckPerrmision){
            if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
                verifyStoragePermissions();
            }else{
                if(checkPermissionCallback!=null)checkPermissionCallback.onBack(true);
            }
        }
    }

    public Toast getToast() {
        return _toast;
    }

    public Toast setToast(Toast _toast) {
        this._toast = _toast;
        return this._toast;
    }
    public void log(String s) {
        if(VfhhuLib.isDebug()) Log.d(TAG,s);
    }
    public void loggger(Object s) {
        if(VfhhuLib.isDebug()) Logger.d(s);
    }
    public void toast(final String s) {
        if(Looper.myLooper() == Looper.getMainLooper()) {
            if(_toast==null)Toast.makeText(ctx,s,Toast.LENGTH_LONG).show();
            else _toast.show();
        }else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(_toast==null)Toast.makeText(ctx,s,Toast.LENGTH_LONG).show();
                    else _toast.show();
                }
            });
        }
    }
    public void toast(final int s) {
        toast(this.getResources().getString(s));
    }
    public void setTextView(final TextView t, final String s) {
        if(t==null)return;
        if(Looper.myLooper() == Looper.getMainLooper()) {
            t.setText(s);
        }else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setTextView(t, s);
                }
            });
        }
    }
    public void setTextView(final EditText t, final int id) {
        if(t==null)return;
        setTextView(t, act.getResources().getString(id));
    }

    public void setTextView(final EditText t,final String s) {
        if(t==null)return;
        if(Looper.myLooper() == Looper.getMainLooper()) {
            t.setText(s);
        }else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setTextView(t, s);
                }
            });
        }
    }
    public void setTextView(final TextView t,final int id) {
        if(t==null)return;
        setTextView(t, act.getResources().getString(id));
    }

    public void showView(final View v) {
        if(v==null)return;
        if(Looper.myLooper() == Looper.getMainLooper()) {
            v.setVisibility(View.VISIBLE);
        }else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showView(v);
                }
            });
        }
    }

    public void hideView(final View v) {
        if(v==null)return;
        if(Looper.myLooper() == Looper.getMainLooper()) {
            v.setVisibility(View.GONE);
        }else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    hideView(v);
                }
            });
        }
    }
    public void showView(final View v,final long showtime) {
        if(v==null)return;
        if(Looper.myLooper() == Looper.getMainLooper()) {
            v.setVisibility(View.VISIBLE);
            if(showtime>0)
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideView(v);
                    }
                },showtime);
        }else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showView(v,showtime);
                }
            });
        }
    }
    public boolean isActive() {
        return active;
    }


    //=================start and result
    public void startAct(final Intent it){
        if(it==null)return;
        if(Looper.myLooper() == Looper.getMainLooper()) {
            startActivity(it);
        }else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    startAct(it);
                }
            });
        }
    }
    public void startActRet(final Intent it,final int code,final ActivityResultCallback callback){
        if(it==null)return;
        if(Looper.myLooper() == Looper.getMainLooper()) {
            onActivityResultCallback=callback;
            startActivityForResult(it,code);
        }else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    startActRet(it,code,callback);
                }
            });
        }
    }


    //=================Permission
    public int getPermissions() {
        int permission = PackageManager.PERMISSION_GRANTED;
        for(String permissions:_PERMISSIONS_REQUEST){
            if(ActivityCompat.checkSelfPermission(ctx, permissions)!= PackageManager.PERMISSION_GRANTED){
                permission=PackageManager.PERMISSION_DENIED;
                break;
            }
        }
        return permission;
    }
    public void verifyStoragePermissions() {
        // Check if we have write permission
        int permission = getPermissions();
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    act,
                    _PERMISSIONS_REQUEST,
                    TAG_PERMISSION
            );
        }else{
            if(checkPermissionCallback!=null)checkPermissionCallback.onBack(true);
        }
    }
    public void checkAllPermission(final String[] perrmisions,final CheckPermissionCallback callback){
        if(perrmisions.length>0){
            isCheckPerrmision=true;
            checkPermissionCallback=callback;
            _PERMISSIONS_REQUEST=perrmisions;
        }else{
            if(callback!=null)callback.onBack(true);
        }
    }


    //=================Fragment
    public void switchFragment(Fragment fragment, String tag,int container_viewid) {
        if (fragment != _currFragment) {
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            if(fragment.isAdded()){
                if(_currFragment!=null)fragmentTransaction=fragmentTransaction.hide(_currFragment);
                fragmentTransaction.show(fragment).commit();
            }else {
                if(_currFragment!=null)fragmentTransaction=fragmentTransaction.hide(_currFragment);
                fragmentTransaction.add(container_viewid, fragment, tag).commit();
            }
            _currFragment=fragment;
        }
    }
    public Fragment get_currFragment() {
        return _currFragment;
    }
}
