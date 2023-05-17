package com.example.timerapp.bean;

/**
 * 
 * @TableName Action
 */
public class Action{
    /**
     * 
     */
    private Integer actionid;

    /**
     * 时间名称
     */
    private String actionname;

    /**
     * 用户
     */
    private Integer userid;

    /**
     * 背景颜色
     */
    private String color;

    public Integer getActionid() {
        return actionid;
    }

    public void setActionid(Integer actionid) {
        this.actionid = actionid;
    }

    public String getActionname() {
        return actionname;
    }

    public void setActionname(String actionname) {
        this.actionname = actionname;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}