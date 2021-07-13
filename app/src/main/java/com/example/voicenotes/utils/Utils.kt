package com.example.voicenotes.utils

import android.content.Context
import android.content.Intent
import android.support.wearable.activity.ConfirmationActivity
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

object Utils {
    fun View.visible() {
        this.visibility = View.VISIBLE
    }

    fun View.invisible() {
        this.visibility = View.INVISIBLE
    }

    fun View.gone() {
        this.visibility = View.GONE
    }

    fun Fragment.toast(
            message: String,
            duration: Int = Toast.LENGTH_LONG,
            context: Context = requireContext()
    ) {
       Toast.makeText(context, message, duration).show()
    }

    fun displayConfirmation(message: String, context: Context) {
        val intent = Intent(context, ConfirmationActivity::class.java).apply {
            putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE, ConfirmationActivity.SUCCESS_ANIMATION)
            putExtra(ConfirmationActivity.EXTRA_MESSAGE, message)
        }
        context.startActivity(intent)
    }

    fun displayCancel(message: String, context: Context) {
        val intent = Intent(context, ConfirmationActivity::class.java).apply {
            putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE, ConfirmationActivity.FAILURE_ANIMATION)
            putExtra(ConfirmationActivity.EXTRA_MESSAGE, message)
        }
        context.startActivity(intent)
    }
}