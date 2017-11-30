package com.xinshen.sdk.Iface;

import com.xinshen.sdk.entity.DetectFaceRespone;
import com.xinshen.sdk.entity.ErrorRespond;

/**
 * Created by thinkpad on 2017/11/30.
 */

public interface DetectFaceCallBack {
    void onRespond(DetectFaceRespone respond);
    void onError(ErrorRespond error);
}
