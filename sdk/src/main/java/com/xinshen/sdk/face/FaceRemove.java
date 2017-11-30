package com.xinshen.sdk.face;

import com.google.gson.Gson;
import com.xinshen.sdk.Global;
import com.xinshen.sdk.Iface.RemoveFaceCallBack;
import com.xinshen.sdk.Iface.RequestCallBack;
import com.xinshen.sdk.URL;
import com.xinshen.sdk.entity.AddFaceRespone;
import com.xinshen.sdk.entity.ErrorRespond;
import com.xinshen.sdk.entity.RemoveFaceRespone;
import com.xinshen.sdk.net.OkHttpManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by thinkpad on 2017/11/28.
 */

public class FaceRemove {
    private static FaceRemove mRemove;
    private FaceRemove(){}
    public static synchronized FaceRemove ewInstance(){
        if (mRemove == null){
            mRemove = new FaceRemove();
        }
        return mRemove;
    }
    public void removeFace(String setName, String face_tokens, final RemoveFaceCallBack callBack){
        if (setName == null)
            throw new NullPointerException("The setName is null");
        if (face_tokens == null)
            throw new NullPointerException("The face_tokens is null");
        if (callBack == null)
            throw new NullPointerException("The RemoveFaceCallBack is null");
        Map<String,String> map = new HashMap<String,String>();
        map.put("api_key", Global.API_KEY);
        map.put("api_secret",Global.API_SECRET);
        map.put("outer_id",setName);
        map.put("face_tokens",face_tokens);
        OkHttpManager.getInstance().post_asy(URL.REMOVE_FACE_URL, map, new RequestCallBack() {
            @Override
            public void dataCallBack(String data, int stateCode) {
                if (stateCode == 200){
                    RemoveFaceRespone respone = new Gson().fromJson(data,RemoveFaceRespone.class);
                    callBack.onRespond(respone);
                }else{
                    ErrorRespond error = new Gson().fromJson(data,ErrorRespond.class);
                    callBack.onError(error);
                }
            }
        });
    }
}
