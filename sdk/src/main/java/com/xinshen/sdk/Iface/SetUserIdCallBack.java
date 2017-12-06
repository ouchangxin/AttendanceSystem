package com.xinshen.sdk.Iface;

import com.xinshen.sdk.entity.ErrorRespond;
import com.xinshen.sdk.entity.SetUserIdRespond;

/**
 * Created by thinkpad on 2017/11/28.
 */

public interface SetUserIdCallBack {
    void onRespond(SetUserIdRespond respond);
    void onError(ErrorRespond error);
}
