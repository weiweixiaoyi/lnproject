package com.example.guo.lnproject.module.pill;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Administrator on 2016/12/25.
 */

public class PillPresenterIml implements PillPresenter {
    private PillView mView;

    public PillPresenterIml(PillView mView) {
        this.mView = mView;
    }

    @Override
    public void loadCookbookData() {
        String url1 = "http://apicloud.mob.com/v1/cook/menu/search";
        OkHttpUtils.get().url(url1).addParams("key", "fdfee2e569f5")
                .addParams("name", "养生").addParams("page", "1")
                .addParams("size", "20").build().execute(new StringCallback() {

            @Override
            public void onError(Call call, Exception e) {
                mView.showToast(e.getMessage());
            }

            @Override
            public void onResponse(String s) {
                mView.updateBannerData(s);
            }
        });
    }

    public void detach(){
        mView = null;
    }
}
