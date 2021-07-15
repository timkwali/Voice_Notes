package com.example.voicenotes.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "notes")
data class Note(
        val note: String,
        val date: String,
        val time: String,
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0
): Parcelable
