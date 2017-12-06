package com.xinshen.sdk.face;

import com.google.gson.Gson;
import com.xinshen.sdk.Global;
import com.xinshen.sdk.Iface.AddFaceCallBack;
import com.xinshen.sdk.Iface.RemoveFaceCallBack;
import com.xinshen.sdk.Iface.RequestCallBack;
import com.xinshen.sdk.Iface.SetUserIdCallBack;
import com.xinshen.sdk.URL;
import com.xinshen.sdk.entity.AddFaceRespond;
import com.xinshen.sdk.entity.ErrorRespond;
import com.xinshen.sdk.entity.RemoveFaceRespond;
import com.xinshen.sdk.entity.SetUserIdRespond;
import com.xinshen.sdk.net.OkHttpManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by thinkpad on 2017/11/28.
 */

public class FaceManager {
    private static FaceManager manager;
    private FaceManager(){}
    public static synchronized FaceManager newInstance(){
        if (manager == null){
            manager = new FaceManager();
        }
        return manager;
    }
    /**添加人脸*/
    public void addFace(String setName, String face_tokens, final AddFaceCallBack callBack){
        if (setName == null)
            throw new IllegalArgumentException("The setName cannot be null");
        if (face_tokens == null)
            throw new IllegalArgumentException("The face_tokens cannot be null");
        if (callBack == null)
            throw new IllegalArgumentException("The AddFaceCallBack cannot be null");
        Map<String,String> map = new HashMap<String,String>();
        map.put("api_key", Global.API_KEY);
        map.put("api_secret",Global.API_SECRET);
        map.put("outer_id",setName);
        map.put("face_tokens",face_tokens);
        OkHttpManager.getInstance().post_asy(URL.ADD_FACE_URL, map, new RequestCallBack() {
            @Override
            public void dataCallBack(String data, int stateCode) {
                if (stateCode == 200){
                    AddFaceRespond respone = new Gson().fromJson(data,AddFaceRespond.class);
                    callBack.onRespond(respone);
                }else{
                    ErrorRespond error = new Gson().fromJson(data,ErrorRespond.class);
                    callBack.onError(error);
                }
            }
        });

    }
    /**删除人脸*/
    public void removeFace(String setName, String face_tokens, final RemoveFaceCallBack callBack){
        if (setName == null)
            throw new NullPointerException("The setName cannot be null");
        if (face_tokens == null)
            throw new NullPointerException("The face_tokens cannot be null");
        if (callBack == null)
            throw new NullPointerException("The RemoveFaceCallBack cannot be null");
        Map<String,String> map = new HashMap<String,String>();
        map.put("api_key", Global.API_KEY);
        map.put("api_secret",Global.API_SECRET);
        map.put("outer_id",setName);
        map.put("face_tokens",face_tokens);
        OkHttpManager.getInstance().post_asy(URL.REMOVE_FACE_URL, map, new RequestCallBack() {
            @Override
            public void dataCallBack(String data, int stateCode) {
                if (stateCode == 200){
                    RemoveFaceRespond respone = new Gson().fromJson(data,RemoveFaceRespond.class);
                    callBack.onRespond(respone);
                }else{
                    ErrorRespond error = new Gson().fromJson(data,ErrorRespond.class);
                    callBack.onError(error);
                }
            }
        });
    }
    /**设置user_id*/
    public void setUserID(String face_token, String user_id,final SetUserIdCallBack callBack){
        if (face_token == null)
            throw new NullPointerException("The face_token cannot be  null");
        if (user_id == null)
            throw new NullPointerException("The userID can not  null");
        if (!user_id.matches(Global.REG))
            throw new IllegalArgumentException("The Name of userID cannot contain characters \"&^@,=*'\"\" ");
        Map<String,String> map = new HashMap<String,String>();
        map.put("api_key", Global.API_KEY);
        map.put("api_secret",Global.API_SECRET);
        map.put("face_token",face_token);
        map.put("user_id",user_id);
        OkHttpManager.getInstance().post_asy(URL.SET_USER_ID_URL, map, new RequestCallBack() {
            @Override
            public void dataCallBack(String data, int stateCode) {
                if (stateCode == 200){
                    SetUserIdRespond respone = new Gson().fromJson(data,SetUserIdRespond.class);
                    callBack.onRespond(respone);
                }else{
                    ErrorRespond error = new Gson().fromJson(data,ErrorRespond.class);
                    callBack.onError(error);
                }
            }
        });
    }
}
