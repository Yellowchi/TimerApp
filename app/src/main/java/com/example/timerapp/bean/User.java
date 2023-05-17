package com.example.timerapp.bean;
import java.io.Serializable;

public class User implements Serializable {
    /**
     * 用户主键
     */
    private Integer userid;

    /**
     * 用户名
     */
    private String username;

    /**
     * 电话号
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话
     */
    private String tel;

    /**
     * 0为未知，1为女，2为男
     */
    private Integer sex;

    /**
     * 年龄
     */
    private Integer age;
}