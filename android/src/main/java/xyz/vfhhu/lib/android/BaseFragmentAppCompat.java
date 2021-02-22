package xyz.vfhhu.lib.android;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
//import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.orhanobut.logger.Logger;

/**
 * Created by leo3x on 2019/3/22.
 */

public class BaseFragmentAppCompat extends Fragment {
    public String TAG;
    private BaseActivityAppCompat act_base;
    private BaseFragment.OnFragmentInteractionListener mListener;
    private Toast _toast;
    public BaseFragmentAppCompat() {
        // Required empty public constructor
        TAG=getClass().getSimpleName();
    }

    public static BaseFragmentAppCompat newInstance() {
        BaseFragmentAppCompat fragment = new BaseFragmentAppCompat();
        return fragment;
    }
    public static BaseFragment newInstance(Bundle args) {
        BaseFragment fragment = new BaseFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        act_base=(BaseActivityAppCompat)context;
        if (context instanceof BaseFragment.OnFragmentInteractionListener) {
            mListener = (BaseFragment.OnFragmentInteractionListener) context;
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public Toast getToast() {
        return _toast;
    }

    public Toast setToast(Toast _toast) {
        this._toast = _toast;
        return this._toast;
    }

    //获取宿主Activity
    public BaseActivityAppCompat getActivityParent() {
        return act_base;
    }
    public void log(String s) {
        if(VfhhuLib.isDebug()) Log.d(TAG,s);
    }
    public void loggger(Object s) {
        if(VfhhuLib.isDebug()) Logger.d(s);
    }
    public void toast(final String s) {
        if(Looper.myLooper() == Looper.getMainLooper()) {
            if(_toast==null)Toast.makeText(act_base,s,Toast.LENGTH_LONG).show();
            else _toast.show();
        }else{
            act_base.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(_toast==null)Toast.makeText(act_base,s,Toast.LENGTH_LONG).show();
                    else _toast.show();
                }
            });
        }
    }
    public void toast(final int s) {
        toast(this.getResources().getString(s));
    }
}
