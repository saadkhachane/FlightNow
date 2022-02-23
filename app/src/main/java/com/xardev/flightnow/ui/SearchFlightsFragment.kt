package com.xardev.flightnow.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.xardev.flightnow.R
import com.xardev.flightnow.databinding.FragmentSearchFlightsBinding
import com.xardev.flightnow.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFlightsFragment : Fragment() {

    private lateinit var binding : FragmentSearchFlightsBinding

    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_search_flights,
            container,
            false
        )
        binding.lifecycleOwner = this

        viewModel.getStations()

        val params = HashMap<String, String>()
        params["dateout"] = "2022-02-28"
        params["origin"] = "WRO"
        params["destination"] = "STN"
        params["adt"] = "5"
        params["chd"] = "0"
        params["teen"] = "0"
        params["inf"] = "0"
        params["ToUs"] = "AGREED"

        viewModel.getFlights(params)

                // Inflate the layout for this fragment
        return binding.root
    }

}