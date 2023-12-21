package com.example.holidaysapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.holidaysapp.database.HolidayDao
import com.example.holidaysapp.database.HolidayEntity

class HolidayDetailViewModel : ViewModel() {

    private val holidayDetail: MutableLiveData<HolidayEntity> = MutableLiveData()
    fun getHolidayDetail(): LiveData<HolidayEntity> = holidayDetail

    fun fetchHolidayByNameAndCountry(holidayName: String, holidayCode: String, holidayDao: HolidayDao) {
        val holiday = holidayDao.findHolidayByNameAndCountry(holidayName, holidayCode)
        holidayDetail.value = holiday
    }
}