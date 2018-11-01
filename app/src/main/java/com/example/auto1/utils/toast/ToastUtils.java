package com.example.auto1.utils.toast;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.auto1.R;


public class ToastUtils {

    private static ToastUtils toastUtils;
    private Activity activity;
    private TextView textView;
    private static Toast toast;


    public static ToastUtils getInstance() {
        if (toastUtils == null) {
            synchronized (ToastUtils.class) {
                toastUtils = new ToastUtils();
            }
        }
        return toastUtils;
    }


    private void createToastStructure(Context context) throws Exception {
        activity = (Activity) context;
        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) activity.findViewById(R.id.toast_layout_root));
        textView = (TextView) layout.findViewById(R.id.text);
        toast = new Toast(activity.getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 250);
        toast.setView(layout);
    }

    public void showToast(Context context, String message, ToastDuration toastDuration) {
        try {
            createToastStructure(context);
            if (toastDuration.equals(ToastDuration.LONG))
                toast.setDuration(Toast.LENGTH_LONG);
            else
                toast.setDuration(Toast.LENGTH_SHORT);

            textView.setText(message);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
