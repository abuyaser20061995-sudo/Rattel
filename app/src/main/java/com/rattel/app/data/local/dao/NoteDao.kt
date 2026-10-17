package com.rattel.app.data.local.dao

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "user_notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val ayahId: Int,             // معرف الآية المرتبطة
    val surahName: String,       // اسم السورة للعرض السريع
    val ayahNumber: Int,         // رقم الآية
    val noteText: String,        // نص الملاحظة (إذا كان فارغاً يعني أنها مفضلة فقط)
    val isFavorite: Boolean,     // هل الآية مضافة للمفضلة؟
    val timestamp: Long          // وقت الإضافة
)

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity)

    @Query("DELETE FROM user_notes WHERE id = :noteId")
    suspend fun deleteNote(noteId: Int)

    // جلب جميع الملاحظات والمفضلات مرتبة من الأحدث للأقدم
    @Query("SELECT * FROM user_notes ORDER BY timestamp DESC")
    fun getAllNotesAndFavorites(): Flow<List<NoteEntity>>

    // التحقق مما إذا كانت الآية في المفضلة (لتحديث أيقونة القلب في المصحف)
    @Query("SELECT * FROM user_notes WHERE ayahId = :ayahId LIMIT 1")
    suspend fun getNoteByAyahId(ayahId: Int): NoteEntity?
}
