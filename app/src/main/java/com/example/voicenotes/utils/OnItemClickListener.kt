package com.example.voicenotes.utils

interface OnItemClickListener<T> {
    fun onItemClick(item: T, position: Int)
}