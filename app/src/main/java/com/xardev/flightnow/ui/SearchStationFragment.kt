package com.xardev.flightnow.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.xardev.flightnow.R
import com.xardev.flightnow.adapters.StationRecyclerAdapter
import com.xardev.flightnow.databinding.FragmentSearchStationBinding
import com.xardev.flightnow.models.Station
import com.xardev.flightnow.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.lang.Exception

private const val TAG = "SearchStationFragment"

@AndroidEntryPoint
class SearchStationFragment : Fragment(), StationRecyclerAdapter.EventListener {

    private lateinit var binding: FragmentSearchStationBinding

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var adapter: StationRecyclerAdapter

    // Station type 0 = departure , 1 = destination
    private var stationType: Int? = 0

    private val stations = ArrayList<Station>(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        stationType = arguments?.get("stationType").toString().toInt()
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

        setTitles()
        setupRecycler()
        setObservers()
        setClickListeners()

        setInputChangeListener()

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setClickListeners() {
        binding.btnClose.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setInputChangeListener() {
        binding.txtInput.doAfterTextChanged { s ->
            if (s != null) {
                if (s.isNotEmpty()) {
                    adapter.updateList(
                        stations.filter {
                            it.countryName!!.contains(s, ignoreCase = true)
                                    || it.name!!.contains(s, ignoreCase = true)
                        }
                    )
                }
            }
        }
    }

    private fun setTitles() {
        binding.txtTitle.text = if (stationType == 0) getString(R.string.departure)
        else getString(R.string.destination)

        if (stationType == 0) binding.txtInputLayout.hint = getString(R.string.from)
        else binding.txtInputLayout.hint = getString(R.string.to)
    }

    private fun setObservers() {
        viewModel.isLoading.observe(requireActivity()) {
            if (it != null) {

                binding.loading.visibility = if (it) View.VISIBLE else View.GONE
                binding.recycler.visibility = if (it) View.INVISIBLE else View.VISIBLE

                try {
                    Glide.with(requireContext())
                        .load(R.drawable.loading)
                        .into(binding.loading)
                } catch (e: Exception) {
                    Log.d(TAG, "Glide error: ${e.message}")
                }

            }
        }

        viewModel.stations.observe(requireActivity()) { list ->
            if (list != null) {
                stations.clear()

                // Group list by country name
                val l = list.groupBy { it.countryGroupCode }

                // add each item to station's list
                l.forEach {
                    stations.addAll(
                        it.value
                    )
                }

                // update list
                adapter.updateList(stations)
            }


        }

        viewModel.error.observe(requireActivity()) {
            if (it != null) {

                kotlin.runCatching {

                    if (it is IOException)

                        Snackbar.make(
                            binding.root,
                            getString(R.string.CONNEXION_PROBLEM),
                            Snackbar.LENGTH_LONG
                        ).show()
                    else

                        Snackbar.make(
                            binding.root,
                            getString(R.string.UNKNOWN_PROBLEM),
                            Snackbar.LENGTH_LONG
                        ).show()
                }

            }
        }

    }

    private fun setupRecycler() {
        adapter = StationRecyclerAdapter(requireContext(), this)
        binding.recycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recycler.adapter = adapter
    }

    private fun loadData() {
        viewModel.getStations()
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    override fun onStationSelected(station: String) {

        if (stationType == 0)
            viewModel.searchParams.value?.set("origin", station)
        else
            viewModel.searchParams.value?.set("destination", station)

        binding.txtInput.clearFocus()
        findNavController().navigateUp()
    }

}