package com.yb.numsearch.entity;

/**
 * Created by yb on 2017/7/27.
 */
public class City {
    private String CITY_CODE;
    private String CITY_NAME;

    public City() {
    }

    public City(String CITY_CODE, String CITY_NAME) {
        this.CITY_CODE = CITY_CODE;
        this.CITY_NAME = CITY_NAME;
    }

    public String getCITY_CODE() {
        return CITY_CODE;
    }

    public void setCITY_CODE(String CITY_CODE) {
        this.CITY_CODE = CITY_CODE;
    }

    public String getCITY_NAME() {
        return CITY_NAME;
    }

    public void setCITY_NAME(String CITY_NAME) {
        this.CITY_NAME = CITY_NAME;
    }

    @Override
    public String toString() {
        return "City{" +
                "CITY_CODE='" + CITY_CODE + '\'' +
                ", CITY_NAME='" + CITY_NAME + '\'' +
                '}';
    }
}
