package com.example.holidaysapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.holidaysapp.database.Holiday
import com.example.holidaysapp.database.HolidayApiService
import com.example.holidaysapp.database.HolidayDao
import com.example.holidaysapp.database.HolidayEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class HolidayListViewModel : ViewModel() {

    private val holidayList: MutableLiveData<List<Holiday>> = MutableLiveData()

    fun getHolidayList(): LiveData<List<Holiday>> = holidayList

    private val holidayApiService: HolidayApiService = Retrofit.Builder()
        .baseUrl("https://date.nager.at/")
        .addConverterFactory(JacksonConverterFactory.create())
        .build()
        .create(HolidayApiService::class.java)

    fun fetchHolidays(year: Int, countryCode: String, holidayDao: HolidayDao) {
        val call = holidayApiService.getPublicHolidays(year, countryCode)
        call.enqueue(object : Callback<List<Holiday>> {
            override fun onResponse(call: Call<List<Holiday>>, response: Response<List<Holiday>>) {
                if (response.isSuccessful) {
                    val holidays: List<Holiday>? = response.body()
                    holidays?.let {
                        val existingHolidays = holidayDao.findHolidayByCountryCode(countryCode)
                        val newHolidays = it.filterNot { holiday ->
                            existingHolidays.any { existingHoliday ->
                                existingHoliday.name == holiday.name && existingHoliday.date == holiday.date
                            }
                        }

                        val holidayEntities = newHolidays.map { holiday ->
                            HolidayEntity(
                                name = holiday.name,
                                date = holiday.date,
                                localName = holiday.localName,
                                countryCode = holiday.countryCode,
                                isFixed = holiday.isFixed,
                                isGlobal = holiday.isGlobal,
                                types = holiday.types
                            )
                        }

                        Log.d("myLog", holidayEntities.toString())
                        holidayDao.insertHolidays(holidayEntities)

                        val updatedHolidayList = holidayDao.findHolidayByCountryCode(countryCode).map { holidayEntity ->
                            Holiday(
                                name = holidayEntity.name,
                                date = holidayEntity.date,
                                localName = holidayEntity.localName,
                                countryCode = holidayEntity.countryCode,
                                isFixed = holidayEntity.isFixed,
                                isGlobal = holidayEntity.isGlobal,
                                types = holidayEntity.types
                            )
                        }
                        holidayList.postValue(updatedHolidayList) // Оновити список свят у ViewModel
                    }
                } else {
                    Log.d("myLog", "Помилка fetchAndSaveHolidays")
                }
            }

            override fun onFailure(call: Call<List<Holiday>>, t: Throwable) {
                Log.d("myLog", "Помилка onFailure: ${t.message}")
            }
        })
    }
}