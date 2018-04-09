package com.yb.numsearch.presenter;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yb.numsearch.biz.GetProvinceInfoBiz;
import com.yb.numsearch.biz.base.RetrofitUtils;
import com.yb.numsearch.contract.GetProvinceInfoContract;
import com.yb.numsearch.entity.City;
import com.yb.numsearch.entity.Province;
import com.yb.numsearch.entity.Provinces;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.ResponseBody;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yb on 2017/7/27.
 */
public class GetProvinceInfoPresenter implements GetProvinceInfoContract.GetProvinceInfoPresenter {
    private GetProvinceInfoBiz getProvinceInfoBiz;
    private GetProvinceInfoContract.GetPrvoinceInfoView getPrvoinceInfoView;

    public GetProvinceInfoPresenter(GetProvinceInfoContract.GetPrvoinceInfoView getPrvoinceInfoView) {
        this.getPrvoinceInfoView = getPrvoinceInfoView;
        getProvinceInfoBiz = RetrofitUtils.getInstence().create(GetProvinceInfoBiz.class);
    }

    @Override
    public void getProvinceInfo() {
        getProvinceInfoBiz.getProvinceInfo()
                .subscribeOn(Schedulers.io())
                .map(new Func1<ResponseBody, Provinces>() {
                    @Override
                    public Provinces call(ResponseBody response) {
                        Provinces returnData = null;
                        try {
                            String result = response.string();
                            JSONObject jsonObject = new JSONObject(result);
                            JSONArray array = jsonObject.getJSONArray("provinceData");
                            //解析省份
                            List<Province> provinces = new ArrayList<Province>();
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject element = array.getJSONObject(i);
                                Province province = new Province(element.getString("PROVINCE_NAME"), element.getString("PROVINCE_CODE"), element.getString("PRO_ORDER_NUMBER"));
                                provinces.add(province);
                            }
                            //解析GroupNum
                            Map<String, String> groupMaps = new HashMap<String, String>();
                            JSONObject groupNum = jsonObject.getJSONObject("proGroupNum");
                            Iterator<String> groups = groupNum.keys();
                            while (groups.hasNext()) {
                                String key = groups.next();
                                groupMaps.put(key, groupNum.getString(key));
                            }
                            //解析城市
                            Map<String, List<City>> citys = new HashMap<String, List<City>>();
                            JSONObject citysJsonObject = jsonObject.getJSONObject("cityData");
                            Iterator<String> cityIds = citysJsonObject.keys();
                            while (cityIds.hasNext()) {
                                String key = cityIds.next();
                                JSONArray cityJsonArray = citysJsonObject.getJSONArray(key);
                                List<City> cityList = new ArrayList<City>();
                                for (int i = 0; i < cityJsonArray.length(); i++) {
                                    JSONObject cityJsonObject = cityJsonArray.getJSONObject(i);
                                    cityList.add(new City(cityJsonObject.getString("CITY_CODE"), cityJsonObject.getString("CITY_NAME")));
                                }
                                citys.put(key, cityList);
                            }
                            //解析商品ID
                            String goodsId = jsonObject.getString("goodsId");
                            returnData = new Provinces(provinces, groupMaps, citys, goodsId);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return returnData;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Provinces>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Provinces provinces) {
                        if (getPrvoinceInfoView != null) {
                            getPrvoinceInfoView.refreshProvinceInfo(provinces);
                        }
                    }
                });
    }

    @Override
    public void start() {

    }
}
