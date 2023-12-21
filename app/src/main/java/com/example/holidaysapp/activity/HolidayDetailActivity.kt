package com.example.holidaysapp.activity


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.holidaysapp.database.HolidayDatabase
import com.example.holidaysapp.databinding.ActivityHolidayDetailBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.holidaysapp.viewmodel.HolidayDetailViewModel

class HolidayDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHolidayDetailBinding
    private lateinit var viewModel: HolidayDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHolidayDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val holidayName = intent.getStringExtra("selectedHolidayName") ?: ""
        val holidayCode = intent.getStringExtra("selectedHolidayCode") ?: ""

        viewModel = ViewModelProvider(this).get(HolidayDetailViewModel::class.java)
        val holidayDao = HolidayDatabase.getInstance(applicationContext).holidayDao()

        viewModel.getHolidayDetail().observe(this, Observer { holiday ->
            holiday?.let {
                binding.textViewName.text = it.name
                binding.textViewDate.text = "Date: ${it.date}"
                binding.textViewLocalName.text = "Local Name: ${it.localName}"
                binding.textViewCountryCode.text = "Country Code: ${it.countryCode}"
                binding.textViewFixed.text = "Fixed: ${it.isFixed}"
                binding.textViewGlobal.text = "Global: ${it.isGlobal}"
                binding.textViewTypes.text = "Types: ${it.types.joinToString()}"
            }
        })

        viewModel.fetchHolidayByNameAndCountry(holidayName, holidayCode, holidayDao)
    }
}