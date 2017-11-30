package com.xinshen.sdk.Iface;


import com.xinshen.sdk.entity.CreateRespond;
import com.xinshen.sdk.entity.ErrorRespond;

/**
 * Created by thinkpad on 2017/11/17.
 */

public interface CreateSetCallBack {
    void onRespond(CreateRespond respond);
    void onError(ErrorRespond error);
}
