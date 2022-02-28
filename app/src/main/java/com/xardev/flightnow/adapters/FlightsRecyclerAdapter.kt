package com.xardev.flightnow.adapters

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.xardev.flightnow.R
import com.xardev.flightnow.databinding.FlightItemBinding
import com.xardev.flightnow.models.Flight
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
            flight.time = listOf(formatTime(flight.time!![0]), formatTime(flight.time!![1]))
            if (flight.operatedBy.isNullOrBlank()) flight.operatedBy = context.getString(R.string.ryanair)
            binder.flight = flight

//            val time = "${formatTime(flight.time!![0])} - ${formatTime(flight.time!![1])}"
//            binder.txtTime.text = time

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

            animateItem(binder.cardFlight, 1f)
            animateItem(binder.cardFlightShadow, 0.2f)

        }

    }


    private fun formatTime(dateString: String): String {
        try {
            val dt : LocalDateTime

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dt = LocalDateTime.parse(dateString)
                return "${dt.hour}:${dt.minute}"
            }
            return ""

        } catch (e: Exception) {
            return dateString
        }

    }

    private fun formatDuration(s: String): String {

        return "${s.replace(":", "h ")}m"
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class FlightViewHolder(var binder: FlightItemBinding) : RecyclerView.ViewHolder(binder.root)

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<Flight>) {
        this.list = list
        notifyDataSetChanged()
    }

    private fun animateItem(view: View, alpha: Float) {

        val translateY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 50f, 0f)
        val alfa = PropertyValuesHolder.ofFloat(View.ALPHA, 0f, alpha)

        ObjectAnimator.ofPropertyValuesHolder(
            view,
            translateY,
            alfa
        ).also {
            it.duration = 300
            it.interpolator = DecelerateInterpolator()
            it.start()
        }

    }
}