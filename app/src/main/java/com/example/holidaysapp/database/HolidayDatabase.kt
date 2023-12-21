package com.example.holidaysapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [HolidayEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class HolidayDatabase : RoomDatabase() {
    abstract fun holidayDao(): HolidayDao

    companion object {
        @Volatile
        private var INSTANCE: HolidayDatabase? = null

        fun getInstance(context: Context): HolidayDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HolidayDatabase::class.java,
                    "holiday_database"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                instance
            }
        }
    }
}