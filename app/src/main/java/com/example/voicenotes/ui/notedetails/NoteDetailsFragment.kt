package com.example.voicenotes.ui.notedetails

import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.wear.widget.CircularProgressLayout
import com.example.voicenotes.R
import com.example.voicenotes.data.Note
import com.example.voicenotes.data.NotesViewmodel
import com.example.voicenotes.databinding.FragmentNoteDetailsBinding
import com.example.voicenotes.utils.Constants.SPEECH_PITCH
import com.example.voicenotes.utils.Constants.SPEECH_SPEED
import com.example.voicenotes.utils.Utils.displayCancel
import com.example.voicenotes.utils.Utils.displayConfirmation
import com.example.voicenotes.utils.Utils.gone
import com.example.voicenotes.utils.Utils.toast
import com.example.voicenotes.utils.Utils.visible
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class NoteDetailsFragment : Fragment(), CircularProgressLayout.OnTimerFinishedListener, View.OnClickListener {

    private var _binding: FragmentNoteDetailsBinding? = null
    private val binding: FragmentNoteDetailsBinding get() = _binding!!

    private lateinit var note: Note
    private lateinit var circularProgress: CircularProgressLayout
    private lateinit var tts: TextToSpeech
    private val viewmodel: NotesViewmodel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNoteDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialiseTTS()
        note = NoteDetailsFragmentArgs.fromBundle(requireArguments()).note
        val date = note.date
        val time = note.time
        binding.apply {
            noteDetailsDateTv.text = "$date\n$time"
            noteDetailsNoteTv.text = note.note

            circularProgress = noteDetailsCircularProgressCpl.apply {
                onTimerFinishedListener = this@NoteDetailsFragment
                setOnClickListener(this@NoteDetailsFragment)
            }
            noteDetailsBackTv.setOnClickListener {
                findNavController().popBackStack()
            }
            noteDetailsSpeakerIb.setOnClickListener {
                if(noteDetailsNoteTv.text != getString(R.string.press_the_mic_to_add_new_note)) {
                    speak()
                }
            }
            noteDetailsDeleteIb.setOnClickListener {
                startDeleteAnimation()
            }
            noteDetailsImageIb.setOnClickListener {
                onClick(requireView())
            }
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

    override fun onTimerFinished(layout: CircularProgressLayout?) {
        binding.noteDetailsCancelProgressBarCl.gone()
        //Delete Note From database
        viewmodel.deleteNote(note.id).isCompleted
        displayConfirmation(getString(R.string.note_deleted), requireContext())
        findNavController().popBackStack()
    }

    override fun onClick(v: View?) {
        circularProgress.stopTimer()
        binding.noteDetailsCancelProgressBarCl.gone()
        displayCancel(getString(R.string.note_not_deleted), requireContext())
    }

    private fun startDeleteAnimation() {
        binding.apply {
            noteDetailsCancelProgressBarCl.visible()
            circularProgress.apply {
                totalTime = 2000
                startTimer()
            }
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
        val text = binding.noteDetailsNoteTv.text.toString()
        val pitch: Float = SPEECH_PITCH
        val speed: Float = SPEECH_SPEED
        tts.apply {
            setPitch(pitch)
            setSpeechRate(speed)
            speak(text, TextToSpeech.QUEUE_FLUSH, null)
        }
    }
}