package com.example.voicenotes.utils

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.voicenotes.R

class ConfirmationDialog (private val message: String) : DialogFragment() {
    var listener: Listener? = null
    var promptMessage: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.confirmation_dialog, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tv_longtext = view.findViewById<TextView>(R.id.tv_longtext)
        val btn_cancel: android.support.wearable.view.CircledImageView = view.findViewById(R.id.btn_cancel)
        val btn_ok: android.support.wearable.view.CircledImageView = view.findViewById(R.id.btn_ok)

        tv_longtext.text = promptMessage
        tv_longtext.visibility = if (promptMessage.isEmpty()) View.GONE else View.VISIBLE

        btn_cancel.setOnClickListener {
            dismiss()
            listener?.onCancel()
        }
        btn_ok.setOnClickListener {
            dismiss()
            listener?.onConfirm()
        }
    }

    override fun onStart() {
        super.onStart()
        val params = dialog?.window?.attributes
        params?.width = WindowManager.LayoutParams.MATCH_PARENT
        params?.height = WindowManager.LayoutParams.MATCH_PARENT
        dialog?.window?.attributes = params as WindowManager.LayoutParams
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
    }

    open class Listener {
        fun onCancel() {
        }

        open fun onConfirm() {
        }
    }

    companion object {
        fun show(
            fm: FragmentManager,
            promptMessage: String = "",
            listener: Listener
        ) {
            val fragment = ConfirmationDialog(promptMessage)
            fragment.promptMessage = promptMessage
            fragment.listener = listener
            fragment.show(fm, fm::class.simpleName)
        }
    }
}