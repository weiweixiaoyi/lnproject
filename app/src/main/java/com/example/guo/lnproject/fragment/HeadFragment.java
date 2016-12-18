package com.example.guo.lnproject.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guo.lnproject.R;
import com.example.guo.lnproject.activity.HeadMethodActivity;
import com.example.guo.lnproject.activity.TimeRemindActivity;
import com.example.guo.lnproject.utils.Contacts;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/3/16.
 */
public class HeadFragment extends Fragment{

    private static final String TAG = "HeadFragment";
    private View convertView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        convertView = inflater.inflate(R.layout.fragment_head, null);
        ButterKnife.bind(this, convertView);
        return convertView;
    }

    @OnClick({R.id.laozhenLayout,R.id.shubiaoshouLayout,R.id.bozisuantongLayout,R.id.jianbeijiangyingyingLayout,R.id.head_clockLayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.laozhenLayout:
                HeadMethodActivity.startAction(getActivity(), HeadMethodActivity.TYPE_LAOZHEN);
                break;
            case R.id.shubiaoshouLayout:
                HeadMethodActivity.startAction(getActivity(), HeadMethodActivity.TYPE_SHUBIAOSHOU);
                break;
            case R.id.bozisuantongLayout:
                HeadMethodActivity.startAction(getActivity(), HeadMethodActivity.TYPE_BOZISUANTENG);
                break;
            case R.id.jianbeijiangyingyingLayout:
                HeadMethodActivity.startAction(getActivity(), HeadMethodActivity.TYPE_JIANBENJIANGYING);
                break;
            case R.id.head_clockLayout:
                TimeRemindActivity.startAction(getActivity(), Contacts.TYPE_HEAD);
                break;

        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
