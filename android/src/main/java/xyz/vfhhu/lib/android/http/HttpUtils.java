package xyz.vfhhu.lib.android.http;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by leo3x on 2018/6/21.
 */

public class HttpUtils {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    public static final String TAG="HttpUtils";
    private static OkHttpClient client ;
    private static HashMap<String,String> setHeader;
    private static HashMap <String,String> addHeader;
    private static  HttpUtils inst;
    private HttpUtils(){
        client = new OkHttpClient();
        setHeader=new HashMap<>();
        addHeader=new HashMap<>();
        inst=this;
    }
    public static  HttpUtils init(){
        if(inst!=null)return inst;
        return new HttpUtils();
    }
    public static void setTimeout(long sec){
        if(inst==null)init();
        OkHttpClient.Builder b=client.newBuilder();
        b.connectTimeout(sec, TimeUnit.SECONDS); // connect timeout
        b.readTimeout(sec, TimeUnit.SECONDS);    // socket timeout
        client=b.build();
    }
    public static void setHeader(HashMap<String,String> map){setHeader=map;}
    public static void addHeader(HashMap<String,String> map){addHeader=map;}
    private static void buildHeader(Request.Builder b){
        for(Map.Entry<String,String> e: setHeader.entrySet()){
            b.header(e.getKey(),e.getValue());
        }
        for(Map.Entry<String,String> e: addHeader.entrySet()){
//            b.removeHeader(e.getKey()).addHeader(e.getKey(),e.getValue());
            b.addHeader(e.getKey(),e.getValue());
        }
    }

    public static String Get(String url) throws IOException {
        if(inst==null)init();
        Request.Builder b=new Request.Builder();
        buildHeader(b);
        Request request = b.url(url).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
    public static void Get(String url,final Callback callBcak) throws IOException {
        if(inst==null)init();
        Request.Builder b=new Request.Builder();
        buildHeader(b);
        Request request = b.url(url).build();
        try{
            client.newCall(request).enqueue(callBcak);
        }catch (Exception e){}

//        return response.body().string();
    }

    public static String Post(String url, String json) throws IOException {
        if(inst==null)init();
        RequestBody requestBody = RequestBody.create(JSON, json);

        Request.Builder b=new Request.Builder();
        buildHeader(b);
        Request request = b.url(url).post(requestBody).build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
    public static void Post(String url, String json,Callback callBcak) throws IOException {
        if(inst==null)init();
        RequestBody requestBody = RequestBody.create(JSON, json);

        Request.Builder b=new Request.Builder();
        buildHeader(b);
        Request request = b.url(url).post(requestBody).build();

        try{
            client.newCall(request).enqueue(callBcak);
        }catch (Exception e){}

    }
    public static String Post(String url, TreeMap<String,String> para) throws IOException {
        if(inst==null)init();
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : para.keySet()) {
            builder.add(key, para.get(key));
        }
        RequestBody requestBody=builder.build();

        Request.Builder b=new Request.Builder();
        buildHeader(b);
        Request request = b.url(url).post(requestBody).build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
    public static void Post(String url, TreeMap<String,String> para,Callback callBcak) throws IOException {
        if(inst==null)init();
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : para.keySet()) {
            builder.add(key, para.get(key));
        }
        RequestBody requestBody=builder.build();

        Request.Builder b=new Request.Builder();
        buildHeader(b);
        Request request = b.url(url).post(requestBody).build();

        try{
            client.newCall(request).enqueue(callBcak);
        }catch (Exception e){}
    }
}
