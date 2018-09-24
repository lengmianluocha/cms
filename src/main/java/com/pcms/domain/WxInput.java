package com.pcms.domain;

public class WxInput {
    private Integer id;

    private String inputvalue;

    private String inputtype;

    private String inputtime;

    private String status;

    private String responsevalue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInputvalue() {
        return inputvalue;
    }

    public void setInputvalue(String inputvalue) {
        this.inputvalue = inputvalue == null ? null : inputvalue.trim();
    }

    public String getInputtype() {
        return inputtype;
    }

    public void setInputtype(String inputtype) {
        this.inputtype = inputtype == null ? null : inputtype.trim();
    }

    public String getInputtime() {
        return inputtime;
    }

    public void setInputtime(String inputtime) {
        this.inputtime = inputtime == null ? null : inputtime.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getResponsevalue() {
        return responsevalue;
    }

    public void setResponsevalue(String responsevalue) {
        this.responsevalue = responsevalue == null ? null : responsevalue.trim();
    }
}