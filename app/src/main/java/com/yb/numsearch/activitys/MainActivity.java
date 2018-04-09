package com.yb.numsearch.activitys;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yb.numsearch.R;

import java.util.List;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private boolean isStart = false;
    private TextView mTv3a;
    private TextView mTv4a;
    private TextView mTv5a;
    private TextView mTvabc;
    private TextView mTvcba;
    private TextView mTvaabbcc;
    private RequestQueue mQuene;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTv3a = (TextView) findViewById(R.id.a3);
        mTv4a = (TextView) findViewById(R.id.a4);
        mTv5a = (TextView) findViewById(R.id.a5);
        mTvabc = (TextView) findViewById(R.id.abc);
        mTvcba = (TextView) findViewById(R.id.cba);
        mTvaabbcc = (TextView) findViewById(R.id.aabbcc);
        mQuene = Volley.newRequestQueue(this);
    }

    public void startSearchBJ(View view) {
        if (isStart) {
            return;
        }
        isStart = true;
//e卡重庆
//        url = "https://m.10010.com/NumApp/NumberCenter/qryNum?callback=jsonp_queryMoreNums&provinceCode=83&cityCode=831&monthFeeLimit=0&groupKey=99250240&searchCategory=3&net=01&amounts=200&codeTypeCode=&searchValue=&qryType=02&goodsNet=4&_=";
//e卡北京
        url = "http://m.10010.com/NumApp/NumberCenter/qryNum?callback=jsonp_queryMoreNums&provinceCode=11&cityCode=110&monthFeeLimit=0&groupKey=30242833&searchCategory=3&net=01&amounts=200&codeTypeCode=&searchValue=&qryType=02&goodsNet=4&_=";
//   宝卡背北京     url="http://m.10010.com/NumApp/NumberCenter/qryNum?callback=jsonp_queryMoreNums&provinceCode=11&cityCode=110&monthFeeLimit=0&groupKey=58242834&searchCategory=3&net=01&amounts=200&codeTypeCode=&searchValue=&qryType=02&goodsNet=4&_=";
        startSearch();
    }

    public void startSearchSJZ(View view) {
        if (isStart) {
            return;
        }
        isStart = true;
        url = "http://m.10010.com/NumApp/NumberCenter/qryNum?callback=jsonp_queryMoreNums&provinceCode=18&cityCode=188&monthFeeLimit=0&groupKey=15237219&searchCategory=3&net=01&amounts=200&codeTypeCode=&searchValue=&qryType=02&goodsNet=4&_=";
        startSearch();
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (!isStart) {
                return;
            }
            startSearch();
        }
    };
    String url;

    private void startSearch() {
        StringRequest stringRequest = new StringRequest(url + System.currentTimeMillis(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    response = response.replace("jsonp_queryMoreNums(", "");
                    response = response.replace(");", "");
                    try {
                        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(response);
                        List<String> nums = JSON.parseArray(jsonObject.getString("numArray"), String.class);
                        parseNums(nums);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                new Handler().postDelayed(runnable, 1000);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("test", "error:" + error.getMessage());
                new Handler().postDelayed(runnable, 1000);
            }
        });
        mQuene.add(stringRequest);
    }

    int change = 0;

    private void parseNums(List<String> nums) {
        boolean isChange = false;
        if (nums != null && nums.size() > 0) {
            for (String num : nums) {
                if (num != null && num.length() >= 11) {
                    Log.e("test", num);
                    String regEx = "\\d*([\\d])\\1{2}\\d*";//3A
                    boolean isMatch = Pattern.matches(regEx, num);
                    if (isMatch) {
                        regEx = "\\d*([\\d])\\1{3}\\d*";//4A
                        isMatch = Pattern.matches(regEx, num);
                        if (isMatch) {
                            regEx = "\\d*([\\d])\\1{4}\\d*";//5A
                            isMatch = Pattern.matches(regEx, num);
                            if (isMatch) {
                                if (!mTv5a.getText().toString().contains(num)) {
                                    mTv5a.append(num);
                                    mTv5a.append("\n");
                                    sendNotification("5A", num);
                                    isChange = true;
                                }
                            } else {
                                if (!mTv4a.getText().toString().contains(num)) {
                                    mTv4a.append(num);
                                    mTv4a.append("\n");
                                    sendNotification("4A", num);
                                    isChange = true;
                                }
                            }
                        } else {
                            if ((num.contains("999")||num.contains("666") ||num.contains("888")) && !mTv3a.getText().toString().contains(num)) {
                                mTv3a.append(num);
                                mTv3a.append("\n");
//                            sendNotification("3A", num);
                                isChange = true;
                            }
                        }
                    } else {
                        regEx = "\\d*(?:0(?=1)|1(?=2)|2(?=3)|3(?=4)|4(?=5)|5(?=6)|6(?=7)|7(?=8)|8(?=9)){3}\\d\\d*";//ABC+
                        isMatch = Pattern.matches(regEx, num);
                        if (isMatch) {
                            if (!mTvabc.getText().toString().contains(num)) {
                                mTvabc.append(num);
                                mTvabc.append("\n");
//                            sendNotification("ABC+", num);
                                isChange = true;
                            }
                        } else {
                            regEx = "\\d*(?:9(?=8)|8(?=7)|7(?=6)|6(?=5)|5(?=4)|4(?=3)|3(?=2)|2(?=1)|1(?=0)){3}\\d\\d*";//ZYX-
                            isMatch = Pattern.matches(regEx, num);
                            if (isMatch) {
                                if (!mTvcba.getText().toString().contains(num)&&!num.contains("3210")) {
                                    mTvcba.append(num);
                                    mTvcba.append("\n");
//                                sendNotification("ZYX-", num);
                                    isChange = true;
                                }
                            } else {
                                regEx = "\\d*([\\d])\\1{1}([\\d])\\2{1}([\\d])\\3{1}\\d*";//AABBCC类型
                                isMatch = Pattern.matches(regEx, num);
                                if (isMatch) {
                                    if (!mTvaabbcc.getText().toString().contains(num)) {
                                        mTvaabbcc.append(num);
                                        mTvaabbcc.append("\n");
//                                    sendNotification("AABBCC", num);
                                        isChange = true;
                                    }
                                } else {

                                }
                            }
                        }
                    }
                }
            }
        }
        if (!isChange) {
            change++;
            Toast.makeText(this, "这次并没有更新哦" + change, Toast.LENGTH_SHORT).show();
        } else {
            change = 0;
        }
    }

    private void sendNotification(String title, String num) {
        if (num.contains("111")) {
            return;
        }
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 11, new Intent(this, MainActivity.class), 0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle(title)
                .setContentText(num)
                .setTicker("靓号找到啦")
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setSmallIcon(R.mipmap.ic_launcher).setContentIntent(pendingIntent);
        manager.notify((int) System.currentTimeMillis(), mBuilder.build());
    }

}
