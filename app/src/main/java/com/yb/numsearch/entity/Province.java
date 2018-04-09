package com.yb.numsearch.entity;

/**
 * Created by yb on 2017/7/27.
 */
public class Province {
    private String PROVINCE_NAME;
    private String PROVINCE_CODE;
    private String PRO_ORDER_NUMBER;

    public Province() {
    }

    public Province(String PROVINCE_NAME, String PROVINCE_CODE, String PRO_ORDER_NUMBER) {
        this.PROVINCE_NAME = PROVINCE_NAME;
        this.PROVINCE_CODE = PROVINCE_CODE;
        this.PRO_ORDER_NUMBER = PRO_ORDER_NUMBER;
    }

    public String getPROVINCE_NAME() {
        return PROVINCE_NAME;
    }

    public void setPROVINCE_NAME(String PROVINCE_NAME) {
        this.PROVINCE_NAME = PROVINCE_NAME;
    }

    public String getPROVINCE_CODE() {
        return PROVINCE_CODE;
    }

    public void setPROVINCE_CODE(String PROVINCE_CODE) {
        this.PROVINCE_CODE = PROVINCE_CODE;
    }

    public String getPRO_ORDER_NUMBER() {
        return PRO_ORDER_NUMBER;
    }

    public void setPRO_ORDER_NUMBER(String PRO_ORDER_NUMBER) {
        this.PRO_ORDER_NUMBER = PRO_ORDER_NUMBER;
    }

    @Override
    public String toString() {
        return "Province{" +
                "PROVINCE_NAME='" + PROVINCE_NAME + '\'' +
                ", PROVINCE_CODE='" + PROVINCE_CODE + '\'' +
                ", PRO_ORDER_NUMBER='" + PRO_ORDER_NUMBER + '\'' +
                '}';
    }
}
