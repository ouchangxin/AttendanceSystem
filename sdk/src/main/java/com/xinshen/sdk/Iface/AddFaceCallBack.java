package com.xinshen.sdk.Iface;

import com.xinshen.sdk.entity.AddFaceRespond;
import com.xinshen.sdk.entity.ErrorRespond;

/**
 * Created by thinkpad on 2017/11/28.
 */

public interface AddFaceCallBack {
    void onRespond(AddFaceRespond respond);
    void onError(ErrorRespond error);
}
