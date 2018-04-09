package com.yb.numsearch.biz.base;

import android.util.Log;

import com.yb.numsearch.biz.Paths;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yb on 2017/7/27.
 */
public class RetrofitUtils {
    private static final int READ_TIMEOUT = 60;//读取超时时间,单位  秒
    private static final int CONN_TIMEOUT = 12;//连接超时时间,单位  秒
    private static Retrofit mRetrofit;

    private RetrofitUtils() {
    }

    public static Retrofit getInstence() {
        return getInstence(Paths.BASE_PATH_1);
    }

    public static Retrofit getInstence(String host) {
        return new Retrofit.Builder()
                .client(initClient())//添加一个client,不然retrofit会自己默认添加一个
                .baseUrl(host)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }


    public static OkHttpClient initClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        //获取request请求
                        Request request = chain.request();

                        //添加公共参数
                        HttpUrl newUrl = request.url()
                                .newBuilder()
                                .addQueryParameter("app_name", "searchNum")
                                .addQueryParameter("app_version", "101").build();

                        //添加header
                        request = request.newBuilder()
                                .addHeader("Connection", "keep-alive")
                                .addHeader("User-Agent", "searchNumV1.0.1")
                                .url(newUrl)
                                .build();
                        return chain.proceed(request);
                    }
                })
                .connectTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(CONN_TIMEOUT, TimeUnit.SECONDS)
                .build();
        return client;
    }
}
