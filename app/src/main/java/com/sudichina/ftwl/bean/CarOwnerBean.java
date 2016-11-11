package com.sudichina.ftwl.bean;

/**
 * Created by mccccccmike on 2016/9/7.
 */
public class CarOwnerBean {
    private long id;
    private String status;
    private long gmtCreate;
    private long gmtModified;
    private String realName;
    private long accountId;
    private String drivingLicense;
    private String drivingLicensePath;
    private String accountProxyId;
    private String auditStatus;
    private String createDate;
    private String recordSource;
    private String proxyType;
    private String dataStatus;

    public CarOwnerBean(long id, String status, long gmtCreate, long gmtModified, String realName, long accountId, String drivingLicense, String drivingLicensePath, String accountProxyId, String auditStatus, String createDate, String recordSource, String proxyType, String dataStatus) {
        this.id = id;
        this.status = status;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.realName = realName;
        this.accountId = accountId;
        this.drivingLicense = drivingLicense;
        this.drivingLicensePath = drivingLicensePath;
        this.accountProxyId = accountProxyId;
        this.auditStatus = auditStatus;
        this.createDate = createDate;
        this.recordSource = recordSource;
        this.proxyType = proxyType;
        this.dataStatus = dataStatus;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getDrivingLicense() {
        return drivingLicense;
    }

    public void setDrivingLicense(String drivingLicense) {
        this.drivingLicense = drivingLicense;
    }

    public String getDrivingLicensePath() {
        return drivingLicensePath;
    }

    public void setDrivingLicensePath(String drivingLicensePath) {
        this.drivingLicensePath = drivingLicensePath;
    }

    public String getAccountProxyId() {
        return accountProxyId;
    }

    public void setAccountProxyId(String accountProxyId) {
        this.accountProxyId = accountProxyId;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getRecordSource() {
        return recordSource;
    }

    public void setRecordSource(String recordSource) {
        this.recordSource = recordSource;
    }

    public String getProxyType() {
        return proxyType;
    }

    public void setProxyType(String proxyType) {
        this.proxyType = proxyType;
    }

    public String getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(String dataStatus) {
        this.dataStatus = dataStatus;
    }
}
