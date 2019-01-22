package xyz.vfhhu.lib.android;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.orhanobut.logger.Logger;

/**
 * Created by leo3x on 2019/1/11.
 */

public class BaseService extends Service {
    public String TAG;
    public Context ctx;
    public Service _service;
    public BaseService _service_base;
    private boolean isServiceRun=false;
    public Handler _handler_main;
    public Handler _handler;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        TAG=getClass().getSimpleName();
        _handler_main=new Handler(this.getMainLooper());
        _handler=new Handler();
        ctx=_service=_service_base=this;
        Logger.d("onCreate");
        super.onCreate();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        isServiceRun=true;
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onDestroy() {
        isServiceRun=false;
        super.onDestroy();
        _service=null;
        _service_base=null;
    }

    public void log(String s) {
        if(VfhhuLib.isDebug()) Log.d(TAG,s);
    }
    public void loggger(Object s) {
        if(VfhhuLib.isDebug()) Logger.d(s);
    }

    public boolean isServiceRun() {
        return isServiceRun;
    }
}
