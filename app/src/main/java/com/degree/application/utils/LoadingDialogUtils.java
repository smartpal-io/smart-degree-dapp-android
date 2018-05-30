package com.degree.application.utils;

import android.app.Activity;
import android.app.ProgressDialog;

import com.degree.application.R;

public class LoadingDialogUtils {

    private static ProgressDialog progressDialog;

    public static void displayLoadingDialog(Activity activity) {
        progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle(activity.getString(R.string.loading_title));
        progressDialog.setMessage(activity.getString(R.string.loading_message));
        progressDialog.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progressDialog.show();
    }

    public static void hideLoadingDialog(){
        if(progressDialog!=null) {
            progressDialog.hide();
            progressDialog = null;
        }
    }
}
