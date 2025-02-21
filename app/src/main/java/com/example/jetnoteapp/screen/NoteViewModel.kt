package com.example.jetnoteapp.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetnoteapp.data.NotesDataSource
import com.example.jetnoteapp.model.Note
import com.example.jetnoteapp.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val noteRepository: NoteRepository) : ViewModel() {
//    private var noteList = mutableStateListOf<Note>()

    private val _noteList = MutableStateFlow<List<Note>>(emptyList())
    val noteList = _noteList.asStateFlow()

    fun addNote(note: Note) = viewModelScope.launch { noteRepository.addNote(note) }
    fun updateNote(note: Note) = viewModelScope.launch { noteRepository.updateNote(note) }
    fun removeNote(note: Note) = viewModelScope.launch { noteRepository.deleteNote(note) }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.getAllNotes().distinctUntilChanged().collect { listOfNotes ->
//                if (listOfNotes.isNullOrEmpty()) {
//                    Log.d("Empty", "Empty List")
//                } else {
                    _noteList.value = listOfNotes
//                }
            }
        }
//        noteList.addAll(NotesDataSource().loadNotes())
    }
//
//    fun addNote(note: Note) {
//        noteList.add(note)
//    }
//
//    fun removeNote(note: Note) {
//        noteList.remove(note)
//    }
//
//    fun getAllNotes() : List<Note> {
//        return noteList
//    }
}