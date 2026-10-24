package com.rattel.app.di

import android.content.Context
import androidx.room.Room
import com.rattel.app.data.local.QuranDao
import com.rattel.app.data.local.QuranDatabase
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
    fun provideQuranDatabase(@ApplicationContext context: Context): QuranDatabase {
        return Room.databaseBuilder(
            context,
            QuranDatabase::class.java,
            "quran_database.db"
        )
        // هذا السطر مهم جداً: يسحب قاعدة البيانات الجاهزة من ملفات التطبيق
        .createFromAsset("databases/quran.db") 
        .fallbackToDestructiveMigration()
        .build()
    }

    @Provides
    @Singleton
    fun provideQuranDao(database: QuranDatabase): QuranDao {
        return database.quranDao()
    }
}
