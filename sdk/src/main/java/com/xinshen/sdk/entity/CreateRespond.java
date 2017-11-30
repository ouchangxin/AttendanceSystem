package com.xinshen.sdk.entity;

import java.util.List;

/**
 * Created by thinkpad on 2017/11/17.
 */

public class CreateRespond {

    /**
     * faceset_token : 6110df81aff5fd5d20e7d2571b743bac
     * time_used : 1721
     * face_count : 5
     * face_added : 5
     * request_id : 1470369312,911b9802-732c-4a4c-a3f4-e2735ab59bc0
     * outer_id :
     * failure_detail : [{"reason":"INVALID_FACE_TOKEN","face_token":"testtest1"},{"reason":"INVALID_FACE_TOKEN","face_token":"testtest2"}]
     */

    private String faceset_token;
    private int time_used;
    private int face_count;
    private int face_added;
    private String request_id;
    private String outer_id;
    private List<FailureDetailBean> failure_detail;
    private String error_message;

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public String getFaceset_token() {
        return faceset_token;
    }

    public void setFaceset_token(String faceset_token) {
        this.faceset_token = faceset_token;
    }

    public int getTime_used() {
        return time_used;
    }

    public void setTime_used(int time_used) {
        this.time_used = time_used;
    }

    public int getFace_count() {
        return face_count;
    }

    public void setFace_count(int face_count) {
        this.face_count = face_count;
    }

    public int getFace_added() {
        return face_added;
    }

    public void setFace_added(int face_added) {
        this.face_added = face_added;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getOuter_id() {
        return outer_id;
    }

    public void setOuter_id(String outer_id) {
        this.outer_id = outer_id;
    }

    public List<FailureDetailBean> getFailure_detail() {
        return failure_detail;
    }

    public void setFailure_detail(List<FailureDetailBean> failure_detail) {
        this.failure_detail = failure_detail;
    }

    public static class FailureDetailBean {
        /**
         * reason : INVALID_FACE_TOKEN
         * face_token : testtest1
         */

        private String reason;
        private String face_token;

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getFace_token() {
            return face_token;
        }

        public void setFace_token(String face_token) {
            this.face_token = face_token;
        }
    }
}
