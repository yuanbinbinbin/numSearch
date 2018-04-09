package com.yb.numsearch.contract;

import com.yb.numsearch.biz.GetProvinceInfoBiz;
import com.yb.numsearch.entity.Provinces;
import com.yb.numsearch.presenter.BasePresenter;

/**
 * 获取省份信息
 * Created by yb on 2017/7/27.
 */
public interface GetProvinceInfoContract {
    interface GetPrvoinceInfoView extends BaseView<GetProvinceInfoPresenter> {
        void refreshProvinceInfo(Provinces provinces);
    }

    interface GetProvinceInfoPresenter extends BasePresenter {
        void getProvinceInfo();
    }
}
