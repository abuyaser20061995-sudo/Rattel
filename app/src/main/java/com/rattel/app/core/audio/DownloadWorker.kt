package com.rattel.app.core.audio

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class DownloadWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val fileUrl = inputData.getString("FILE_URL") ?: return@withContext Result.failure()
        val fileName = inputData.getString("FILE_NAME") ?: return@withContext Result.failure()

        try {
            // تحديد مجلد الحفظ الآمن الخاص بالتطبيق (لا يتطلب صلاحيات تخزين خارجية)
            val audioDirectory = File(applicationContext.filesDir, "quran_audio")
            if (!audioDirectory.exists()) audioDirectory.mkdirs()

            val outputFile = File(audioDirectory, fileName)

            // تحميل الملف
            URL(fileUrl).openStream().use { input ->
                FileOutputStream(outputFile).use { output ->
                    input.copyTo(output)
                }
            }

            // إرجاع مسار الملف المحفوظ بنجاح
            val outputData = workDataOf("SAVED_PATH" to outputFile.absolutePath)
            Result.success(outputData)

        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }
}
