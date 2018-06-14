package io.smartpal.smartdegree.helpers

import android.app.AlertDialog
import android.content.Context
import io.smartpal.smartdegree.R
import dmax.dialog.SpotsDialog

object LoadingDialog {

    private var progressDialog: AlertDialog? = null

    fun displayLoadingDialog(context: Context) {
        progressDialog = SpotsDialog
                .Builder()
                .setContext(context)
                .setMessage(context.getString(R.string.loading_message))
                .setCancelable(false)
                .build()
        progressDialog?.show()
    }

    fun hideLoadingDialog() {
        progressDialog?.hide()
    }
}
