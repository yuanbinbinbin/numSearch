package com.yb.numsearch.contract;

import com.yb.numsearch.entity.Num;
import com.yb.numsearch.presenter.BasePresenter;

/**
 * 查找号码的关联接口
 * Created by yb on 2017/7/27.
 */
public interface SearchNumContract {
    interface SearchNumView extends BaseView<SearchNumPresenter> {
        String getProvinceCode();

        String getCityCode();

        String getGroupKey();

        int addNums(Num num);

    }

    interface SearchNumPresenter extends BasePresenter {
        void startSearchNum();
    }

    ;
}
