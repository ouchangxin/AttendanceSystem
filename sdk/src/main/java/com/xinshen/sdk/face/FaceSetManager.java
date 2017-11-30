package com.xinshen.sdk.face;

import com.google.gson.Gson;
import com.xinshen.sdk.Global;
import com.xinshen.sdk.Iface.CreateSetCallBack;
import com.xinshen.sdk.Iface.GetFaceSetCallBack;
import com.xinshen.sdk.Iface.RequestCallBack;
import com.xinshen.sdk.URL;
import com.xinshen.sdk.entity.CreateRespond;
import com.xinshen.sdk.entity.ErrorRespond;
import com.xinshen.sdk.entity.GetFaceSetRespond;
import com.xinshen.sdk.net.OkHttpManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by thinkpad on 2017/11/28.
 */

public class FaceSetManager {
    private static FaceSetManager manager;
    private FaceSetManager(){}
    public static synchronized FaceSetManager newInstance(){
        if (manager == null){
            manager = new FaceSetManager();
        }
        return manager;
    }
    /**创建人脸集合*/
    public void createSet(String setName, final CreateSetCallBack callBack){
        if (setName == null)
            throw new NullPointerException("The setName is null");
        if (callBack == null)
            throw new NullPointerException("The CreateSetCallBack is null");
        if (!setName.matches(Global.REG)){
            throw new IllegalArgumentException("The Name of FaceSet cannot contain characters \"&^@,=*'\"\" ");
        }
        Map<String,String> map = new HashMap<String,String>();
        map.put("api_key", Global.API_KEY);
        map.put("api_secret", Global.API_SECRET);
        map.put("display_name",setName);
        map.put("outer_id",setName);
        //加一个正则匹配

        OkHttpManager.getInstance().post_asy(URL.CREATE_SET_URL, map, new RequestCallBack() {

            @Override
            public void dataCallBack(String data, int stateCode) {
                if (stateCode == 200){
                    CreateRespond respond =  new Gson().fromJson(data, CreateRespond.class);
                    callBack.onRespond(respond);
                }else{
                    ErrorRespond error = new Gson().fromJson(data,ErrorRespond.class);
                    callBack.onError(error);
                }
            }
        });

    }

    /**获得人脸集合信息*/
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
