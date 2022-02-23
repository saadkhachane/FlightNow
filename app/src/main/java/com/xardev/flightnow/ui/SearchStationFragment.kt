package com.xardev.flightnow.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.xardev.flightnow.R
import com.xardev.flightnow.databinding.FragmentSearchStationBinding


class SearchStationFragment : Fragment() {

    private lateinit var binding: FragmentSearchStationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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

        // Inflate the layout for this fragment
        return binding.root
    }

}