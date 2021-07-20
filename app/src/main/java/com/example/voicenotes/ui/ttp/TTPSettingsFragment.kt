package com.example.voicenotes.ui.ttp

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.voicenotes.R
import com.example.voicenotes.databinding.FragmentTtpSettingsBinding
import com.example.voicenotes.utils.Utils.displayConfirmation
import com.example.voicenotes.utils.Utils.getFloat
import com.example.voicenotes.utils.Utils.saveFloat
import com.example.voicenotes.utils.Utils.toast
import java.util.*


class TTPSettingsFragment : Fragment() {

    private var _binding: FragmentTtpSettingsBinding? = null
    private val binding: FragmentTtpSettingsBinding get() = _binding!!
    private lateinit var tts: TextToSpeech


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        _binding = FragmentTtpSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialiseTTS()
        binding.apply {
            ttsSettingsSpeedSubBtn.setOnClickListener { substract(ttsSettingsSpeechSpeedTv) }
            ttsSettingsSpeedAddBtn.setOnClickListener { add(ttsSettingsSpeechSpeedTv) }
            ttsSettingsPitchSubBtn.setOnClickListener { substract(ttsSettingsSpeechPitchTv) }
            ttsSettingsPitchAddBtn.setOnClickListener { add(ttsSettingsSpeechPitchTv) }
            ttsSettingsOkTv.setOnClickListener { saveSettings() }
            ttpSettingsBackTv.setOnClickListener { findNavController().popBackStack() }

            /** SET SPEECH SETTINGS ON TEXT VIEWS */
            ttsSettingsSpeechSpeedTv.text = "${getFloat(getString(R.string.speech_speed_setting)).toString().substring(0, 3)}x"
            ttsSettingsSpeechPitchTv.text = "${getFloat(getString(R.string.speech_pitch_setting)).toString().substring(0, 3)}x"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        if(tts != null) {
            tts.stop()
            tts.shutdown()
        }
    }

    private fun add(textView: TextView) {
        val value = textView.text.substring(0, 3).toFloat()
        if(value < 2.0) {
            textView.text = "${value + 0.5}x"
            speak()
        }
    }

    private fun substract(textView: TextView) {
        val value = textView.text.substring(0, 3).toFloat()
        if(value > 0.5) {
            textView.text = "${value - 0.5}x"
            speak()
        }
    }

    private fun saveSettings() {
        binding.apply {
            val speed = ttsSettingsSpeechSpeedTv.text.substring(0, 3).toFloat()
            val pitch = ttsSettingsSpeechPitchTv.text.substring(0, 3).toFloat()
            saveFloat(getString(R.string.speech_speed_setting), speed)
            saveFloat(getString(R.string.speech_pitch_setting), pitch)
            displayConfirmation(getString(R.string.saved), requireContext())
            findNavController().popBackStack()
        }
    }

    private fun initialiseTTS() {
        tts = TextToSpeech(requireContext()) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val language = tts.setLanguage(Locale.ENGLISH)
                if (language == TextToSpeech.LANG_MISSING_DATA || language == TextToSpeech.LANG_NOT_SUPPORTED) {
                    toast("Language not supported")
                }
            } else {
                toast("Text To Speech Initialisation Failed.")
            }
        }
    }

    private fun speak() {
        binding.apply {
            val text = getString(R.string.sample_speech)
            val speed = ttsSettingsSpeechSpeedTv.text.substring(0, 3).toFloat()
            val pitch = ttsSettingsSpeechPitchTv.text.substring(0, 3).toFloat()

            tts.apply {
                setPitch(pitch)
                setSpeechRate(speed)
                speak(text, TextToSpeech.QUEUE_FLUSH, null)
            }
        }
    }
}