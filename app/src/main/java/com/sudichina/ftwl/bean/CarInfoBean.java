package com.sudichina.ftwl.bean;

import java.util.List;

/**
 * Created by mccccccmike on 2016/8/30.
 */
public class CarInfoBean {
    private List<DicGroupBean> dicGroup;

    private List<DicValueBean> dicValue;

    public CarInfoBean(List<DicGroupBean> dicGroup, List<DicValueBean> dicValue) {
        this.dicGroup = dicGroup;
        this.dicValue = dicValue;
    }
    public List<DicGroupBean> getDicGroup() {
        return dicGroup;
    }

    public void setDicGroup(List<DicGroupBean> dicGroup) {
        this.dicGroup = dicGroup;
    }

    public List<DicValueBean> getDicValue() {
        return dicValue;
    }

    public void setDicValue(List<DicValueBean> dicValue) {
        this.dicValue = dicValue;
    }
}
