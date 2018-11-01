package com.example.auto1.utils;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDialogUtils {

    private static ProgressDialog progressDialog;

    public static void initProgressDialog(Context context, String msg) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(msg);
    }

    public static void showProgressDialog() {
        if (progressDialog != null) {
            progressDialog.show();
        }
    }

    public static void dismissProgressDialog() {
        if (progressDialog != null) {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
        }
    }
}
