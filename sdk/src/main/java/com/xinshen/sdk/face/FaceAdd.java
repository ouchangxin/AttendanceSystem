package com.xinshen.sdk.face;

import com.google.gson.Gson;
import com.xinshen.sdk.Global;
import com.xinshen.sdk.Iface.AddFaceCallBack;
import com.xinshen.sdk.Iface.RequestCallBack;
import com.xinshen.sdk.URL;
import com.xinshen.sdk.entity.AddFaceRespone;
import com.xinshen.sdk.entity.ErrorRespond;
import com.xinshen.sdk.net.OkHttpManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by thinkpad on 2017/11/28.
 */

public class FaceAdd {
    private static FaceAdd mAdd;
    private FaceAdd(){}
    public static synchronized FaceAdd newInstance(){
        if (mAdd == null){
            mAdd = new FaceAdd();
        }
        return mAdd;
    }



    public void addFace(String setName, String face_tokens, final AddFaceCallBack callBack){
        if (setName == null)
            throw new NullPointerException("The setName is null");
        if (face_tokens == null)
            throw new NullPointerException("The face_tokens is null");
        if (callBack == null)
            throw new NullPointerException("The AddFaceCallBack is null");
        Map<String,String> map = new HashMap<String,String>();
        map.put("api_key", Global.API_KEY);
        map.put("api_secret",Global.API_SECRET);
        map.put("outer_id",setName);
        map.put("face_tokens",face_tokens);
        OkHttpManager.getInstance().post_asy(URL.ADD_FACE_URL, map, new RequestCallBack() {
            @Override
            public void dataCallBack(String data, int stateCode) {
                if (stateCode == 200){
                    AddFaceRespone respone = new Gson().fromJson(data,AddFaceRespone.class);
                    callBack.onRespond(respone);
                }else{
                    ErrorRespond error = new Gson().fromJson(data,ErrorRespond.class);
                    callBack.onError(error);
                }
            }
        });

    }
}
