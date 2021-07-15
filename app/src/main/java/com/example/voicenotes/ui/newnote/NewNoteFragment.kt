package com.example.voicenotes.ui.newnote

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.voicenotes.data.Note
import com.example.voicenotes.data.NotesViewmodel
import com.example.voicenotes.databinding.FragmentNewNoteBinding
import com.example.voicenotes.utils.Utils.displayConfirmation
import com.example.voicenotes.utils.Utils.gone
import com.example.voicenotes.utils.Utils.toast
import com.example.voicenotes.utils.Utils.visible
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class NewNoteFragment : Fragment() {

    private var _binding: FragmentNewNoteBinding? = null
    private val binding get() = _binding!!

    private val viewmodel: NotesViewmodel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNewNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** RECORD NEW NOTE */
        displaySpeechScreen()
        binding.apply {
            newNoteMicIb.setOnClickListener{
                displaySpeechScreen()
            }
            newNoteBackTv.setOnClickListener {
                findNavController().popBackStack()
            }
            newNoteSaveIb.setOnClickListener{
                if(newNoteNoteTv.text.isEmpty()) {
                    toast("Please Record A New Note To Continue.")
                } else {
                    saveNote()
                }
            }
            newNoteDeleteIb.setOnClickListener {
                newNoteNoteTv.text = ""
                newNoteNoNoteTv.visible()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1001 && resultCode == RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            binding.apply {
                newNoteNoNoteTv.gone()
                newNoteNoteTv.text = "${newNoteNoteTv.text} ${result?.get(0)?.capitalize()}."
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun saveNote() {
        val note = binding.newNoteNoteTv.text.toString()
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(Date())
        val meridian = if(sdf.substring(10, 12).toInt() < 12) "AM" else "PM"
        val month = when("${sdf.substring(3, 4).toInt()}") {
            "1" -> "January"
            "2" -> "February"
            "3" -> "March"
            "4" -> "April"
            "5" -> "May"
            "6" -> "June"
            "7" -> "July"
            "8" -> "August"
            "9" -> "September"
            "10" -> "October"
            "11" -> "November"
            "12" -> "December"
            else -> ""
        }

        val date = "${sdf.substring(0, 2).toInt()} $month ${sdf.substring(5, 9)}"
        val time = "${sdf.substring(10, 12).toInt()}:${sdf.substring(13, 15)} $meridian"

//        if(viewmodel.saveNote(Note(note, date, time)).isCompleted) {
            viewmodel.saveNote(Note(note, date, time))
            displayConfirmation("Note Added", requireContext())
            viewmodel.getAllNotes()
            Log.d("allNotes", viewmodel.allNotes.value.toString())
            findNavController().popBackStack()
//        } else {
//            toast("Something Went wrong. Please try again.")
//        }
    }

    private fun displaySpeechScreen() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to add note!")
        startActivityForResult(intent, 1001)
    }
}