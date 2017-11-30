package com.xinshen.attendancesystem.main.employee;

/**
 * Created by thinkpad on 2017/11/16.
 */

public class EmployeeBean {

    private String avator;
    private String name;
    private String depart;
    private String position;
    private String date;

    public EmployeeBean(String avator,String name,String depart,String position,String date){
        this.avator = avator;
        this.name = name;
        this.depart = depart;
        this.position = position;
        this.date = date;
    }



    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
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
