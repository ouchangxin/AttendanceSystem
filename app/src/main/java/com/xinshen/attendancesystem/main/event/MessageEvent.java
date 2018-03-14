package com.xinshen.attendancesystem.main.event;

import com.xinshen.attendancesystem.main.employee.EmployeeBean;

/**
 * Created by thinkpad on 2017/12/12.
 */

public class MessageEvent {

    public static final int EVENT_UPDATE = 0 ;
    public static final int EVENT_AFACE = 1;
    private EmployeeBean bean;
    private int type;

    public MessageEvent(int type,EmployeeBean bean) {
        this.type = type;
        this.bean = bean;
    }

    public EmployeeBean getBean() {
        return bean;
    }

    public void setBean(EmployeeBean bean) {
        this.bean = bean;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
