package com.rattel.app.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * جدول الآيات القرآنية.
 * يحتوي على جميع التفاصيل التي نحتاجها لرسم المصحف والبحث فيه.
 */
@Entity(tableName = "quran_ayahs")
data class AyahEntity(
    @PrimaryKey val id: Int, // رقم الآية التراكمي في المصحف (من 1 إلى 6236)
    val surahNumber: Int,    // رقم السورة (1 إلى 114)
    val ayahNumber: Int,     // رقم الآية داخل السورة
    val pageNumber: Int,     // رقم الصفحة في مصحف المدينة (1 إلى 604)
    val jozNumber: Int,      // رقم الجزء
    val hizbQuarter: Int,    // ربع الحزب
    val textUthmani: String, // النص بالرسم العثماني مع التشكيل (للعرض)
    val textClean: String,   // النص الإملائي بدون تشكيل (للبحث السريع)
    val isSajdah: Boolean    // هل تحتوي الآية على سجدة تلاوة؟
)

/**
 * جدول التفاسير.
 */
@Entity(tableName = "tafseer")
data class TafseerEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val ayahId: Int,         // يربط التفسير بالآية في الجدول السابق
    val sourceId: Int,       // 1 = الميسر، 2 = ابن كثير، 3 = السعدي
    val text: String         // نص التفسير
)

/**
 * جدول الحفظ والمراجعة (Spaced Repetition).
 * هذا الجدول هو سر الميزة الذكية لمراجعة الحفظ.
 */
@Entity(tableName = "memorization_review")
data class ReviewItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val startAyahId: Int,        // بداية المقطع المحفوظ
    val endAyahId: Int,          // نهاية المقطع المحفوظ
    val nextReviewDate: Long,    // موعد المراجعة القادمة (Timestamp)
    val intervalDays: Int,       // عدد الأيام حتى المراجعة القادمة
    val easeFactor: Float        // معامل السهولة (يتغير بناءً على أخطاء المستخدم)
)
