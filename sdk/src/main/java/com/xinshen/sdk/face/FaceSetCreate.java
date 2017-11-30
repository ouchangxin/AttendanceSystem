package com.xinshen.sdk.face;

import com.google.gson.Gson;
import com.xinshen.sdk.Global;
import com.xinshen.sdk.Iface.CreateSetCallBack;
import com.xinshen.sdk.Iface.RequestCallBack;
import com.xinshen.sdk.URL;
import com.xinshen.sdk.entity.CreateRespond;
import com.xinshen.sdk.entity.ErrorRespond;
import com.xinshen.sdk.net.OkHttpManager;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by thinkpad on 2017/11/16.
 */

public class FaceSetCreate {
    private static FaceSetCreate mCreator;
    private static final String REG = "[^&^@,=*'\"]+";
    private FaceSetCreate(){}
    public static synchronized FaceSetCreate newInstance(){
        if (mCreator ==null){
            mCreator = new FaceSetCreate();
        }
        return mCreator;
    }
    public void createSet(String setName, final CreateSetCallBack callBack){
        if (setName == null)
            throw new NullPointerException("The setName is null");
        if (callBack == null)
            throw new NullPointerException("The CreateSetCallBack is null");
        if (!setName.matches(REG)){
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

}
