package xyz.vfhhu.lib.android;

import android.content.Context;
import android.content.SharedPreferences;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.TreeMap;

import okhttp3.Callback;
import okhttp3.Response;
import xyz.vfhhu.lib.android.http.HttpUtils;

/**
 * Created by leo3x on 2018/11/22.
 */

public class VfhhuLib {
    private static boolean debug=false;
    private static VfhhuLib _instance;
    private VfhhuLib(){
        Logger.clearLogAdapters();
        Logger.addLogAdapter(new AndroidLogAdapter());
    }
    public static VfhhuLib init(){
        if(_instance==null)_instance=new VfhhuLib();
        return _instance;
    }

    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean debug) {
        VfhhuLib.debug = debug;
    }

    public static boolean report_bug(Context ctx,String id,String token, String title,String data){
        return report_bug(ctx,id,token, title,data, ctx.getPackageName(),"you have a message");
    }
    public static boolean report_bug(Context ctx,String id,String token, String title,String data, String head,String desc){
        try {
            head =head.trim();
            data =data.trim();
            desc =desc.trim();
            title =title.trim();
            id =id.trim();
            token =token.trim();
            if(id.length()<=0)return false;
            if(token.length()<=0)return false;
            if(head.length()<=0)return false;
            if(data.length()<=0)return false;
            if(desc.length()<=0)return false;
            if(title.length()<=0)return false;

            TreeMap<String,String> para=new TreeMap<>();
            para.put("id",id);
            para.put("tk",token);
            para.put("head",head);
            para.put("desc",desc);
            para.put("title",title);
            para.put("data",data);
            HttpUtils.Post("https://secretarybot.vfhhu.xyz/data_set.php", para, new Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {}
                @Override
                public void onResponse(okhttp3.Call call, Response response) throws IOException {}
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }


}
