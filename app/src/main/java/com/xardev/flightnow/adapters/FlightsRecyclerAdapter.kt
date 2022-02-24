package com.xardev.flightnow.adapters

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.xardev.flightnow.R
import com.xardev.flightnow.databinding.FlightItemBinding
import com.xardev.flightnow.models.Flight
import com.xardev.flightnow.ui.SearchFlightsFragment
import java.lang.Exception
import java.text.SimpleDateFormat

class FlightsRecyclerAdapter(var context: Context, val fragment: SearchFlightsFragment)
    : RecyclerView.Adapter<FlightsRecyclerAdapter.FlightViewHolder>() {

    private var list : List<Flight> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightViewHolder {
        val binder : FlightItemBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.flight_item, parent, false )
        return FlightViewHolder(binder)
    }

    override fun onBindViewHolder(holder: FlightViewHolder, position: Int) {
        val binder = holder.binder

        if (list.isNotEmpty()){
            val flight = list[position]

            if (flight != null){

                flight.duration = formatDuration(flight.duration!!)
                flight.time = listOf(formatTime(flight.time!![0]), formatTime(flight.time!![1]))
                if (flight.operatedBy.isNullOrBlank()) flight.operatedBy = "Ryanair"
                binder.flight = flight

                binder.txtTime.text = "${formatTime(flight.time!![0])} - ${formatTime(flight.time!![1])}"
                binder.txtAirports.text = flight.origin + " - " + flight.destination

                binder.cardFlight.setOnClickListener {
                    var bundle = Bundle()
                    bundle.putParcelable("flight", flight)
                    fragment.findNavController().navigate(R.id.action_searchFlightsFragment_to_flightDetailsFragment,
                        bundle)
                }

                animateItem(binder.cardFlight, 1f)
                animateItem(binder.cardFlightShadow, 0.2f)
            }

        }

    }


    private fun formatTime(date: String) : String{
        try {
            val format1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val dt1 = format1.parse(date.replace(".000",""))

            val format2 = SimpleDateFormat("HH:mm")

            return "${format2.format(dt1)}"
        }catch (e:Exception) {
            return date
        }

    }

    private fun formatDuration(s: String) : String{

        return "${s.replace(":","h ")}m"
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class FlightViewHolder(var binder: FlightItemBinding) : RecyclerView.ViewHolder(binder.root){

    }

    fun updateList(list: List<Flight>) {
        this.list = list
        notifyDataSetChanged()
    }

    private fun animateItem(view: View, alpha: Float){

        val translateY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 50f, 0f)
        val alpha = PropertyValuesHolder.ofFloat(View.ALPHA, 0f, alpha)

        ObjectAnimator.ofPropertyValuesHolder(
            view,
            translateY,
            alpha
        ).also {
            it.duration = 300
            it.interpolator = DecelerateInterpolator()
            it.start()
        }

    }
}