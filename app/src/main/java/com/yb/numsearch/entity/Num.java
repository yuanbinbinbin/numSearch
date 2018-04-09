package com.yb.numsearch.entity;

/**
 * Created by yb on 2017/7/26.
 */
public class Num {
    public static final int TYPE_3A = 0;
    public static final int TYPE_4A = 1;
    public static final int TYPE_5A = 2;
    public static final int TYPE_ABC = 3;
    public static final int TYPE_ZYX = 4;
    public static final int TYPE_AABBCC = 5;
    public static final int TYPE_SPECIAL = 6;
    private int type;
    private String num;

    public Num(String num, int type) {
        this.num = num;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "Num{" +
                "type=" + type +
                ", num='" + num + '\'' +
                '}';
    }
}
