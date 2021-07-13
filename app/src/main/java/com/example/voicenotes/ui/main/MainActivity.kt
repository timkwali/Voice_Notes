package com.example.voicenotes.ui.main

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import androidx.fragment.app.FragmentActivity
import com.example.voicenotes.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Enables Always-on
//        setAmbientEnabled()
    }
}