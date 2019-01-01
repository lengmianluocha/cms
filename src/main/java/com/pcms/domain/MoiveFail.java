package com.pcms.domain;

public class MoiveFail {


    /**
     * 链接失效
     */
    public final static String FAILTYPE_INVAILD = "01";

    /**
     * 催更
     */
    public final static String FAILTYPE_URGEMORE = "02";

    public final static String HANLING = "01";
    public final static String HANLED = "02";
    public final static String INGORE = "03";

    private Integer id;

    private String moivename;

    private String status;

    private String updatetime;

    private String wxname;

    private String failtype;

    private Integer counter;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMoivename() {
        return moivename;
    }

    public void setMoivename(String moivename) {
        this.moivename = moivename == null ? null : moivename.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime == null ? null : updatetime.trim();
    }

    public String getWxname() {
        return wxname;
    }

    public void setWxname(String wxname) {
        this.wxname = wxname == null ? null : wxname.trim();
    }

    public String getFailtype() {
        return failtype;
    }

    public void setFailtype(String failtype) {
        this.failtype = failtype == null ? null : failtype.trim();
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }
}