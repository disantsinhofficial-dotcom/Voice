package com.example.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [CustomCommandEntity::class, DeviceProfileEntity::class, AudioPresetEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun customCommandDao(): CustomCommandDao
    abstract fun deviceProfileDao(): DeviceProfileDao
    abstract fun audioPresetDao(): AudioPresetDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "music_voice_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
