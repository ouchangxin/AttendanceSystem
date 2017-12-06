package com.xinshen.sdk.Iface;

import com.xinshen.sdk.entity.ErrorRespond;
import com.xinshen.sdk.entity.RemoveFaceRespond;

/**
 * Created by thinkpad on 2017/11/28.
 */

public interface RemoveFaceCallBack {
    void onRespond(RemoveFaceRespond respond);
    void onError(ErrorRespond error);
}
