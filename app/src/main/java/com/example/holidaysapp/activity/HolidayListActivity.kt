package com.example.holidaysapp.activity

import android.content.Intent
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.holidaysapp.database.HolidayAdapter
import com.example.holidaysapp.database.HolidayDatabase
import com.example.holidaysapp.databinding.ActivityHolidayListBinding
import com.example.holidaysapp.viewmodel.HolidayListViewModel

class HolidayListActivity : AppCompatActivity(), HolidayAdapter.OnHolidayItemClickListener {

    private lateinit var holidayAdapter: HolidayAdapter
    private lateinit var viewModel: HolidayListViewModel
    private lateinit var binding: ActivityHolidayListBinding
    private lateinit var selectedCountryCode: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHolidayListBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        holidayAdapter = HolidayAdapter(this)
        binding.recyclerView.adapter = holidayAdapter
        selectedCountryCode = "UA"
        setUpButtons()
        viewModel = ViewModelProvider(this)[HolidayListViewModel::class.java]
        viewModel.getHolidayList().observe(this, Observer {
            it?.let { holidays -> holidayAdapter.setData(holidays) }
        })

        fetchHolidays()
    }

    private fun fetchHolidays() {
        var holidayDao = HolidayDatabase.getInstance(applicationContext).holidayDao()
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        viewModel.fetchHolidays(currentYear, selectedCountryCode, holidayDao)
    }

    private fun setUpButtons() {
        binding.bUA.setOnClickListener {
            selectedCountryCode = "UA"
            fetchHolidays()
        }

        binding.bUS.setOnClickListener {
            selectedCountryCode = "US"
            fetchHolidays()
        }
    }

    override fun onHolidayItemClick(position: Int) {
        val intent = Intent(this, HolidayDetailActivity::class.java)
        val selectedHolidayName = holidayAdapter.currentList[position].name
        val selectedHolidayCode = holidayAdapter.currentList[position].countryCode
        Log.d("myLog", selectedHolidayCode)
        intent.putExtra("selectedHolidayName", selectedHolidayName)
        intent.putExtra("selectedHolidayCode", selectedHolidayCode)
        startActivity(intent)
    }
}
