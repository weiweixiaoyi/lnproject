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
 * Created by gmw on 16/12/29.
 */

public class EditTextDialog extends BaseDialog
{

    @Bind(R.id.tv_title)
    TextView tvTitle;

    @Bind(R.id.tv_back)
    TextView tvBack;

    @Bind(R.id.tv_confirm)
    TextView tvConfirm;

    @Bind(R.id.et_input)
    EditText etInput;

    private CallBackDialog mCallback;

    public EditTextDialog (Activity activity)
    {

        super(activity);
        ButterKnife.bind(this);
    }

    @Override
    protected int contentView ()
    {

        return R.layout.dialog_edittext;
    }

    public interface CallBackDialog
    {
        void completedConfirm ();

        void completedCancel ();
    }

    public void setTitle (String title)
    {

        tvTitle.setText(title);
    }

    public void setTitle(int resId){
        tvTitle.setText(resId);
    }

    public String getEditTextText ()
    {

        return etInput.getText().toString().trim();
    }

    public void setCallback (CallBackDialog callback)
    {

        this.mCallback = callback;
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
}
