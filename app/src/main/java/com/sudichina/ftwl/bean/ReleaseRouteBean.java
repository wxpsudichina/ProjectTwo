package com.sudichina.ftwl.bean;

/**
 * Created by SudiChina-105 on 2016/9/7.
 * 路线发布实体类
 */


/**
 * /**
 * long accountId;//用户id
 * long carid;//车辆id
 * long toZoneCode;//到达地区code
 * long fromZoneCode;//开始地区code
 * String laLoPosition;//经度，纬度
 * String fromAddress;//开始详细地址
 * String releaseTime;//发车时间
 * String arriveTime;//到达时间
 * String type;//类型
 * double priceDun;//吨的单价
 * double priceSquare;//方的单价
 * String addService;//增值服务
 */
public class ReleaseRouteBean {
    private long accountId;
    private long carId;
    private long toZoneCode;
    private long fromZoneCode;
    private String laLoPosition;
    private String fromAddress;
    private String releaseTime;
    private String arriveTime;
    private String type;//all //zero
    private double priceAll;
    private double priceKg;
    private double priceDun;
    private double priceSquare;
    private String addService;//door_door door_station line_invoice

    public ReleaseRouteBean(long accountId, long carId, long toZoneCode, long fromZoneCode, String laLoPosition, String fromAddress, String releaseTime, String arriveTime, String type, double priceAll, double priceKg, double priceDun, double priceSquare, String addService) {
        this.accountId = accountId;
        this.carId = carId;
        this.toZoneCode = toZoneCode;
        this.fromZoneCode = fromZoneCode;
        this.laLoPosition = laLoPosition;
        this.fromAddress = fromAddress;
        this.releaseTime = releaseTime;
        this.arriveTime = arriveTime;
        this.type = type;
        this.priceAll = priceAll;
        this.priceKg = priceKg;
        this.priceDun = priceDun;
        this.priceSquare = priceSquare;
        this.addService = addService;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getCarId() {
        return carId;
    }

    public void setCarId(long carId) {
        this.carId = carId;
    }

    public long getToZoneCode() {
        return toZoneCode;
    }

    public void setToZoneCode(long toZoneCode) {
        this.toZoneCode = toZoneCode;
    }

    public long getFromZoneCode() {
        return fromZoneCode;
    }

    public void setFromZoneCode(long fromZoneCode) {
        this.fromZoneCode = fromZoneCode;
    }

    public String getLaLoPosition() {
        return laLoPosition;
    }

    public void setLaLoPosition(String laLoPosition) {
        this.laLoPosition = laLoPosition;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPriceAll() {
        return priceAll;
    }

    public void setPriceAll(double priceAll) {
        this.priceAll = priceAll;
    }

    public double getPriceKg() {
        return priceKg;
    }

    public void setPriceKg(double priceKg) {
        this.priceKg = priceKg;
    }

    public double getPriceDun() {
        return priceDun;
    }

    public void setPriceDun(double priceDun) {
        this.priceDun = priceDun;
    }

    public double getPriceSquare() {
        return priceSquare;
    }

    public void setPriceSquare(double priceSquare) {
        this.priceSquare = priceSquare;
    }

    public String getAddService() {
        return addService;
    }

    public void setAddService(String addService) {
        this.addService = addService;
    }
}
