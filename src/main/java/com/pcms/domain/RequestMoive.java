package com.pcms.domain;

public class RequestMoive {
    private Integer id;

    private String moivename;

    private String status;

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
}