package com.example.voicenotes.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.wear.widget.WearableLinearLayoutManager
import com.example.voicenotes.R
import com.example.voicenotes.data.NotesViewmodel
import com.example.voicenotes.data.Setting
import com.example.voicenotes.databinding.FragmentSettingsBinding
import com.example.voicenotes.utils.ConfirmationDialog
import com.example.voicenotes.utils.OnItemClickListener
import com.example.voicenotes.utils.Utils.displayConfirmation
import com.example.voicenotes.utils.Utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment(), OnItemClickListener<Setting> {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var settingsAdapter: SettingsRVAdapter
    private val viewmodel: NotesViewmodel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingsAdapter = SettingsRVAdapter(settingsList, this)
        binding.apply {
            settingsBackTv.setOnClickListener {
                findNavController().popBackStack()
            }
            settingsSettingsListRv.apply {
                adapter = settingsAdapter
                layoutManager = LinearLayoutManager(requireContext())
                isCircularScrollingGestureEnabled = true
//                isEdgeItemsCenteringEnabled = true
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemClick(item: Setting, position: Int) {
        when(item.name) {
            getString(R.string.sync_with_google) -> toast(item.name)
            getString(R.string.text_to_speech_settings) -> toast(item.name)
            getString(R.string.delete_all_notes) -> confirmDelete()
            getString(R.string.about) -> toast(item.name)
        }
    }

    private val settingsList = listOf(
        Setting(R.drawable.google_g, "Sync With Google"),
        Setting(R.drawable.ic_speaker_jasmine, "Text To Speech Settings"),
        Setting(R.drawable.ic_delete_jasmine, "Delete All Notes"),
        Setting(R.drawable.info, "About"),
    )

    private fun confirmDelete() {
        ConfirmationDialog.show(
                childFragmentManager,
                getString(R.string.delete_warning),
                object : ConfirmationDialog.Listener() {
                    override fun onConfirm() {
                        super.onConfirm()
                        viewmodel.deleteAllNotes()
                        displayConfirmation(getString(R.string.all_notes_delete_success), requireContext())
                    }
                })
    }

}