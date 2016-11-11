package com.sudichina.ftwl.bean;

import java.util.List;

/**
 * Created by SudiChina-105 on 2016/9/3.
    */
    public class CityItems {
        private List<CityData> itemMap;
        private List<HotCity> hotZone;

        public CityItems(List<CityData> itemMap, List<HotCity> hotZone) {
            this.itemMap = itemMap;
            this.hotZone = hotZone;
        }

        public List<CityData> getItemMap() {
            return itemMap;
        }

    public void setItemMap(List<CityData> itemMap) {
        this.itemMap = itemMap;
    }

    public List<HotCity> getHotZone() {
        return hotZone;
    }

    public void setHotZone(List<HotCity> hotZone) {
        this.hotZone = hotZone;
    }
}
