package com.rattel.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rattel.app.data.local.dao.QuranDao
import com.rattel.app.data.local.entities.AyahEntity
import com.rattel.app.data.local.entities.ReviewItemEntity
import com.rattel.app.data.local.entities.TafseerEntity

@Database(
    entities = [
        AyahEntity::class, 
        TafseerEntity::class, 
        ReviewItemEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class RattelDatabase : RoomDatabase() {
    
    // تعريف الـ DAOs المتاحة في هذه القاعدة
    abstract fun quranDao(): QuranDao
    
    // يمكن إضافة TafseerDao و ReviewDao هنا لاحقاً
}
