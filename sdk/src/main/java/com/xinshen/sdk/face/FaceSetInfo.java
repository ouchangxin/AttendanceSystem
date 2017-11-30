package com.xinshen.sdk.face;

import com.google.gson.Gson;
import com.xinshen.sdk.Global;
import com.xinshen.sdk.Iface.GetFaceSetCallBack;
import com.xinshen.sdk.Iface.RequestCallBack;
import com.xinshen.sdk.URL;
import com.xinshen.sdk.entity.ErrorRespond;
import com.xinshen.sdk.entity.GetFaceSetRespond;
import com.xinshen.sdk.net.OkHttpManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by thinkpad on 2017/11/28.
 */

public class FaceSetInfo {
    private static FaceSetInfo mInfo;
    private FaceSetInfo(){}
    public static synchronized FaceSetInfo newInstance(){
        if (mInfo == null){
            mInfo = new FaceSetInfo();
        }
        return mInfo;
    }

    public void getFaceSet(GetFaceSetCallBack callBack){
        if (callBack == null)
            throw new NullPointerException("GetFaceSetCallBack is null");
        getFaceSet(1,callBack);
    }


    public void getFaceSet(final int start, final GetFaceSetCallBack callBack){
        if (callBack == null)
            throw new NullPointerException("GetFaceSetCallBack is null");
        Map<String,String> map = new HashMap<String,String>();
        map.put("api_key", Global.API_KEY);
        map.put("api_secret", Global.API_SECRET);
        map.put("start",String.valueOf(start));
        OkHttpManager.getInstance().post_asy(URL.GET_FACE_SET_URL, map, new RequestCallBack() {
            @Override
            public void dataCallBack(String data, int stateCode) {
                if (stateCode == 200){
                    GetFaceSetRespond respond = new Gson().fromJson(data,GetFaceSetRespond.class);
                    callBack.onRespond(respond);
                }else{
                    ErrorRespond error = new Gson().fromJson(data,ErrorRespond.class);
                    callBack.onError(error);
                }
            }
        });
    }

}
