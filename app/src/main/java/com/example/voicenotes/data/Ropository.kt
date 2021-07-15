package com.example.voicenotes.data

import javax.inject.Inject


class Repository @Inject constructor(
    private val notesDb: NotesDatabase
) {

    private val notesDao = notesDb.notesDao()

    suspend fun getAllNotes(): List<Note> {
        return notesDao.getAllNotes()
    }

    suspend fun saveNote(note: Note) {
        notesDao.saveNote(note)
    }

    suspend fun deleteNote(noteId: Int) {
        notesDao.deleteNote(noteId)
    }

    suspend fun deleteAllNotes() {
        notesDao.deleteAllNotes()
    }
}