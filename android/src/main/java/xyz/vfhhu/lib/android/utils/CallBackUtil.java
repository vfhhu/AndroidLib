package xyz.vfhhu.lib.android.utils;

import java.io.UnsupportedEncodingException;

/**
 * Created by leo3x on 2018/9/21.
 */

public abstract class CallBackUtil<T> {
    private String TAG="";
    public CallBackUtil(){}
    public CallBackUtil(String _TAG){TAG=_TAG;}
    public CallBackUtil(byte[] _TAG){
        try {
            TAG=new String(_TAG,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    public String getTAG() {
        return TAG;
    }

    public abstract void onData(T... data);
}
