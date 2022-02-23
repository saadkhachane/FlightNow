package com.xardev.flightnow.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.xardev.flightnow.R
import com.xardev.flightnow.databinding.FragmentFlightDetailsBinding


class FlightDetailsFragment : Fragment() {

    private lateinit var binding: FragmentFlightDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_search_station,
            container,
            false
        )

        return binding.root
    }

}