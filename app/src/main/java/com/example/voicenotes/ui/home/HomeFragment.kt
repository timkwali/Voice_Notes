package com.example.voicenotes.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.voicenotes.R
import com.example.voicenotes.data.Note
import com.example.voicenotes.data.NotesViewmodel
import com.example.voicenotes.databinding.FragmentHomeBinding
import com.example.voicenotes.utils.OnItemClickListener
import com.example.voicenotes.utils.Utils.gone
import com.example.voicenotes.utils.Utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), OnItemClickListener<Note> {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var noteAdapter: NotesRVAdapter
    private lateinit var notesList: List<Note>
    private val viewmodel: NotesViewmodel by viewModels()

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

        getAllNotes()
        binding.apply {
            homeSettingsIb.setOnClickListener {
                findNavController().navigate(R.id.settingsFragment)
            }
            homeAddNoteIb.setOnClickListener{
                findNavController().navigate(R.id.newNoteFragment)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemClick(item: Note, position: Int) {
        val action = HomeFragmentDirections.actionHomeFragmentToNoteDetailsFragment3(item)
        findNavController().navigate(action)
    }

    private fun getAllNotes() {
        viewmodel.getAllNotes()
        viewmodel.allNotes.observe(viewLifecycleOwner, Observer {
            notesList = it
            noteAdapter = NotesRVAdapter(notesList, this)
            if(notesList.isEmpty()) binding.homeMessageTv.visible() else binding.homeMessageTv.gone()
            binding.apply {
                homeNotesListRv.apply {
                    adapter = noteAdapter
                    layoutManager = LinearLayoutManager(requireContext())
                    isCircularScrollingGestureEnabled = true
//                isEdgeItemsCenteringEnabled = true
                }
            }
        })
    }
}