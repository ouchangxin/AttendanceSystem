package com.xinshen.sdk.net;

import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.xinshen.sdk.Global;
import com.xinshen.sdk.Iface.RequestCallBack;
import com.xinshen.sdk.util.LogWrapper;

import java.io.IOException;
import java.util.Map;

/**
 * Created by thinkpad on 2017/10/22.
 */

public class OkHttpManager {

    private static OkHttpManager mManager;
    private static OkHttpClient mClient;
    private static final MediaType JSON = MediaType.parse("Application/json; charset=utf-8");
    private Gson mGson;

    private OkHttpManager(){
        mClient = new OkHttpClient();
        mGson = new Gson();
    }

    public synchronized static OkHttpManager getInstance(){
        if (mManager == null){
            mManager = new OkHttpManager();
        }
        return mManager;
    }

    /**同步post请求*/
    public void post(final String url, final Map<String,String> map, final RequestCallBack callBack){
        if (url == null || map == null)
            return;
        new Thread(){
            @Override
            public void run() {
                FormEncodingBuilder builder = new FormEncodingBuilder();
                for (Map.Entry<String,String> entry : map.entrySet()){
                    builder.add(entry.getKey(),entry.getValue());
                }
                RequestBody body = builder.build();
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                Call call = mClient.newCall(request);
                try {
                    Response response = call.execute();
                    String data = response.body().string();
                    int stateCode = response.code();
                    if (response.isSuccessful()){
                        callBack.dataCallBack(data,stateCode);
                    }else{
                        LogWrapper.e("网络请求失败: "+data+"\n"+response.toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    /**异步post请求*/
    public void post_asy(final String url, final Map<String,String> map, final RequestCallBack callBack){
        if (url == null || map == null)
            return;
        new Thread(){
            @Override
            public void run() {
                FormEncodingBuilder builder = new FormEncodingBuilder();
                for (Map.Entry<String,String> entry : map.entrySet()){
                    builder.add(entry.getKey(),entry.getValue());
                }
                RequestBody body = builder.build();
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                Call call = mClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        LogWrapper.e("网络请求失败");
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        String data = response.body().string();
                        int stateCode = response.code();
                        callBack.dataCallBack(data,stateCode);
                    }
                });

            }
        }.start();
    }
}
