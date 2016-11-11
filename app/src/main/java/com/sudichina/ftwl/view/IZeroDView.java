package com.sudichina.ftwl.view;

/**
 * Created by mccccccmike on 2016/8/30.
 */
public interface IZeroDView {
    long getAccountId();

    public long getCarid();

    public long getToZoneCode();

    public long getFromZoneCode();

    public String getLaLoPosition();

    public String getFromAddress();

    public String getReleaseTime();

    public String getArriveTime();

    public String getType();

    public double getPriceDun() ;

    public double getPriceSquare();

    public String getAddService();
}
