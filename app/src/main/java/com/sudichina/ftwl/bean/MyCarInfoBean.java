package com.sudichina.ftwl.bean;

import java.util.List;

/**
 * Created by mccccccmike on 2016/9/2.
 */
public class MyCarInfoBean {
    private DicGroupBean dicGroup;
    private List<DicValueBean> dicValue;

    public MyCarInfoBean(DicGroupBean dicGroup, List<DicValueBean> dicValue) {
        this.dicGroup = dicGroup;
        this.dicValue = dicValue;
    }

    public List<DicValueBean> getDicValue() {
        return dicValue;
    }

    public void setDicValue(List<DicValueBean> dicValue) {
        this.dicValue = dicValue;
    }

    public DicGroupBean getDicGroup() {
        return dicGroup;
    }

    public void setDicGroup(DicGroupBean dicGroup) {
        this.dicGroup = dicGroup;
    }
}
