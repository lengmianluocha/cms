package com.pcms.domain;

public class Moive {
    private Long id;

    private String mname;

    private String abstracts;

    private String panurl;

    private String panpwd;

    private String murl;

    private String updatetime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname == null ? null : mname.trim();
    }

    public String getAbstracts() {
        return abstracts;
    }

    public void setAbstracts(String abstracts) {
        this.abstracts = abstracts == null ? null : abstracts.trim();
    }

    public String getPanurl() {
        return panurl;
    }

    public void setPanurl(String panurl) {
        this.panurl = panurl == null ? null : panurl.trim();
    }

    public String getPanpwd() {
        return panpwd;
    }

    public void setPanpwd(String panpwd) {
        this.panpwd = panpwd == null ? null : panpwd.trim();
    }

    public String getMurl() {
        return murl;
    }

    public void setMurl(String murl) {
        this.murl = murl == null ? null : murl.trim();
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime == null ? null : updatetime.trim();
    }
}