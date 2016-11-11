package com.sudichina.ftwl.bean;

/**
 * Created by SudiChina-105 on 2016/9/2.
 * 城市信息 ，包含城市名称，城市的拼音，城市对应点id
 */
public class CityBean {
    private Long id;
    private String name;
    private String firstAleph;

    public CityBean(Long id, String name, String firstAleph) {
        this.id = id;
        this.name = name;
        this.firstAleph = firstAleph;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstAleph() {
        return firstAleph;
    }

    public void setFirstAleph(String firstAleph) {
        this.firstAleph = firstAleph;
    }
}
