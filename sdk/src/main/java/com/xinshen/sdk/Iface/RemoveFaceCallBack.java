package com.xinshen.sdk.Iface;

import com.xinshen.sdk.entity.AddFaceRespone;
import com.xinshen.sdk.entity.ErrorRespond;
import com.xinshen.sdk.entity.RemoveFaceRespone;

/**
 * Created by thinkpad on 2017/11/28.
 */

public interface RemoveFaceCallBack {
    void onRespond(RemoveFaceRespone respond);
    void onError(ErrorRespond error);
}
