package com.yb.numsearch.biz;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by yb on 2017/7/25.
 */
public interface SearchNumsBiz {

    @GET("NumApp/NumberCenter/qryNum?callback=jsonp_queryMoreNums&monthFeeLimit=0&searchCategory=3&net=01&amounts=200&codeTypeCode=&searchValue=&qryType=02&goodsNet=4")
    Observable<ResponseBody> getNums(@Query("provinceCode") String provinceCode, @Query("cityCode") String cityCode, @Query("groupKey") String groupKey, @Query("_") String time);

}
