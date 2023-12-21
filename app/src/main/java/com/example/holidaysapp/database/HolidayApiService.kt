package com.example.holidaysapp.database

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface HolidayApiService {
    @GET("/api/v3/PublicHolidays/{year}/{countryCode}")
    fun getPublicHolidays(
        @Path("year") year: Int,
        @Path("countryCode") countryCode: String
    ): Call<List<Holiday>>
}

