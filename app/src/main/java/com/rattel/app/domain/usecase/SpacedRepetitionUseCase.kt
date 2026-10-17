package com.rattel.app.domain.usecase

import javax.inject.Inject

enum class ReviewQuality(val value: Int) {
    AGAIN(0),  // نسي المقطع تماماً (إعادة فورية)
    HARD(1),   // تذكر بصعوبة (أخطاء كثيرة)
    GOOD(2),   // تذكر جيد (تردد بسيط)
    EASY(3)    // حفظ متقن
}

data class ReviewCalculationResult(
    val newIntervalDays: Int,
    val newEaseFactor: Float,
    val nextReviewTimestamp: Long
)

class SpacedRepetitionUseCase @Inject constructor() {

    fun calculateNextReview(
        currentIntervalDays: Int,
        currentEaseFactor: Float,
        quality: ReviewQuality
    ): ReviewCalculationResult {
        var newInterval: Int
        var newEaseFactor = currentEaseFactor

        if (quality == ReviewQuality.AGAIN) {
            // إذا نسي المقطع، يعود الفاصل الزمني إلى يوم واحد
            newInterval = 1
            // يقل معامل السهولة قليلاً
            newEaseFactor -= 0.2f
        } else {
            // حساب الفاصل الزمني الجديد بناءً على الفاصل القديم
            newInterval = when (currentIntervalDays) {
                0 -> 1 // المراجعة الأولى
                1 -> 6 // المراجعة الثانية (بعد 6 أيام)
                else -> (currentIntervalDays * currentEaseFactor).toInt() // المراجعات اللاحقة
            }

            // تعديل معامل السهولة بناءً على جودة الحفظ
            // معادلة SM-2 المعدلة
            val qualityValue = quality.value
            newEaseFactor += (0.1f - (3 - qualityValue) * (0.08f + (3 - qualityValue) * 0.02f))
        }

        // الحد الأدنى لمعامل السهولة هو 1.3 حتى لا تتكرر المراجعة بشكل مزعج
        if (newEaseFactor < 1.3f) newEaseFactor = 1.3f

        // حساب وقت المراجعة القادمة بالملي ثانية
        val nextReviewMillis = System.currentTimeMillis() + (newInterval * 24L * 60L * 60L * 1000L)

        return ReviewCalculationResult(
            newIntervalDays = newInterval,
            newEaseFactor = newEaseFactor,
            nextReviewTimestamp = nextReviewMillis
        )
    }
}
