package com.rattel.app.di

import android.content.Context
import androidx.room.Room
import com.rattel.app.data.local.RattelDatabase
import com.rattel.app.data.local.dao.QuranDao
import com.rattel.app.data.local.dao.NoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideRattelDatabase(@ApplicationContext context: Context): RattelDatabase {
        return Room.databaseBuilder(
            context,
            RattelDatabase::class.java,
            "rattel_quran.db"
        )
        // أوقفنا جلب الملف من assets مؤقتاً لتجنب انهيار التطبيق إذا لم تكن قد رفعت ملف البيانات بعد
        .fallbackToDestructiveMigration()
        .build()
    }

    @Provides
    @Singleton
    fun provideQuranDao(database: RattelDatabase): QuranDao {
        return database.quranDao()
    }

    @Provides
    @Singleton
    fun provideNoteDao(database: RattelDatabase): NoteDao {
        return database.noteDao()
    }
}
