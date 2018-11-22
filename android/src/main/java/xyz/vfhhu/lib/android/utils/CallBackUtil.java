package xyz.vfhhu.lib.android.utils;

/**
 * Created by leo3x on 2018/9/21.
 */

public abstract class CallBackUtil<T> {
    private T TAG;
    public CallBackUtil(T _TAG){TAG=_TAG;}

    public T getTAG() {
        return TAG;
    }

    public abstract void onData(T... data);
}
