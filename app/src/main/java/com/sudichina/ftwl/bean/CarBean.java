package com.sudichina.ftwl.bean;

import java.io.Serializable;

/**
 * Created by mccccccmike on 2016/9/2.
 */
public class CarBean implements Serializable {
    private long id;
    private String status;
    private long gmtCreate;
    private long gmtModified;
    private long accountId;
    private String carFirstLetter;
    private String cardNumber;
    private String driverName;
    private String driverTel;
    private String driverLicense;
    private String driverLicensePath;
    private String engineNum;
    private long carDicId;
    private String driveLicense;
    private String driveLicensePath;
    private String driveLicensePath2;
    private String driveLicensePath3;
    private String zoneId;
    private String zoneStreet;
    private int auditStatus;
    private String auditReason;
    private String postTimes;
    private long recordSource;
    private boolean del;
    private String online;

    public CarBean(long id, String status, long gmtCreate, long gmtModified, long accountId, String carFirstLetter, String cardNumber, String driverName, String driverTel, String driverLicense, String driverLicensePath, String engineNum, long carDicId, String driveLicense, String driveLicensePath, String driveLicensePath2, String driveLicensePath3, String zoneId, String zoneStreet, int auditStatus, String auditReason, String postTimes, long recordSource, boolean del, String online) {
        this.id = id;
        this.status = status;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.accountId = accountId;
        this.carFirstLetter = carFirstLetter;
        this.cardNumber = cardNumber;
        this.driverName = driverName;
        this.driverTel = driverTel;
        this.driverLicense = driverLicense;
        this.driverLicensePath = driverLicensePath;
        this.engineNum = engineNum;
        this.carDicId = carDicId;
        this.driveLicense = driveLicense;
        this.driveLicensePath = driveLicensePath;
        this.driveLicensePath2 = driveLicensePath2;
        this.driveLicensePath3 = driveLicensePath3;
        this.zoneId = zoneId;
        this.zoneStreet = zoneStreet;
        this.auditStatus = auditStatus;
        this.auditReason = auditReason;
        this.postTimes = postTimes;
        this.recordSource = recordSource;
        this.del = del;
        this.online = online;
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

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getCarFirstLetter() {
        return carFirstLetter;
    }

    public void setCarFirstLetter(String carFirstLetter) {
        this.carFirstLetter = carFirstLetter;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverTel() {
        return driverTel;
    }

    public void setDriverTel(String driverTel) {
        this.driverTel = driverTel;
    }

    public String getDriverLicense() {
        return driverLicense;
    }

    public void setDriverLicense(String driverLicense) {
        this.driverLicense = driverLicense;
    }

    public String getDriverLicensePath() {
        return driverLicensePath;
    }

    public void setDriverLicensePath(String driverLicensePath) {
        this.driverLicensePath = driverLicensePath;
    }

    public String getEngineNum() {
        return engineNum;
    }

    public void setEngineNum(String engineNum) {
        this.engineNum = engineNum;
    }

    public long getCarDicId() {
        return carDicId;
    }

    public void setCarDicId(long carDicId) {
        this.carDicId = carDicId;
    }

    public String getDriveLicense() {
        return driveLicense;
    }

    public void setDriveLicense(String driveLicense) {
        this.driveLicense = driveLicense;
    }

    public String getDriveLicensePath() {
        return driveLicensePath;
    }

    public void setDriveLicensePath(String driveLicensePath) {
        this.driveLicensePath = driveLicensePath;
    }

    public String getDriveLicensePath2() {
        return driveLicensePath2;
    }

    public void setDriveLicensePath2(String driveLicensePath2) {
        this.driveLicensePath2 = driveLicensePath2;
    }

    public String getDriveLicensePath3() {
        return driveLicensePath3;
    }

    public void setDriveLicensePath3(String driveLicensePath3) {
        this.driveLicensePath3 = driveLicensePath3;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getZoneStreet() {
        return zoneStreet;
    }

    public void setZoneStreet(String zoneStreet) {
        this.zoneStreet = zoneStreet;
    }

    public int getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(int auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuditReason() {
        return auditReason;
    }

    public void setAuditReason(String auditReason) {
        this.auditReason = auditReason;
    }

    public String getPostTimes() {
        return postTimes;
    }

    public void setPostTimes(String postTimes) {
        this.postTimes = postTimes;
    }

    public long getRecordSource() {
        return recordSource;
    }

    public void setRecordSource(long recordSource) {
        this.recordSource = recordSource;
    }

    public boolean isDel() {
        return del;
    }

    public void setDel(boolean del) {
        this.del = del;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }
}
