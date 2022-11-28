package com.demo.lint

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import com.lxj.xpopup.core.BasePopupView

/**
 *
 * @author like
 * @date 2022/11/23 16:28
 */
class CustomDialog(context: Context) : Dialog(context) {

    init {
        context.startActivity(Intent())
        (context as Activity).startActivity(Intent())
    }

    private fun alertDialog(dialog:AlertDialog){
        AlertDialog.Builder(context)
    }
}