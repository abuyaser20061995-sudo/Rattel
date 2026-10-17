package com.rattel.app.domain.usecase

import javax.inject.Inject

enum class WordStatus { PENDING, CORRECT, ERROR, SKIPPED }

data class TrackedWord(
    val text: String,
    var status: WordStatus = WordStatus.PENDING
)

class VoiceMatchingUseCase @Inject constructor() {

    /**
     * توحيد النص (Normalization) 
     * إزالة التشكيل وتوحيد أشكال الألف والياء والهاء لتسهيل المطابقة مع محرك الصوت.
     */
    private fun normalizeText(text: String): String {
        return text.replace(Regex("[\\u0617-\\u061A\\u064B-\\u0652]"), "") // إزالة التشكيل
            .replace("أ", "ا").replace("إ", "ا").replace("آ", "ا") // توحيد الألف
            .replace("ة", "ه") // توحيد التاء المربوطة والهاء
            .replace("ى", "ي") // توحيد الياء والألف المقصورة
    }

    /**
     * خوارزمية المطابقة (مبنية على فكرة Levenshtein مبسطة).
     * تقارن الكلمة المنطوقة مع الكلمات المستهدفة.
     */
    fun processSpokenText(
        spokenText: String, 
        targetWords: List<TrackedWord>, 
        currentIndex: Int
    ): Int {
        val normalizedSpoken = normalizeText(spokenText).split(" ")
        var newIndex = currentIndex

        for (spokenWord in normalizedSpoken) {
            if (newIndex >= targetWords.size) break

            val targetWord = normalizeText(targetWords[newIndex].text)

            if (spokenWord == targetWord) {
                // نطق صحيح
                targetWords[newIndex].status = WordStatus.CORRECT
                newIndex++
            } else {
                // التحقق مما إذا كان القارئ قد تجاوز الكلمة الحالية وقرأ التي تليها
                val nextTargetWord = targetWords.getOrNull(newIndex + 1)?.let { normalizeText(it.text) }
                if (spokenWord == nextTargetWord) {
                    targetWords[newIndex].status = WordStatus.SKIPPED // الكلمة السابقة تم تجاوزها
                    targetWords[newIndex + 1].status = WordStatus.CORRECT
                    newIndex += 2
                } else {
                    // نطق خاطئ للكلمة الحالية
                    targetWords[newIndex].status = WordStatus.ERROR
                }
            }
        }
        return newIndex // إرجاع المؤشر الجديد للكلمة الحالية
    }
}
