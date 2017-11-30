package com.xinshen.attendancesystem.okhttp;

import android.util.Log;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.xinshen.attendancesystem.Global;
import com.xinshen.sdk.Iface.RequestCallBack;

import java.io.IOException;


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
    public void post(final String url, final Object object){
        if (url == null || object == null)
            return;
        new Thread(){
            @Override
            public void run() {
                RequestBody body = RequestBody.create(JSON,mGson.toJson(object));
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
                        Logger.e(data);
                    }else{
                        Logger.e(data);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    /**异步post请求*/
    public void post_asy(final String url, final Object object, final RequestCallBack callBack){
        if (url == null || object == null)
            return;
        new Thread(){
            @Override
            public void run() {
                RequestBody body = RequestBody.create(JSON,mGson.toJson(object));
                final Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                Call call = mClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
//                        callBack.dataCallBack(response.body().toString(),response.code());
                    }
                });
            }
        }.start();
    }
    public void post2(){
        new Thread(){
            @Override
            public void run() {
                FormEncodingBuilder builder = new FormEncodingBuilder();
                builder.add("api_key","2NIYsLEIdDuMncGiCRsv3WUzmBVm0mRe");
                builder.add("api_secret","zqdgod6ND5gjMnEvxYaw8g7LV_sLwRza");
                builder.add("outer_id","qwe");
                RequestBody body = builder.build();
                Request request = new Request.Builder().url(Global.Const.CREATE_SET_URL)
                        .post(body)
                        .build();
                Call call = mClient.newCall(request);
                try {
                    Response response = call.execute();
                    String data = response.body().string();
                    if (response.isSuccessful()){
                        Logger.e(data);
                    }else{
                        Logger.e(data);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
