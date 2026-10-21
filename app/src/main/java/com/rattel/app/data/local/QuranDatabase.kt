package com.rattel.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Ayah::class], version = 1, exportSchema = false)
abstract class QuranDatabase : RoomDatabase() {
    abstract fun quranDao(): QuranDao
}
