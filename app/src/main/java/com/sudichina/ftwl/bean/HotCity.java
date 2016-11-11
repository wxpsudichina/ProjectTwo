package com.sudichina.ftwl.bean;

/**
 * Created by SudiChina-105 on 2016/9/2.
 * 热门城市
 */
public class HotCity {
    private Long id;
    private String name;

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

    public HotCity(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
