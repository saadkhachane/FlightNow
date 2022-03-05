package com.xardev.flightnow.utils

import androidx.recyclerview.widget.DiffUtil
import com.xardev.flightnow.models.Flight
import com.xardev.flightnow.models.Station

class MyDiffUtil<T>(
    private val oldList: List<T>,
    private val newList: List<T>
) : DiffUtil.Callback() {


    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

        return when (oldList[oldItemPosition]) {
            is Station -> {
                val oldItem = oldList[oldItemPosition] as Station
                val newItem = newList[newItemPosition] as Station
                oldItem.code == newItem.code
            }
            is Flight -> {
                val oldItem = oldList[oldItemPosition] as Flight
                val newItem = newList[newItemPosition] as Flight
                oldItem.flightNumber == newItem.flightNumber
            }
            else -> {
                false
            }
        }

    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}