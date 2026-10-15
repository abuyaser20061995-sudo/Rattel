package com.rattel.app.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.rattel.app.data.local.entities.AyahEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuranDao {

    /**
     * جلب جميع الآيات الموجودة في صفحة معينة.
     * مفيد جداً عندما ينتقل المستخدم لصفحة جديدة في شاشة المصحف.
     */
    @Query("SELECT * FROM quran_ayahs WHERE pageNumber = :page ORDER BY id ASC")
    fun getAyahsByPage(page: Int): Flow<List<AyahEntity>>

    /**
     * جلب آية معينة بواسطة رقمها التراكمي (يستخدم عند الضغط المطول على آية).
     */
    @Query("SELECT * FROM quran_ayahs WHERE id = :ayahId")
    suspend fun getAyahById(ayahId: Int): AyahEntity?

    /**
     * محرك البحث: يبحث في النص الخالي من التشكيل.
     */
    @Query("SELECT * FROM quran_ayahs WHERE textClean LIKE '%' || :searchQuery || '%'")
    fun searchQuran(searchQuery: String): Flow<List<AyahEntity>>

    /**
     * استعلام خاص لخوارزمية الإمام: 
     * جلب آيات متتالية تناسب صلاة معينة (بناءً على الطول المطلوب).
     */
    @Query("SELECT * FROM quran_ayahs WHERE surahNumber = :surahId AND ayahNumber >= :startAyah LIMIT :limit")
    suspend fun getAyahsForImam(surahId: Int, startAyah: Int, limit: Int): List<AyahEntity>
}
