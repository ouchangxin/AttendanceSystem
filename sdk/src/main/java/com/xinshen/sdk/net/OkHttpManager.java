package com.xinshen.sdk.net;


import com.google.gson.Gson;

import com.xinshen.sdk.Iface.RequestCallBack;
import com.xinshen.sdk.util.LogWrapper;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by thinkpad on 2017/10/22.
 */

public class OkHttpManager {

    private static OkHttpManager mManager;
    private static OkHttpClient mClient;
    private static final MediaType JSON = MediaType.parse("Application/json; charset=utf-8");
    private Gson mGson;

    private OkHttpManager() {
        mClient = new OkHttpClient();
        mGson = new Gson();
    }

    public synchronized static OkHttpManager getInstance() {
        if (mManager == null) {
            mManager = new OkHttpManager();
        }
        return mManager;
    }

    /**
     * 同步post请求
     */
    public void post(final String url, final Map<String, String> map, final RequestCallBack
            callBack) {
        if (url == null || map == null)
            return;
        new Thread() {
            @Override
            public void run() {
                FormBody.Builder builder = new FormBody.Builder();
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    builder.add(entry.getKey(), entry.getValue());
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
                    if (response.isSuccessful()) {
                        callBack.dataCallBack(data, stateCode);
                    } else {
                        LogWrapper.e("网络请求失败: " + data + "\n" + response.toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 异步post请求
     */
    public void post_asy(final String url, final Map<String, String> param, final RequestCallBack
            callBack) {
        if (url == null || param == null)
            return;
        FormBody.Builder builder = new FormBody.Builder();
            for (Map.Entry<String, String> entry : param.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
            RequestBody body = builder.build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Call call = mClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    LogWrapper.e("网络请求失败");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String data = response.body().string();
                    int stateCode = response.code();
                    callBack.dataCallBack(data, stateCode);
                }
            });

    }
}
