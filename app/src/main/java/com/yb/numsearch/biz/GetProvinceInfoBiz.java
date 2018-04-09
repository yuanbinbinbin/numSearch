package com.yb.numsearch.biz;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import rx.Observable;

/**
 * 获取省份信息
 * Created by yb on 2017/7/27.
 */
public interface GetProvinceInfoBiz {
    @GET("king/kingNumCard/icbcinit?product=2&channel=1")
    Observable<ResponseBody> getProvinceInfo();
}
