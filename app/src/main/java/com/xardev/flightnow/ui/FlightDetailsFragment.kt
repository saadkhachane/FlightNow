package com.xardev.flightnow.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.xardev.flightnow.R
import com.xardev.flightnow.databinding.FragmentFlightDetailsBinding
import com.xardev.flightnow.models.Flight
import java.text.SimpleDateFormat


class FlightDetailsFragment : Fragment() {

    private lateinit var binding: FragmentFlightDetailsBinding

    var flight: Flight? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        flight = arguments?.get("flight") as Flight
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_flight_details,
            container,
            false
        )

        if (flight != null){

            binding.flight = flight
        }else {
            findNavController().navigateUp()
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root
    }

    private fun formatTime(date: String) : String{
        val format1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val dt1 = format1.parse(date.replace(".000",""))

        val format2 = SimpleDateFormat("HH:mm")

        return "${format2.format(dt1)}"
    }

    private fun formatDuration(s: String) : String{
        return "${s.replace(":","h ")}m"
    }

}