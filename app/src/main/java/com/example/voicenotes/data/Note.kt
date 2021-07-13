package com.example.voicenotes.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Note(
        val note: String,
        val date: String,
        val time: String
): Parcelable
