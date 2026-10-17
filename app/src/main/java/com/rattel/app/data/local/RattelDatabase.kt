package com.rattel.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rattel.app.data.local.dao.QuranDao
import com.rattel.app.data.local.dao.NoteDao
import com.rattel.app.data.local.dao.NoteEntity
import com.rattel.app.data.local.entities.AyahEntity
import com.rattel.app.data.local.entities.ReviewItemEntity
import com.rattel.app.data.local.entities.TafseerEntity

@Database(
    entities = [
        AyahEntity::class, 
        TafseerEntity::class, 
        ReviewItemEntity::class,
        NoteEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class RattelDatabase : RoomDatabase() {
    abstract fun quranDao(): QuranDao
    abstract fun noteDao(): NoteDao
}
