package com.xinshen.sdk.Iface;

import com.xinshen.sdk.entity.DetectFaceRespond;
import com.xinshen.sdk.entity.ErrorRespond;

/**
 * Created by thinkpad on 2017/11/30.
 */

public interface DetectFaceCallBack {
    void onRespond(DetectFaceRespond respond);
    void onError(ErrorRespond error);
}
