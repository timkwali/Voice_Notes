package com.example.voicenotes.ui.notedetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.wear.widget.CircularProgressLayout
import com.example.voicenotes.R
import com.example.voicenotes.data.Note
import com.example.voicenotes.databinding.FragmentNoteDetailsBinding
import com.example.voicenotes.utils.Utils.displayCancel
import com.example.voicenotes.utils.Utils.displayConfirmation
import com.example.voicenotes.utils.Utils.gone
import com.example.voicenotes.utils.Utils.toast
import com.example.voicenotes.utils.Utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteDetailsFragment : Fragment(), CircularProgressLayout.OnTimerFinishedListener, View.OnClickListener {

    private var _binding: FragmentNoteDetailsBinding? = null
    private val binding: FragmentNoteDetailsBinding get() = _binding!!

    private lateinit var note: Note
    private lateinit var circularProgress: CircularProgressLayout

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

            }
            noteDetailsDeleteIb.setOnClickListener {
                startDeleteAnimation()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onTimerFinished(layout: CircularProgressLayout?) {
        binding.noteDetailsCancelProgressBarCl.gone()
        //Delete Note From database
        displayConfirmation(getString(R.string.note_deleted), requireContext())
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
}