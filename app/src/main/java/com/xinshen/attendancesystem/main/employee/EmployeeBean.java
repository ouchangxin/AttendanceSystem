package com.xinshen.attendancesystem.main.employee;

/**
 * Created by thinkpad on 2017/11/16.
 */

public class EmployeeBean {

    private String avator_url;
    private String name;
    private String depart;
    private String position;
    private String date;
    private String face_token;

    public EmployeeBean(String avator,String name,String depart,String position,String date,String face_token){
        this.avator_url = avator;
        this.name = name;
        this.depart = depart;
        this.position = position;
        this.date = date;
        this.face_token = face_token;
    }

    public String getFace_token() {
        return face_token;
    }

    public void setFace_token(String face_token) {
        this.face_token = face_token;
    }

    public String getAvator_url() {
        return avator_url;
    }

    public void setAvator_url(String avator_url) {
        this.avator_url = avator_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
