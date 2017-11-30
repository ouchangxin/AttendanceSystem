package com.xinshen.sdk.face;

import com.google.gson.Gson;
import com.xinshen.sdk.Global;
import com.xinshen.sdk.Iface.DetectFaceCallBack;
import com.xinshen.sdk.Iface.RequestCallBack;
import com.xinshen.sdk.URL;
import com.xinshen.sdk.entity.DetectFaceRespone;
import com.xinshen.sdk.entity.ErrorRespond;
import com.xinshen.sdk.net.OkHttpManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by thinkpad on 2017/11/30.
 */

public class FaceDetector {
    private static FaceDetector mDetector;
    private FaceDetector(){}
    public static synchronized FaceDetector newInstance(){
        if (mDetector == null){
            mDetector = new FaceDetector();
        }
        return mDetector;
    }
    public void detectFace(String img,DetectFaceCallBack callBack){
       detectFace(img,false,callBack);
    }
    public void detectFace(String img,boolean isCheckSexAndAge, final DetectFaceCallBack callBack) {
        if (img == null)
            throw new NullPointerException("image  cannot be null");
        if (callBack == null)
            throw new NullPointerException("DetectFaceCallBack  cannot be null");
        Map<String, String> map = new HashMap<String, String>();
        map.put("api_key", Global.API_KEY);
        map.put("api_secret", Global.API_SECRET);
        map.put("image_file", img);
        if (isCheckSexAndAge){
            map.put("return_attributes","gender,age");
        }
        OkHttpManager.getInstance().post_asy(URL.DETECT_URL, map, new RequestCallBack() {
            @Override
            public void dataCallBack(String data, int stateCode) {
                if (stateCode == 200){
                    DetectFaceRespone respone = new Gson().fromJson(data,DetectFaceRespone.class);
                    callBack.onRespond(respone);
                }else{
                    ErrorRespond error = new Gson().fromJson(data,ErrorRespond.class);
                    callBack.onError(error);
                }
            }
        });
    }

}
