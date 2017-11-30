package com.xinshen.attendancesystem.adapter;

/**
 * Created by thinkpad on 2017/11/15.
 */

public class StaffBean {

    private String imgURL;
    private String name;
    private String depart;

    public StaffBean(String url,String name,String depart){
        this.imgURL = url;
        this.name = name;
        this.depart = depart;
    }
    public  String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
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

}
