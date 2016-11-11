package com.sudichina.ftwl.bean;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by mccccccmike on 2016/8/30.
 */
public class DicValueBean extends DataSupport {
    private int id;
    private String status;
    private String gmtCreate;
    private String gmtModified;
    private String paraCode;
    private String paraName;
    private int dicGroupId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(String gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getParaCode() {
        return paraCode;
    }

    public void setParaCode(String paraCode) {
        this.paraCode = paraCode;
    }

    public String getParaName() {
        return paraName;
    }

    public void setParaName(String paraName) {
        this.paraName = paraName;
    }

    public int getDicGroupId() {
        return dicGroupId;
    }

    public void setDicGroupId(int dicGroupId) {
        this.dicGroupId = dicGroupId;
    }
}
