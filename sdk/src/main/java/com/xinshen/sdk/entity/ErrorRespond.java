package com.xinshen.sdk.entity;

/**
 * Created by thinkpad on 2017/11/17.
 */

public class ErrorRespond {

    /**
     * time_used : 22
     * error_message : BAD_ARGUMENTS: tags
     * request_id : 1470369557,47326d02-c42f-4b71-a5e0-f848a1443430
     */

    private int time_used;
    private String error_message;
    private String request_id;

    public int getTime_used() {
        return time_used;
    }

    public void setTime_used(int time_used) {
        this.time_used = time_used;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }
}
