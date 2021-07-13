package com.example.voicenotes.ui.home

import android.icu.util.BuddhistCalendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.wear.widget.WearableLinearLayoutManager
import com.example.voicenotes.R
import com.example.voicenotes.data.Note
import com.example.voicenotes.databinding.FragmentHomeBinding
import com.example.voicenotes.utils.Utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), OnItemClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var noteAdapter: NotesRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteAdapter = NotesRVAdapter(notesList, this)

        binding.apply {
            homeNotesListRv.apply {
                adapter = noteAdapter
                layoutManager = LinearLayoutManager(requireContext())
//                isCircularScrollingGestureEnabled = false
//                isEdgeItemsCenteringEnabled = true
            }
        }
    }

    override fun onItemClick(note: Note, position: Int) {
        toast(note.note)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private val notesList = listOf (
            Note("Tell Joe to heat up the food in the microwave oven by 7 PM", "13 Jul '21", "11:03 AM"),
            Note("Tell Joe to heat up the food in the microwave oven by 7 PM", "13 Jul '21", "11:03 AM"),
            Note("Tell Joe to heat up the food in the microwave oven by 7 PM", "13 Jul '21", "11:03 AM"),
            Note("Tell Joe to heat up the food in the microwave oven by 7 PM", "13 Jul '21", "11:03 AM"),
            Note("Tell Joe to heat up the food in the microwave oven by 7 PM", "13 Jul '21", "11:03 AM"),
            Note("Tell Joe to heat up the food in the microwave oven by 7 PM", "13 Jul '21", "11:03 AM"),
            Note("Tell Joe to heat up the food in the microwave oven by 7 PM", "13 Jul '21", "11:03 AM"),
            Note("Tell Joe to heat up the food in the microwave oven by 7 PM", "13 Jul '21", "11:03 AM"),
            Note("Tell Joe to heat up the food in the microwave oven by 7 PM", "13 Jul '21", "11:03 AM"),
            Note("Tell Joe to heat up the food in the microwave oven by 7 PM", "13 Jul '21", "11:03 AM"),
            Note("Tell Joe to heat up the food in the microwave oven by 7 PM", "13 Jul '21", "11:03 AM"),
    )
}