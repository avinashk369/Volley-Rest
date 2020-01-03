package com.techcamino.pay.alipay.util;

import android.app.ProgressDialog;
import android.content.Context;

import com.techcamino.pay.alipay.R;


public class Util {


	/* Progress Bar */
    public static ProgressDialog getProgressDialog(Context context, String message) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage(message);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setIndeterminate(false);
        //dialog.setIndeterminateDrawable(context.getResources().getDrawable(R.drawable.logo));
        dialog.setCancelable(false);
        return dialog;
    }
    /* End of progress Bar*/
}
