package com.pcms.domain;

public class MoiveFail {
    private Integer id;

    private String moivename;

    private String status;

    private String updatetime;

    private String wxname;

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
}