package com.example.guo.lnproject.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.guo.lnproject.R;
import com.example.guo.lnproject.base.BaseActivity;
import com.example.guo.lnproject.bean.PillSearchInfoEntity;
import com.example.guo.lnproject.utils.LogUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2016/4/25 0025.
 */
public class SearchPillActivity extends BaseActivity {

    private static final String TAG = "SearchPillActivity";
    @Bind(R.id.searchpill_editText)
    public EditText editText;
    @Bind(R.id.searchpill_nameTv)
    public TextView nameTv;
    @Bind(R.id.searchpill_img)
    public ImageView img;
    @Bind(R.id.searchpill_descTv)
    public TextView descTv;
    @Bind(R.id.searchpill_messageTv)
    public TextView messageTv;
    @Bind(R.id.searchpill_typeTv)
    public TextView typeTv;
    @Bind(R.id.searchpill_webView)
    public WebView webView;
    private InputMethodManager mInputManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchpill);
        ButterKnife.bind(this);
        mInputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        webView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mInputManager != null && getCurrentFocus() != null) {
                    mInputManager.hideSoftInputFromWindow(SearchPillActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }

                return false;
            }
        });
    }

    @OnClick({R.id.searchpill_searchTv,R.id.searchpill_backImg})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.searchpill_backImg:
                finish();
                break;
            case R.id.searchpill_searchTv:
                loadData(editText.getText().toString());
                break;
        }
    }

    private void loadData(String name) {
        //http://tnfs.tngou.net/image
        String url = "http://www.tngou.net/api/drug/name?name="+name;
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                LogUtils.i(TAG, "msg"+e.getMessage());
            }

            @Override
            public void onResponse(String s) {
                LogUtils.i(TAG, "entity---" + s);
                PillSearchInfoEntity entity = JSON.parseObject(s, PillSearchInfoEntity.class);
                if (entity != null) {
                    LogUtils.i(TAG, "entity---" + entity.toString());
//                    setData(entity);
                    webView.loadUrl(entity.getUrl());
                }

            }
        });
    }


   /* private void setData(PillSearchInfoEntity entity){
        nameTv.setText(entity.getName());
        Glide.with(SearchPillActivity.this).load(entity.getImg()).into(img);
        descTv.setText(entity.getDescription());
        messageTv.setText(entity.getMessage());
        typeTv.setText(entity.getType());
    }*/
}
