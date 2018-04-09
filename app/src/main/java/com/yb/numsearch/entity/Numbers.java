package com.yb.numsearch.entity;

import java.util.List;

/**
 * Created by yb on 2017/7/25.
 */
public class Numbers {

    private String splitLen;
    private String uuid;
    private List<String> numArray;

    public String getSplitLen() {
        return splitLen;
    }

    public void setSplitLen(String splitLen) {
        this.splitLen = splitLen;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<String> getNumArray() {
        return numArray;
    }

    public void setNumArray(List<String> numArray) {
        this.numArray = numArray;
    }

    @Override
    public String toString() {
        return "Numbers{" +
                "splitLen='" + splitLen + '\'' +
                ", uuid='" + uuid + '\'' +
                ", numArray=" + numArray +
                '}';
    }
}
