package com.yy.only.onekey.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.yy.only.onekey.R;


/**
 * Created by Administrator on 2015/4/28 0028.
 */
public class ConfirmDialog extends Dialog {
    private View.OnClickListener mPositiveListener;
    public ConfirmDialog(Context context,View.OnClickListener listener) {
        this(context, R.style.style_of_dialog);
        this.mPositiveListener = listener;
        this.setContentView(R.layout.layout_of_confirm_dialog);
        this.setCanceledOnTouchOutside(true);
        this.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                mPositiveListener.onClick(v);
            }
        });
        this.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
    public ConfirmDialog(Context context, int theme) {
        super(context, theme);
    }

    public void setMessage(int res){
        TextView textView = (TextView)this.findViewById(R.id.content);
        textView.setText(res);
    }
    public void setTitle(int res){
        TextView textView = (TextView)this.findViewById(R.id.title);
        textView.setText(res);
    }
}
