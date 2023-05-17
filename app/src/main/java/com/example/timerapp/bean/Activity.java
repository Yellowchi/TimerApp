package com.example.timerapp.bean;

import java.util.Date;
public class Activity{
    /**
     * 
     */
    private Integer activityid;

    /**
     * 
     */
    private Integer actionid;

    /**
     * 
     */
    private Integer userid;

    /**
     * 
     */
    private Date start;

    /**
     * 
     */
    private Date end;

    public Integer getActivityid() {
        return activityid;
    }

    public void setActivityid(Integer activityid) {
        this.activityid = activityid;
    }

    public Integer getActionid() {
        return actionid;
    }

    public void setActionid(Integer actionid) {
        this.actionid = actionid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}