package xyz.vfhhu.lib.android.http;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import xyz.vfhhu.lib.android.BaseActivity;
import xyz.vfhhu.lib.android.utils.CallBackUtil;

/**
 * Created by leo3x on 2018/10/15.
 */

public class OnHttpUtilsSuccess implements Callback {
    BaseActivity act;
    CallBackUtil<String> callback;
    public OnHttpUtilsSuccess(BaseActivity act,CallBackUtil<String> callback){
        this.act=act;
        this.callback=callback;
    }
    @Override
    public void onFailure(Call call, IOException e) {
        if(act!=null)act.toast(e.toString());
        if(callback!=null)callback.onData("0","");
    }

    @Override
    public void onResponse(Call call, Response response) {
        if(response.code()==200){
            if(callback!=null){
                try {
                    String msg = response.body().string();
                    callback.onData("1",msg);
                } catch (IOException e) {
                    e.printStackTrace();
                    if(act!=null)act.toast(e.toString());
                    callback.onData("0","");
                }
            }
        }else{
            if(act!=null)act.toast("http error code:"+response.code());
            if(callback!=null)callback.onData("0","");
        }
    }
}
