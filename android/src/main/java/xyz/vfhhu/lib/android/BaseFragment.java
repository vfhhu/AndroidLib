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
 * Created by leo3x on 2019/2/26.
 */

public class BaseFragment extends Fragment {
    public String TAG;
    private BaseActivity act_base;
    private OnFragmentInteractionListener mListener;
    private Toast _toast;
    public BaseFragment() {
        // Required empty public constructor
        TAG=getClass().getSimpleName();
    }

    public static BaseFragment newInstance() {
        BaseFragment fragment = new BaseFragment();
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
        act_base=(BaseActivity)context;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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
    public BaseActivity getActivityParent() {
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
