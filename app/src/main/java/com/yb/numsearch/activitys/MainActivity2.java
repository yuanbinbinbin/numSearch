package com.yb.numsearch.activitys;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yb.numsearch.R;
import com.yb.numsearch.biz.SearchNumsBiz;
import com.yb.numsearch.contract.GetProvinceInfoContract;
import com.yb.numsearch.contract.SearchNumContract;
import com.yb.numsearch.entity.Num;
import com.yb.numsearch.entity.Provinces;
import com.yb.numsearch.presenter.GetProvinceInfoPresenter;
import com.yb.numsearch.presenter.SearchNumPresenter;

public class MainActivity2 extends BaseActivity {

    private TextView mTv3a;
    private TextView mTv4a;
    private TextView mTv5a;
    private TextView mTvabc;
    private TextView mTvcba;
    private TextView mTvaabbcc;

    private String provinceCode;
    private String cityCode;
    private String groupKey;
    private SearchNumContract.SearchNumPresenter searchNumPresenter;
    private GetProvinceInfoContract.GetProvinceInfoPresenter getProvinceInfoPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mTv3a = (TextView) findViewById(R.id.a3);
        mTv4a = (TextView) findViewById(R.id.a4);
        mTv5a = (TextView) findViewById(R.id.a5);
        mTvabc = (TextView) findViewById(R.id.abc);
        mTvcba = (TextView) findViewById(R.id.cba);
        mTvaabbcc = (TextView) findViewById(R.id.aabbcc);
    }

    @Override
    protected void initDate() {
//        new SearchNumPresenter(this);
//        getProvinceInfoPresenter = new GetProvinceInfoPresenter(this);
    }

    @Override
    protected void initListener() {

    }

    public void startSearchBJ(View view) {
        Log.e("test", "search BJ NUM");
        provinceCode = "11";
        cityCode = "110";
        groupKey = "30242833";
        url = "http://m.10010.com/NumApp/NumberCenter/qryNum?callback=jsonp_queryMoreNums&provinceCode=11&cityCode=110&monthFeeLimit=0&groupKey=30242833&searchCategory=3&net=01&amounts=200&codeTypeCode=&searchValue=&qryType=02&goodsNet=4&_=";
        startSearch();
    }

    public void startSearchSJZ(View view) {

        provinceCode = "18";
        cityCode = "188";
        groupKey = "15237219";
        url = "http://m.10010.com/NumApp/NumberCenter/qryNum?callback=jsonp_queryMoreNums&provinceCode=18&cityCode=188&monthFeeLimit=0&groupKey=15237219&searchCategory=3&net=01&amounts=200&codeTypeCode=&searchValue=&qryType=02&goodsNet=4&_=";
        startSearch();
    }


    String url;

    private void startSearch() {
        Log.e("test", "start search");
        if (searchNumPresenter != null) {
            Log.e("test", "start search != null");
            searchNumPresenter.start();
        } else {
            Log.e("test", "presenter is null");
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

//    @Override
//    public String getProvinceCode() {
//        return provinceCode;
//    }
//
//    @Override
//    public String getCityCode() {
//        return cityCode;
//    }
//
//    @Override
//    public String getGroupKey() {
//        return groupKey;
//    }
//
//    @Override
//    public int addNums(Num num) {
//        if (num == null || !TextUtils.isEmpty(num.getNum())) {
//            return 0;
//        }
//        int result = 0;
//        switch (num.getType()) {
//            case Num.TYPE_SPECIAL:
//                break;
//            case Num.TYPE_3A:
//                if (!mTv3a.getText().toString().contains(num.getNum())) {
//                    mTv3a.append(num.getNum());
//                    mTv3a.append("\n");
//                    result = 1;
//                }
//                result = 0;
//                break;
//            case Num.TYPE_4A:
//                if (!mTv4a.getText().toString().contains(num.getNum())) {
//                    mTv4a.append(num.getNum());
//                    mTv4a.append("\n");
//                    result = 1;
//                }
//                result = 0;
//                break;
//            case Num.TYPE_5A:
//                if (!mTv5a.getText().toString().contains(num.getNum())) {
//                    mTv5a.append(num.getNum());
//                    mTv5a.append("\n");
//                    result = 1;
//                }
//                result = 0;
//                break;
//            case Num.TYPE_ABC:
//                if (!mTvabc.getText().toString().contains(num.getNum())) {
//                    mTvabc.append(num.getNum());
//                    mTvabc.append("\n");
//                    result = 1;
//                }
//                result = 0;
//                break;
//            case Num.TYPE_ZYX:
//                if (!mTvcba.getText().toString().contains(num.getNum())) {
//                    mTvcba.append(num.getNum());
//                    mTvcba.append("\n");
//                    result = 1;
//                }
//                result = 0;
//                break;
//            case Num.TYPE_AABBCC:
//                if (!mTvaabbcc.getText().toString().contains(num.getNum())) {
//                    mTvaabbcc.append(num.getNum());
//                    mTvaabbcc.append("\n");
//                    result = 1;
//                }
//                result = 0;
//                break;
//        }
//        return result;
//    }
//
//    @Override
//    public void setPresenter(SearchNumContract.SearchNumPresenter presenter) {
//        searchNumPresenter = presenter;
//    }
//
//    @Override
//    public void setPresenter(GetProvinceInfoContract.GetProvinceInfoPresenter presenter) {
//        this.getProvinceInfoPresenter = presenter;
//    }
//
//    @Override
//    public void showToast(String msg) {
//        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void refreshProvinceInfo(Provinces provinces) {
//
//    }
}
