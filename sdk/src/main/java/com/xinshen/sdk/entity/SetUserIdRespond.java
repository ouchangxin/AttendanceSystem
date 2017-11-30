package com.xinshen.sdk.entity;

/**
 * Created by thinkpad on 2017/11/28.
 */

public class SetUserIdRespond {

    /**
     * user_id : 234723hgfd
     * request_id : 1470481019,fd7c8a99-93fc-45d4-9eb6-9aaf6fb59f32
     * time_used : 15
     * face_token : 4dc8ba0650405fa7a4a5b0b5cb937f0b
     * error_message : MISSING_ARGUMENTS: face_token
     */

    private String user_id;
    private String request_id;
    private int time_used;
    private String face_token;
    private String error_message;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public int getTime_used() {
        return time_used;
    }

    public void setTime_used(int time_used) {
        this.time_used = time_used;
    }

    public String getFace_token() {
        return face_token;
    }

    public void setFace_token(String face_token) {
        this.face_token = face_token;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }
}
