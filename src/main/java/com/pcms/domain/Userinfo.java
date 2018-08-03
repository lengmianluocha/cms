/**
 * @ClassName Userinfo.java
 * @author He.Feng
 * @version 2.3.7
 * @Date 2018-08-01
 * @Copyright(C): 2018 the author chen.biao All rights reserved. 
 */
package com.pcms.domain;

/**
 *
 * @Title userinfo 实体类
 * @author He.Feng
 * @version 2.3.7
 * @Date 2018-08-01
 * @Copyright(C) 2018 www.sandpay.com.cn Inc. All rights reserved.
 */
public class Userinfo {
    /**
     * @Column userinfo.id
     * @author He.Feng
     * @version 2.3.7
     * @Date 2018-08-01 16:58:49
     */
    private Long id;

    /**
     * @Column userinfo.userName
     * @author He.Feng
     * @version 2.3.7
     * @Date 2018-08-01 16:58:49
     */
    private String username;

    /**
     * @Column userinfo.passWord
     * @author He.Feng
     * @version 2.3.7
     * @Date 2018-08-01 16:58:49
     */
    private String password;

    /**
     * @Description 获取字段: 
     *
     * @Title getId
     * @return userinfo.id
     *
     * @date 2018-08-01 16:58:49
     */
    public Long getId() {
        return id;
    }

    /**
     * @Description 设置 字段: 
     *
     * @Title setId
     * @param id the value for userinfo.id
     *
     * @date 2018-08-01 16:58:49
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @Description 获取字段: 
     *
     * @Title getUsername
     * @return userinfo.userName
     *
     * @date 2018-08-01 16:58:49
     */
    public String getUsername() {
        return username;
    }

    /**
     * @Description 设置 字段: 
     *
     * @Title setUsername
     * @param username the value for userinfo.userName
     *
     * @date 2018-08-01 16:58:49
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * @Description 获取字段: 
     *
     * @Title getPassword
     * @return userinfo.passWord
     *
     * @date 2018-08-01 16:58:49
     */
    public String getPassword() {
        return password;
    }

    /**
     * @Description 设置 字段: 
     *
     * @Title setPassword
     * @param password the value for userinfo.passWord
     *
     * @date 2018-08-01 16:58:49
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * @Description toString方法对应数据库记录:userinfo
     *
     * @date 2018-08-01 16:58:49
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append("]");
        return sb.toString();
    }
}