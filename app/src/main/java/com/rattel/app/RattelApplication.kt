package com.rattel.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * فئة التطبيق الرئيسية.
 * إضافة @HiltAndroidApp ضرورية جداً لبدء عمل نظام حقن الاعتماديات (Hilt).
 * سيقوم هذا النظام بإنشاء وتوفير قاعدة البيانات ومحرك الصوتيات تلقائياً لكل الشاشات.
 */
@HiltAndroidApp
class RattelApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        
        // هنا يمكننا مستقبلاً تهيئة أشياء تعمل فور فتح التطبيق مثل:
        // 1. استخراج ملفات نموذج الذكاء الاصطناعي (Vosk Model) وفك ضغطها في التخزين المحلي.
        // 2. تهيئة إعدادات الوضع الليلي/النهاري المحفوظة للمستخدم.
        // 3. التأكد من وجود ملفات قاعدة بيانات المصحف.
        
        initVoiceRecognitionModel()
    }

    private fun initVoiceRecognitionModel() {
        // كود وهمي حالياً، سيتم استبداله لاحقاً بالكود الفعلي لفك ضغط نموذج Vosk
        // لتجهيزه للعمل بدون إنترنت فور دخول المستخدم لشاشة التسميع.
    }
}
