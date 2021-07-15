package com.example.voicenotes.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewmodel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    private val _allNotes: MutableLiveData<List<Note>> = MutableLiveData()
    val allNotes: LiveData<List<Note>> get() = _allNotes

    fun getAllNotes() = viewModelScope.launch {
        _allNotes.value = repository.getAllNotes()
    }

    fun saveNote(note: Note) = viewModelScope.launch {
        repository.saveNote(note)
    }

    fun deleteNote(noteId: Int) = viewModelScope.launch {
        repository.deleteNote(noteId)
    }

    fun deleteAllNotes() = viewModelScope.launch {
        repository.deleteAllNotes()
    }
}