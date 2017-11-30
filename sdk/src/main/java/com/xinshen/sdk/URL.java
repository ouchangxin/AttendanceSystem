package com.xinshen.sdk;

/**
 * Created by thinkpad on 2017/11/16.
 */

public class URL {
    //face++人脸识别接口相关url
    public static final String ROOT = "https://api-cn.faceplusplus.com/facepp/v3/";
    public static final String DETECT_URL = ROOT+"detect";
    public static final String SERACH_URL = ROOT+"search";
    public static final String CREATE_SET_URL = ROOT+"faceset/create";
    public static final String ADD_FACE_URL = ROOT+"faceset/addface";
    public static final String REMOVE_FACE_URL = ROOT+"faceset/removeface";
    public static final String GET_FACE_SET_URL = ROOT+"faceset/getfacesets";
    public static final String SET_USER_ID_URL = ROOT+"face/setuserid";
}
