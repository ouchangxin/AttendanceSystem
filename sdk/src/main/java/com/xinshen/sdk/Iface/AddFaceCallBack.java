package com.xinshen.sdk.Iface;

import com.xinshen.sdk.entity.AddFaceRespone;
import com.xinshen.sdk.entity.ErrorRespond;
import com.xinshen.sdk.entity.GetFaceSetRespond;

/**
 * Created by thinkpad on 2017/11/28.
 */

public interface AddFaceCallBack {
    void onRespond(AddFaceRespone respond);
    void onError(ErrorRespond error);
}
