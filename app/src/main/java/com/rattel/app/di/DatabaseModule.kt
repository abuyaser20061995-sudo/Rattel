package com.rattel.app.di

import android.content.Context
import androidx.room.Room
import com.rattel.app.data.local.RattelDatabase
import com.rattel.app.data.local.dao.QuranDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    /**
     * إنشاء قاعدة البيانات بناءً على ملف موجود مسبقاً في مجلد Assets
     * (لأن المستخدم يجب أن يجد القرآن جاهزاً بدون إنترنت).
     */
    @Provides
    @Singleton
    fun provideRattelDatabase(@ApplicationContext context: Context): RattelDatabase {
        return Room.databaseBuilder(
            context,
            RattelDatabase::class.java,
            "rattel_quran.db"
        )
        // نسخ القاعدة المليئة بالبيانات من مجلد assets عند أول تشغيل
        .createFromAsset("databases/quran_v1.db") 
        .fallbackToDestructiveMigration()
        .build()
    }

    /**
     * توفير الـ QuranDao ليستدعيه الـ Repository أو الـ ViewModel
     */
    @Provides
    @Singleton
    fun provideQuranDao(database: RattelDatabase): QuranDao {
        return database.quranDao()
    }
}
