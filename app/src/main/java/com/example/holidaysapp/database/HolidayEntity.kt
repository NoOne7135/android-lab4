package com.example.holidaysapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "holidays")
class HolidayEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: String,
    val localName: String,
    val name: String,
    val countryCode: String,
    val isFixed: Boolean,
    val isGlobal: Boolean,
    val types: List<String>
)