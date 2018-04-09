package com.yb.numsearch.entity;

import java.util.List;
import java.util.Map;

/**
 * 省份的集合
 * Created by yb on 2017/7/27.
 */
public class Provinces {
    private List<Province> provinces;
    private Map<String, String> groupNums;
    private Map<String, List<City>> citys;
    private String goodsId;

    public Provinces() {
    }

    public Provinces(List<Province> provinces, Map<String, String> groupNums, Map<String, List<City>> citys, String goodsId) {
        this.provinces = provinces;
        this.groupNums = groupNums;
        this.citys = citys;
        this.goodsId = goodsId;
    }

    public List<Province> getProvinces() {
        return provinces;
    }

    public void setProvinces(List<Province> provinces) {
        this.provinces = provinces;
    }

    public Map<String, String> getGroupNums() {
        return groupNums;
    }

    public void setGroupNums(Map<String, String> groupNums) {
        this.groupNums = groupNums;
    }

    public Map<String, List<City>> getCitys() {
        return citys;
    }

    public void setCitys(Map<String, List<City>> citys) {
        this.citys = citys;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    @Override
    public String toString() {
        return "Provinces{" +
                "provinces=" + provinces +
                ", groupNums=" + groupNums +
                ", citys=" + citys +
                ", goodsId='" + goodsId + '\'' +
                '}';
    }
}
