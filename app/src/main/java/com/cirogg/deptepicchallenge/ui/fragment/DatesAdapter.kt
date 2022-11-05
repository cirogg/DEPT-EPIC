package com.cirogg.deptepicchallenge.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cirogg.deptepicchallenge.R
import com.cirogg.deptepicchallenge.databinding.ItemDateBinding
import com.cirogg.deptepicchallenge.model.response.DatesResponse

class DatesAdapter: RecyclerView.Adapter<DatesAdapter.DateViewHolder>()  {
    private var dates = emptyList<DatesResponse>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_date, parent, false)
        return DateViewHolder(view)
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        holder.bind(dates[position])
    }

    override fun getItemCount(): Int {
        return dates.size
    }

    inner class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemDateBinding.bind(itemView)

        fun bind(date: DatesResponse) {
            binding.dateTextView.text = date.date
        }
    }

    fun setData(listOfDates: MutableList<DatesResponse>, newPositiion: Int? = null){
        val dif = DiffUtil.calculateDiff(object : DiffUtil.Callback(){
            override fun getOldListSize(): Int {
                return dates.size
            }

            override fun getNewListSize(): Int {
                return listOfDates.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return dates[oldItemPosition].date == listOfDates[newItemPosition].date
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return dates[oldItemPosition] == listOfDates[newItemPosition]
            }
        })

        this.dates = listOfDates
        dif.dispatchUpdatesTo(this)

        newPositiion?.let {
            notifyItemChanged(it)
        }?: run {
            notifyDataSetChanged()
        }
    }
}