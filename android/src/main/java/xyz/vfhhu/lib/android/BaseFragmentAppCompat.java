package xyz.vfhhu.lib.android;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by leo3x on 2019/3/22.
 */

public class BaseFragmentAppCompat extends Fragment {
    private BaseActivityAppCompat act_base;
    private BaseFragment.OnFragmentInteractionListener mListener;
    public BaseFragmentAppCompat() {
        // Required empty public constructor
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

    //获取宿主Activity
    protected BaseActivityAppCompat getActivityParent() {
        return act_base;
    }
}
