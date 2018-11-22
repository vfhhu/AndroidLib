package xyz.vfhhu.lib.android.http;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by leo3x on 2018/3/29.
 */

public interface HttpCallBcak {
    void onSuccess(String ret);
    void onError(int code, String ret);
    void onResponse(Call call, Response response);
    void onFailure(Call call, IOException e);
}
