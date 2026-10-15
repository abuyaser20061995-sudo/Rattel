package com.rattel.app.data.repository

import com.rattel.app.data.local.dao.QuranDao
import com.rattel.app.data.local.entities.AyahEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * المستودع (Repository) الخاص بالقرآن الكريم.
 * @Inject تعني أن Hilt سيقوم بتوفير QuranDao تلقائياً لهذا الكلاس.
 * @Singleton تعني أن هناك نسخة واحدة فقط من هذا المستودع في التطبيق كله لتقليل استهلاك الذاكرة.
 */
@Singleton
class QuranRepository @Inject constructor(
    private val quranDao: QuranDao
) {

    /**
     * جلب جميع الآيات لصفحة معينة.
     * نستخدم Flow لكي يتم تحديث الشاشة تلقائياً إذا حدث أي تغيير.
     */
    fun getAyahsByPage(pageNumber: Int): Flow<List<AyahEntity>> {
        return quranDao.getAyahsByPage(pageNumber)
    }

    /**
     * جلب آية محددة بالـ ID الخاص بها (يستخدم لعرض التفسير أو الإعراب).
     */
    suspend fun getAyahById(ayahId: Int): AyahEntity? {
        return quranDao.getAyahById(ayahId)
    }

    /**
     * البحث في المصحف.
     */
    fun searchQuran(query: String): Flow<List<AyahEntity>> {
        return quranDao.searchQuran(query)
    }
}
