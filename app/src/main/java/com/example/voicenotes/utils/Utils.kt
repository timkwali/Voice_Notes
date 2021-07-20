package com.example.voicenotes.utils

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.wearable.activity.ConfirmationActivity
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.voicenotes.R
import com.muddzdev.styleabletoastlibrary.StyleableToast

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
//       StyleableToast.makeText(context, message, R.style.toastStyle).show()
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

    fun Fragment.saveFloat(key: String, value: Float) {
        val sharedPref: SharedPreferences = requireContext().getSharedPreferences(getString(R.string.app_settings), Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putFloat(key, value)
        editor.commit()
    }

    fun Fragment.getFloat(key: String): Float {
        val sharedPref: SharedPreferences = requireContext().getSharedPreferences(getString(R.string.app_settings), Context.MODE_PRIVATE)
        return sharedPref.getFloat(key, 1.0F)
    }
}