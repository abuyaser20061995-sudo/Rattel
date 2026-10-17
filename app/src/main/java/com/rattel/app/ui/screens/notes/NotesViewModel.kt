package com.rattel.app.ui.screens.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rattel.app.data.local.dao.NoteEntity
import com.rattel.app.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteRepository: NoteRepository
) : ViewModel() {

    private val _notesList = MutableStateFlow<List<NoteEntity>>(emptyList())
    val notesList: StateFlow<List<NoteEntity>> = _notesList.asStateFlow()

    init {
        viewModelScope.launch {
            noteRepository.getAllNotes().collect { notes ->
                _notesList.value = notes
            }
        }
    }

    fun deleteNote(noteId: Int) {
        viewModelScope.launch {
            noteRepository.deleteNote(noteId)
        }
    }
}
