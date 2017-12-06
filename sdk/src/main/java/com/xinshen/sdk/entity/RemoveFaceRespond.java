package com.xinshen.sdk.entity;

import java.util.List;

/**
 * Created by thinkpad on 2017/11/28.
 */

public class RemoveFaceRespond {

    /**
     *
     * faceset_token : d203a107c943d47bb0efbb25a16cf84b
     * face_removed : 1
     * time_used : 927
     * face_count : 1
     * request_id : 1470480301,b05e0080-fa65-406e-9b78-64c4387904ee
     * outer_id :
     * failure_detail : [{"reason":"INVALID_FACE_TOKEN","face_token":"testtest1"},{"reason":"INVALID_FACE_TOKEN","face_token":"testtest2"}]
     */

    private String faceset_token;
    private int face_removed;
    private int time_used;
    private int face_count;
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

    public int getFace_removed() {
        return face_removed;
    }

    public void setFace_removed(int face_removed) {
        this.face_removed = face_removed;
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
