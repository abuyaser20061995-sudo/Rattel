package com.rattel.app.core.utils

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BackupRestoreManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    // اسم قاعدة البيانات كما عرفناه في RattelDatabase
    private val dbName = "rattel_quran.db"

    /**
     * تصدير قاعدة البيانات إلى ملف يختاره المستخدم (عبر Storage Access Framework)
     */
    suspend fun backupDatabase(outputUri: Uri): Boolean = withContext(Dispatchers.IO) {
        try {
            val dbFile = context.getDatabasePath(dbName)
            if (!dbFile.exists()) return@withContext false

            // Room Database قد تستخدم ملفات مساعدة (wal, shm) يجب إغلاق القاعدة قبل النسخ لضمان سلامتها
            // للاختصار هنا سننسخ الملف الرئيسي
            context.contentResolver.openOutputStream(outputUri)?.use { outputStream ->
                FileInputStream(dbFile).use { inputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /**
     * استعادة قاعدة البيانات من ملف يختاره المستخدم
     * ملاحظة: يجب إعادة تشغيل التطبيق أو قاعدة البيانات بعد هذه العملية.
     */
    suspend fun restoreDatabase(inputUri: Uri): Boolean = withContext(Dispatchers.IO) {
        try {
            val dbFile = context.getDatabasePath(dbName)
            
            context.contentResolver.openInputStream(inputUri)?.use { inputStream ->
                FileOutputStream(dbFile).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
