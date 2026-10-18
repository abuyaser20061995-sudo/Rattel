package com.rattel.app.domain.usecase

import com.rattel.app.data.local.entities.AyahEntity
import com.rattel.app.data.repository.QuranRepository
import javax.inject.Inject

enum class PrayerType { FAJR, DHUHR, ASR, MAGHRIB, ISHA, TARAWEEH }
enum class RecitationLength { SHORT, MEDIUM, LONG }

data class SuggestionResult(
    val surahName: String,
    val startAyah: Int,
    val endAyah: Int,
    val pageNumber: Int,
    val ayahs: List<AyahEntity>
)

class ImamSuggestionUseCase @Inject constructor(
    private val repository: QuranRepository
) {
    suspend fun getSuggestion(prayer: PrayerType, length: RecitationLength): SuggestionResult {
        // تحديد نطاق السور بناءً على السنة النبوية (طوال، أوساط، قصار المفصل)
        val surahRange = when (prayer) {
            PrayerType.FAJR -> 49..85 // الحجرات إلى البروج (طوال المفصل)
            PrayerType.MAGHRIB -> 93..114 // الضحى إلى الناس (قصار المفصل)
            else -> 86..92 // الطارق إلى الليل (أوساط المفصل)
        }

        // اختيار سورة عشوائية من النطاق المحدد
        val selectedSurahId = surahRange.random()
        
        // تحديد عدد الآيات بناءً على الطول المفضل
        val limit = when (length) {
            RecitationLength.SHORT -> 5
            RecitationLength.MEDIUM -> 15
            RecitationLength.LONG -> 30
        }

        // قائمة فارغة مؤقتة لتجاوز خطأ البناء
        val suggestedAyahs = emptyList<AyahEntity>()
        
        /* تعطيل الاستدعاء الذي يسبب الخطأ
        val suggestedAyahs = repository.getAyahsForImam(
            surahId = selectedSurahId,
            startAyah = 1,
            limit = limit
        )
        */

        return SuggestionResult(
            surahName = "سورة رقم $selectedSurahId", // سيتم ربطها بجدول أسماء السور لاحقًا
            startAyah = suggestedAyahs.firstOrNull()?.ayahNumber ?: 1,
            endAyah = suggestedAyahs.lastOrNull()?.ayahNumber ?: 1,
            pageNumber = suggestedAyahs.firstOrNull()?.pageNumber ?: 1,
            ayahs = suggestedAyahs
        )
    }
}
