package com.rattel.app.data.local

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface QuranDao {
    // جلب آيات سورة معينة
    @Query("SELECT * FROM quran_table WHERE surahNumber = :surahId ORDER BY ayahNumber ASC")
    fun getSurahAyahs(surahId: Int): Flow<List<Ayah>>

    // جلب آيات صفحة معينة
    @Query("SELECT * FROM quran_table WHERE pageNumber = :page ORDER BY id ASC")
    fun getPageAyahs(page: Int): Flow<List<Ayah>>
}
