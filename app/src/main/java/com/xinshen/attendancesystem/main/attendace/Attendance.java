package com.xinshen.attendancesystem.main.attendace;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by thinkpad on 2018/4/1.
 */

@Entity
public class Attendance {
    @Transient
    public static final int NORMAL = 0;
    @Transient
    public static final int LATE = 1;
    @Transient
    public static final int LEAVE_EARLY = 2;
    @Transient
    public static final int ABSENT = 3;

    @Id(autoincrement = true)
    private Long _id;
    private String date;
    private String name;
    private String work;
    private String offWork;
    private String state1;
    private String state2;
    @Generated(hash = 303635678)
    public Attendance(Long _id, String date, String name, String work,
            String offWork, String state1, String state2) {
        this._id = _id;
        this.date = date;
        this.name = name;
        this.work = work;
        this.offWork = offWork;
        this.state1 = state1;
        this.state2 = state2;
    }
    @Generated(hash = 812698609)
    public Attendance() {
    }
    public Long get_id() {
        return this._id;
    }
    public void set_id(Long _id) {
        this._id = _id;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getWork() {
        return this.work;
    }
    public void setWork(String work) {
        this.work = work;
    }
    public String getOffWork() {
        return this.offWork;
    }
    public void setOffWork(String offWork) {
        this.offWork = offWork;
    }
    public String getState1() {
        return this.state1;
    }
    public void setState1(String state1) {
        this.state1 = state1;
    }
    public String getState2() {
        return this.state2;
    }
    public void setState2(String state2) {
        this.state2 = state2;
    }
}
