package com.rattel.app.data.repository

import com.rattel.app.data.local.Ayah
import com.rattel.app.data.local.QuranDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuranRepository @Inject constructor(
    private val quranDao: QuranDao
) {
    fun getSurahAyahs(surahId: Int): Flow<List<Ayah>> {
        return quranDao.getSurahAyahs(surahId)
    }

    fun getPageAyahs(page: Int): Flow<List<Ayah>> {
        return quranDao.getPageAyahs(page)
    }
}
