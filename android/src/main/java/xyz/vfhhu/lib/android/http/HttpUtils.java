package xyz.vfhhu.lib.android.http;

import android.content.Context;
import android.os.Build;
import android.webkit.WebSettings;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
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
    private static OkHttpClient client_https ;
    private static HashMap<String,String> setHeader;
    private static HashMap <String,String> addHeader;
    private static  HttpUtils inst;
    private HttpUtils(){
        client = new OkHttpClient();
        setHeader=new HashMap<>();
        addHeader=new HashMap<>();
        inst=this;

        OkHttpClient.Builder mBuilder = new OkHttpClient.Builder();
        mBuilder.sslSocketFactory(createSSLSocketFactory(), mMyTrustManager)
                .hostnameVerifier(new TrustAllHostnameVerifier());
        mBuilder.cookieJar(getCookieJar());
        client_https=mBuilder.build();
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

        OkHttpClient.Builder bssl=client_https.newBuilder();
        bssl.connectTimeout(sec, TimeUnit.SECONDS); // connect timeout
        bssl.readTimeout(sec, TimeUnit.SECONDS);    // socket timeout
        client_https=bssl.build();
    }
    public static void setHeader(HashMap<String,String> map){setHeader=map;}
    public static void addHeader(HashMap<String,String> map){addHeader=map;}
    private static void buildHeader(Request.Builder b){
        b.removeHeader("User-Agent").addHeader("User-Agent",getUserAgent(null)).build();
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
        Request.Builder b=new Request.Builder().url(url);
        buildHeader(b);
        Request request = b.build();
        OkHttpClient _client=client;
        if(url.toLowerCase().startsWith("https")){
            _client=client_https;
        }
        Response response = _client.newCall(request).execute();
        return response.body().string();
    }
    public static void Get(String url,final Callback callBcak) throws IOException {
        if(inst==null)init();
        Request.Builder b=new Request.Builder().url(url);
        buildHeader(b);
        Request request = b.build();
        try{
            OkHttpClient _client=client;
            if(url.toLowerCase().startsWith("https")){
                _client=client_https;
            }
            _client.newCall(request).enqueue(callBcak);
        }catch (Exception e){}

//        return response.body().string();
    }

    public static String Post(String url, String json) throws IOException {
        if(inst==null)init();
        RequestBody requestBody = RequestBody.create(JSON, json);

        Request.Builder b=new Request.Builder().url(url);
        buildHeader(b);
        Request request = b.post(requestBody).build();

        OkHttpClient _client=client;
        if(url.toLowerCase().startsWith("https")){
            _client=client_https;
        }
        Response response = _client.newCall(request).execute();
        return response.body().string();
    }
    public static void Post(String url, String json,Callback callBcak) throws IOException {
        if(inst==null)init();
        RequestBody requestBody = RequestBody.create(JSON, json);

        Request.Builder b=new Request.Builder().url(url);
        buildHeader(b);
        Request request = b.post(requestBody).build();

        try{
            OkHttpClient _client=client;
            if(url.toLowerCase().startsWith("https")){
                _client=client_https;
            }
            _client.newCall(request).enqueue(callBcak);
        }catch (Exception e){}

    }
    public static String Post(String url, TreeMap<String,String> para) throws IOException {
        if(inst==null)init();
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : para.keySet()) {
            builder.add(key, para.get(key));
        }
        RequestBody requestBody=builder.build();

        Request.Builder b=new Request.Builder().url(url);
        buildHeader(b);
        Request request = b.post(requestBody).build();

        OkHttpClient _client=client;
        if(url.toLowerCase().startsWith("https")){
            _client=client_https;
        }
        Response response = _client.newCall(request).execute();
        return response.body().string();
    }
    public static void Post(String url, TreeMap<String,String> para,Callback callBcak) throws IOException {
        if(inst==null)init();
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : para.keySet()) {
            builder.add(key, para.get(key));
        }
        RequestBody requestBody=builder.build();

        Request.Builder b=new Request.Builder().url(url);
        buildHeader(b);
        Request request = b.post(requestBody).build();

        try{
            OkHttpClient _client=client;
            if(url.toLowerCase().startsWith("https")){
                _client=client_https;
            }
            _client.newCall(request).enqueue(callBcak);
        }catch (Exception e){}
    }
    private static String getUserAgent(Context context) {
        String userAgent = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && context!=null) {
            try {
                userAgent = WebSettings.getDefaultUserAgent(context);
            } catch (Exception e) {
                userAgent = System.getProperty("http.agent");
            }
        } else {
            userAgent = System.getProperty("http.agent");
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }


    private MyTrustManager mMyTrustManager;
    private SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            mMyTrustManager = new MyTrustManager();
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new MyTrustManager[]{mMyTrustManager}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }

        return ssfFactory;
    }
    //实现X509TrustManager接口
    public class MyTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }
    //实现HostnameVerifier接口
    private class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    private static CookieJar getCookieJar(){
        return new CookieJar() {
            @Override
            public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
                cookieStore.put(httpUrl.host(), list);
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl httpUrl) {
                List<Cookie> cookies = cookieStore.get(httpUrl.host());
                return cookies != null ? cookies : new ArrayList<Cookie>();
            }
        };
    }
    private static final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

}
