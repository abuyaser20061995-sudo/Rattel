package com.rattel.app.data.repository

import com.rattel.app.data.local.dao.NoteDao
import com.rattel.app.data.local.dao.NoteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepository @Inject constructor(
    private val noteDao: NoteDao
) {
    fun getAllNotes(): Flow<List<NoteEntity>> {
        return noteDao.getAllNotesAndFavorites()
    }

    suspend fun saveNoteOrFavorite(ayahId: Int, surahName: String, ayahNum: Int, text: String, isFav: Boolean) {
        val note = NoteEntity(
            ayahId = ayahId,
            surahName = surahName,
            ayahNumber = ayahNum,
            noteText = text,
            isFavorite = isFav,
            timestamp = System.currentTimeMillis()
        )
        noteDao.insertNote(note)
    }

    suspend fun deleteNote(noteId: Int) {
        noteDao.deleteNote(noteId)
    }
}
