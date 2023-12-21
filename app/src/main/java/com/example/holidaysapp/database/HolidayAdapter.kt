package com.example.holidaysapp.database

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.holidaysapp.R


class HolidayAdapter(
    private val clickListener: OnHolidayItemClickListener
) : ListAdapter<Holiday, HolidayAdapter.HolidayViewHolder>(HolidayDiffCallback()) {

    interface OnHolidayItemClickListener {
        fun onHolidayItemClick(position: Int)
    }

    inner class HolidayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val textViewHolidayName: TextView = itemView.findViewById(R.id.textViewHolidayName)
        private val textViewHolidayDate: TextView = itemView.findViewById(R.id.textViewHolidayDate)


        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                clickListener.onHolidayItemClick(position)
            }
        }

        fun bind(holiday: Holiday) {
            textViewHolidayName.text = holiday.name
            textViewHolidayDate.text = holiday.date
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolidayViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_holiday, parent, false)
        return HolidayViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HolidayViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    fun setData(newHolidayList: List<Holiday>) {
        submitList(newHolidayList)
    }
}
class HolidayDiffCallback : DiffUtil.ItemCallback<Holiday>() {
    override fun areItemsTheSame(oldItem: Holiday, newItem: Holiday): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Holiday, newItem: Holiday): Boolean {
        return oldItem == newItem
    }
}