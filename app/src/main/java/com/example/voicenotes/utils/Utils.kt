package com.example.voicenotes.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

object Utils {
    fun Fragment.toast(
            message: String,
            duration: Int = Toast.LENGTH_LONG,
            context: Context = requireContext()
    ) {
       Toast.makeText(context, message, duration).show()
    }
}