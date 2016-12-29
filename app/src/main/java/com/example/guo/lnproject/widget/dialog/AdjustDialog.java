package com.example.guo.lnproject.widget.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.guo.lnproject.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by gmw on 16/12/28.
 */

public class AdjustDialog extends BaseDialog
{

    @Bind(R.id.et_adjust_goal)
    EditText etAdjustGoal;

    @Bind(R.id.et_curr_goal)
    EditText etCurrGoal;

    @Bind(R.id.tv_back)
    TextView tvBack;

    @Bind(R.id.tv_confirm)
    TextView tvConfirm;

    private CallBackDialog mCallback;


    public AdjustDialog (Activity activity)
    {

        super(activity);
        ButterKnife.bind(this);
    }

    @Override
    protected int contentView ()
    {

        return R.layout.dialog_adjust_goal;
    }

    public void setCallback (CallBackDialog callback)
    {

        this.mCallback = callback;
    }

    public interface CallBackDialog
    {
        void completedConfirm ();

        void completedCancel ();
    }

    @OnClick({R.id.tv_back, R.id.tv_confirm})
    public void onClick (View view)
    {

        switch (view.getId())
        {
            case R.id.tv_back:
                dismiss();
                if (mCallback != null)
                    mCallback.completedCancel();
                break;
            case R.id.tv_confirm:
                dismiss();
                if (mCallback != null)
                    mCallback.completedConfirm();
                break;
        }
    }

    public String getAdjustGoal ()
    {

        return etAdjustGoal.getText().toString().trim();
    }

    public void setAdjustGoalText (String str)
    {

        etAdjustGoal.setText(str);
    }

    public void setCurrGoalText (String str)
    {

        etCurrGoal.setText(str);
    }
}
