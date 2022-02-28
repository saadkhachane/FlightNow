package com.xardev.flightnow.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.xardev.flightnow.R
import com.xardev.flightnow.databinding.StationItemBinding
import com.xardev.flightnow.models.Station


class StationRecyclerAdapter(var context: Context, private var listener: EventListener)
    : RecyclerView.Adapter<StationRecyclerAdapter.StationViewHolder>() {

    private var list : List<Station> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder {
        val binder : StationItemBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.station_item, parent, false )
        return StationViewHolder(binder)
    }

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        val binder = holder.binder

        if (list.isNotEmpty()){
            val s = list[position]
            binder.station = s

            if (position == 0){
                binder.txtCountry.visibility = View.VISIBLE
            }else if (position > 0){
                if (list[position - 1].countryGroupName != s.countryGroupName){
                    binder.txtCountry.visibility = View.VISIBLE
                }
            }

            binder.cardStation.setOnClickListener {
                listener.onStationSelected(s.code!!)
            }

        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class StationViewHolder(var binder: StationItemBinding) : RecyclerView.ViewHolder(binder.root)

    fun updateList(list: List<Station>) {
        this.list = list
        notifyDataSetChanged()
    }

    interface EventListener{
        fun onStationSelected(station: String)
    }

}
