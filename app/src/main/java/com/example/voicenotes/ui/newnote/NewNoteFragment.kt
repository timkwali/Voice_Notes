package com.example.voicenotes.ui.newnote

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.voicenotes.R
import com.example.voicenotes.databinding.FragmentNewNoteBinding
import com.example.voicenotes.utils.Utils.displayConfirmation
import com.example.voicenotes.utils.Utils.toast
import java.util.*


class NewNoteFragment : Fragment() {

    private var _binding: FragmentNewNoteBinding? = null
    private val binding get() = _binding!!

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
                    /** SAVE NEW NOTE IN DATABASE*/
                    displayConfirmation("Note Added", requireContext())
                }
            }
            newNoteDeleteIb.setOnClickListener {
                newNoteNoteTv.text = ""
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1001 && resultCode == RESULT_OK) {
            val result = data?.getStringArrayExtra(RecognizerIntent.EXTRA_RESULTS)
            binding.newNoteNoteTv.text = result?.get(0)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun displaySpeechScreen() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to add note!")
        startActivityForResult(intent, 1001)
    }
}