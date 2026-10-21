package com.rattel.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quran_table")
data class Ayah(
    @PrimaryKey val id: Int,
    val surahNumber: Int,
    val ayahNumber: Int,
    val textUthmani: String,
    val pageNumber: Int,
    val juzNumber: Int
)
