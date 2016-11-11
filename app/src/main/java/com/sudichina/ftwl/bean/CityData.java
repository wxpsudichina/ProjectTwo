package com.sudichina.ftwl.bean;

import java.util.List;

/**
 * Created by SudiChina-105 on 2016/9/3.
 * //城市集合
 */
public class CityData {
    private String aleph;
    private List<CityBean> listZone;

    public CityData(String firstAleph, List<CityBean> listZone) {
        this.aleph = firstAleph;
        this.listZone = listZone;
    }
    public String getFirstAleph() {
        return aleph;
    }

    public void setFirstAleph(String firstAleph) {
        this.aleph = firstAleph;
    }

    public List<CityBean> getListZone() {
        return listZone;
    }

    public void setListZone(List<CityBean> listZone) {
        this.listZone = listZone;
    }
}
