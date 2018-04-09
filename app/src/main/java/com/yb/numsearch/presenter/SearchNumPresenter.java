package com.yb.numsearch.presenter;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.yb.numsearch.biz.Paths;
import com.yb.numsearch.biz.SearchNumsBiz;
import com.yb.numsearch.biz.base.RetrofitUtils;
import com.yb.numsearch.contract.SearchNumContract;
import com.yb.numsearch.entity.Num;
import com.yb.numsearch.entity.Numbers;

import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yb on 2017/7/26.
 */
public class SearchNumPresenter implements SearchNumContract.SearchNumPresenter {
    private SearchNumContract.SearchNumView searchNumView;
    private SearchNumsBiz searchNumsBiz;
    private String provinceCode;
    private String cityCode;
    private String groupKey;
    private boolean isStart;
    private int refreshCount;
    private int notRefreshTimes;

    public SearchNumPresenter(SearchNumContract.SearchNumView searchNumView) {
        if (searchNumView == null) {
            return;
        }
        this.isStart = false;
        this.notRefreshTimes = 0;
        searchNumsBiz = RetrofitUtils.getInstence(Paths.BASE_PATH_1).create(SearchNumsBiz.class);
        this.searchNumView = searchNumView;
        this.searchNumView.setPresenter(this);
    }

    @Override
    public void start() {
        if (searchNumView == null || isStart) {
            return;
        }
        this.provinceCode = searchNumView.getProvinceCode();
        this.cityCode = searchNumView.getCityCode();
        this.groupKey = searchNumView.getGroupKey();
        if (TextUtils.isEmpty(cityCode) || TextUtils.isEmpty(provinceCode) || TextUtils.isEmpty(groupKey)) {
            searchNumView.showToast("参数错误");
            return;
        }
        if (searchNumsBiz == null || isStart) {
            return;
        }
        isStart = true;
        startSearchNum();
    }

    @Override
    public void startSearchNum() {
        refreshCount = 0;
        searchNumsBiz.getNums(provinceCode, cityCode, groupKey, "" + System.currentTimeMillis())
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<ResponseBody, Observable<String>>() {
                    @Override
                    public Observable<String> call(ResponseBody response) {
                        try {
                            String json = response.string();
                            if (TextUtils.isEmpty(json)) {
                                throw new NullPointerException("result is empty");
                            }
                            json = json.replace("jsonp_queryMoreNums(", "");
                            json = json.replace(");", "");
                            Gson gson = new Gson();
                            Numbers nums = gson.fromJson(json, Numbers.class);
                            return Observable.from(nums.getNumArray());
                        } catch (Exception e) {
                            e.printStackTrace();
                            return Observable.error(e);
                        }
                    }
                })
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return !(TextUtils.isEmpty(s) || s.length() < 11);
                    }
                })
                .map(new Func1<String, Num>() {
                    @Override
                    public Num call(String s) {
                        return parseNums(s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Num>() {
                    @Override
                    public void onCompleted() {
                        new Handler().postDelayed(runnable, 3000);
                        if (refreshCount > 0) {
                            if (searchNumView != null) {
                                searchNumView.showToast("本次更新数据：" + refreshCount + "个");
                                notRefreshTimes = 0;
                            }
                        } else {
                            if (searchNumView != null) {
                                notRefreshTimes++;
                                searchNumView.showToast("第" + notRefreshTimes + "次没有新数据");
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.e("test", "error" + Thread.currentThread());
                        new Handler().postDelayed(runnable, 3000);
                    }

                    @Override
                    public void onNext(Num num) {
                        if (num == null) {
                            return;
                        }
                        if (searchNumView != null) {
                            refreshCount += searchNumView.addNums(num);
                        }
                    }
                });
    }

    private Num parseNums(String num) {
        if (num != null && num.length() >= 11) {
            String regEx = "\\d*([\\d])\\1{2}\\d*";//3A
            boolean isMatch = Pattern.matches(regEx, num);
            if (isMatch) {
                regEx = "\\d*([\\d])\\1{3}\\d*";//4A
                isMatch = Pattern.matches(regEx, num);
                if (isMatch) {
                    regEx = "\\d*([\\d])\\1{4}\\d*";//5A
                    isMatch = Pattern.matches(regEx, num);
                    if (isMatch) {
                        return new Num(num, Num.TYPE_5A);
                    } else {
                        return new Num(num, Num.TYPE_4A);
                    }
                } else {
                    return new Num(num, Num.TYPE_3A);
                }
            } else {
                regEx = "\\d*(?:0(?=1)|1(?=2)|2(?=3)|3(?=4)|4(?=5)|5(?=6)|6(?=7)|7(?=8)|8(?=9)){3}\\d\\d*";//ABC+
                isMatch = Pattern.matches(regEx, num);
                if (isMatch) {
                    return new Num(num, Num.TYPE_ABC);
                } else {
                    regEx = "\\d*(?:9(?=8)|8(?=7)|7(?=6)|6(?=5)|5(?=4)|4(?=3)|3(?=2)|2(?=1)|1(?=0)){3}\\d\\d*";//ZYX-
                    isMatch = Pattern.matches(regEx, num);
                    if (isMatch) {
                        return new Num(num, Num.TYPE_ZYX);
                    } else {
                        regEx = "\\d*([\\d])\\1{1}([\\d])\\2{1}([\\d])\\3{1}\\d*";//AABBCC类型
                        isMatch = Pattern.matches(regEx, num);
                        if (isMatch) {
                            return new Num(num, Num.TYPE_AABBCC);
                        } else {

                        }
                    }
                }
            }
        }
        return null;
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            startSearchNum();
        }
    };
}
