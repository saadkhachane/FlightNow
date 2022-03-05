package com.xardev.flightnow.adapters

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.xardev.flightnow.R
import com.xardev.flightnow.databinding.FlightItemBinding
import com.xardev.flightnow.models.Flight
import com.xardev.flightnow.utils.MyDiffUtil
import java.lang.Exception
import java.time.LocalDateTime

class FlightsRecyclerAdapter(var context: Context) :
    RecyclerView.Adapter<FlightsRecyclerAdapter.FlightViewHolder>() {

    private var list: List<Flight> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightViewHolder {
        val binder: FlightItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.flight_item,
            parent,
            false
        )
        return FlightViewHolder(binder)
    }

    override fun onBindViewHolder(holder: FlightViewHolder, position: Int) {
        val binder = holder.binder

        if (list.isNotEmpty()) {
            val flight = list[position]

            flight.duration = formatDuration(flight.duration!!)

            // Format time
            val (time1, time2) = listOf(formatTime(flight.time!![0]), formatTime(flight.time!![1]))

            flight.time = listOf(time1, time2)

            val time = "$time1 - $time2"
            binder.txtTime.text = time

            // flight operator
            if (flight.operatedBy.isNullOrBlank()) flight.operatedBy =
                context.getString(R.string.ryanair)
            binder.flight = flight

            // Airports
            val airports = "${flight.origin} - ${flight.destination}"
            binder.txtAirports.text = airports

            binder.cardFlight.setOnClickListener {
                val bundle = Bundle()
                bundle.putParcelable("flight", flight)

                Navigation.findNavController(binder.view).navigate(
                    R.id.action_searchFlightsFragment_to_flightDetailsFragment,
                    bundle
                )
            }

        }

    }


    private fun formatTime(dateString: String): String {
        return try {
            val dt: LocalDateTime

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dt = LocalDateTime.parse(dateString)
                "${dt.hour}:${dt.minute}"
            } else {
                ""
            }

        } catch (e: Exception) {
            dateString
        }

    }

    private fun formatDuration(s: String): String {
        var fd = s.replace(":", "h ")
        fd = fd.replace("m", "")
        return "${fd}m"
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class FlightViewHolder(var binder: FlightItemBinding) : RecyclerView.ViewHolder(binder.root)


    fun updateList(list: List<Flight>) {

        val diffUtil = MyDiffUtil(this.list, list)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        this.list = list

        diffResult.dispatchUpdatesTo(this)
    }

}