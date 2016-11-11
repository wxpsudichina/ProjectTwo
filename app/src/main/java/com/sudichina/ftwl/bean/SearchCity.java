package com.sudichina.ftwl.bean;

/**
 * Created by SudiChina-105 on 2016/9/7.
 */
public class SearchCity {
    private double longitude;//经度
    private double latitude;//纬度
    private String title;//信息标题

    public SearchCity(double longitude, double latitude, String title) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.title = title;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
