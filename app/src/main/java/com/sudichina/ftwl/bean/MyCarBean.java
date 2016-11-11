package com.sudichina.ftwl.bean;

import java.util.List;

/**
 * Created by mccccccmike on 2016/9/6.
 */
public class MyCarBean {
    private CarBean car;
    private DicGroupBean dicGroup;
    private List<DicValueBean> dicValue;

    public MyCarBean(CarBean car, DicGroupBean dicGroup, List<DicValueBean> dicValue) {
        this.car = car;
        this.dicGroup = dicGroup;
        this.dicValue = dicValue;
    }

    public CarBean getCar() {
        return car;
    }

    public void setCar(CarBean car) {
        this.car = car;
    }

    public DicGroupBean getDicGroup() {
        return dicGroup;
    }

    public void setDicGroup(DicGroupBean dicGroup) {
        this.dicGroup = dicGroup;
    }

    public List<DicValueBean> getDicValue() {
        return dicValue;
    }

    public void setDicValue(List<DicValueBean> dicValue) {
        this.dicValue = dicValue;
    }
}
