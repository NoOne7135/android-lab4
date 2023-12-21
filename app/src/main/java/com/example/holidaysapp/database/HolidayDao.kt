package com.example.holidaysapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HolidayDao {
    @Query("SELECT * FROM holidays")
    fun getAllHolidays(): List<HolidayEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHolidays(holidays: List<HolidayEntity>)

    @Query("SELECT * FROM holidays WHERE countryCode = :countryCode")
    fun findHolidayByCountryCode(countryCode: String): List<HolidayEntity>

    @Query("SELECT * FROM holidays WHERE name = :holidayName AND countryCode = :countryCode")
    fun findHolidayByNameAndCountry(holidayName: String, countryCode: String): HolidayEntity?

    @Query("DELETE FROM holidays")
    fun clearAllTables()
}