package xyz.vfhhu.lib.android.utils;

/**
 * Created by leo3x on 2018/9/21.
 */

public abstract class CallBackUtil<T> {
    private String TAG;
    public CallBackUtil(String _TAG){TAG=_TAG;}
    public String getTAG() {
        return TAG;
    }

    public abstract void onData(T... data);
}
