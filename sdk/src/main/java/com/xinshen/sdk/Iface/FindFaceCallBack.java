package com.xinshen.sdk.Iface;

import com.xinshen.sdk.entity.ErrorRespond;
import com.xinshen.sdk.entity.FindFaceRespone;

/**
 * Created by thinkpad on 2017/11/30.
 */

public interface FindFaceCallBack {
    void onRespond(FindFaceRespone respond);
    void onError(ErrorRespond error);
}
