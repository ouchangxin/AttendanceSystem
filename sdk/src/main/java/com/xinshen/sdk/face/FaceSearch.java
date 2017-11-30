package com.xinshen.sdk.face;

import com.google.gson.Gson;
import com.xinshen.sdk.Global;
import com.xinshen.sdk.Iface.FindFaceCallBack;
import com.xinshen.sdk.Iface.RequestCallBack;
import com.xinshen.sdk.URL;
import com.xinshen.sdk.entity.ErrorRespond;
import com.xinshen.sdk.entity.FindFaceRespone;
import com.xinshen.sdk.net.OkHttpManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by thinkpad on 2017/11/30.
 */

public class FaceSearch {
    private static FaceSearch mSearch;
    private FaceSearch(){}
    public static synchronized FaceSearch newInstance(){
        if (mSearch == null){
            mSearch = new FaceSearch();
        }
        return mSearch;
    }
    public void findFace(String img, String setName, final FindFaceCallBack callBack){
        if (img == null)
            throw new NullPointerException("image cannot be null");
        if (setName == null)
            throw new NullPointerException("The Name of FaceSet cannot be null");
        if (callBack == null)
            throw new NullPointerException("FindFaceCallBack cannot be null");
        Map<String, String> map = new HashMap<String, String>();
        map.put("api_key", Global.API_KEY);
        map.put("api_secret", Global.API_SECRET);
        map.put("image_file", img);
        map.put("outer_id",setName);
        OkHttpManager.getInstance().post_asy(URL.SERACH_URL, map, new RequestCallBack() {
            @Override
            public void dataCallBack(String data, int stateCode) {
                if (stateCode == 200){
                    FindFaceRespone respone = new Gson().fromJson(data,FindFaceRespone.class);
                    callBack.onRespond(respone);
                }else{
                    ErrorRespond error = new Gson().fromJson(data,ErrorRespond.class);
                    callBack.onError(error);
                }
            }
        });

    }
}
