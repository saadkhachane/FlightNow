package com.xardev.flightnow.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.xardev.flightnow.R
import com.xardev.flightnow.adapters.FlightsRecyclerAdapter
import com.xardev.flightnow.adapters.StationRecyclerAdapter
import com.xardev.flightnow.databinding.FragmentSearchStationBinding
import com.xardev.flightnow.models.Station
import com.xardev.flightnow.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

private const val TAG = "here"

@AndroidEntryPoint
class SearchStationFragment : Fragment() {

    private lateinit var binding: FragmentSearchStationBinding

    val viewModel: MainViewModel by activityViewModels()

    private lateinit var adapter: StationRecyclerAdapter

    var selectionType : String? = ""

    val stations = ArrayList<Station>( emptyList() )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        selectionType = arguments?.get("selectionType").toString()
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
        binding.btnClose.setOnClickListener{
            findNavController().navigateUp()
        }
    }

    private fun setInputChangeListener() {
        binding.txtInput.doAfterTextChanged {s ->
            if (s != null) {
                if (s.length > 0){
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
        binding.txtTitle.text = selectionType
        if (selectionType == "Departure") binding.txtInputLayout.hint = "From"
        else binding.txtInputLayout.hint = "To"
    }

    private fun setObservers() {
        viewModel.isLoading.observe(requireActivity()){
            if (it != null){
                binding.loading.visibility = if(it) View.VISIBLE else View.GONE
                binding.recycler.visibility = if(it) View.INVISIBLE else View.VISIBLE
                try {
                    Glide.with(requireContext())
                        .load(R.drawable.loading)
                        .into(binding.loading)
                }catch (e: Exception){

                }

            }
        }

        viewModel.stations.observe(requireActivity()){
            if (it != null){
                stations.clear()

                val l = it.groupBy { it.countryGroupCode }

                l.forEach{
                    stations.addAll(
                        it.value
                    )
                }
                adapter.updateList(stations)
            }


        }

        viewModel.error.observe(requireActivity()){
            if (it != null){
                kotlin.runCatching {
                    Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                }

            }
        }

    }

    private fun setupRecycler() {
        adapter = StationRecyclerAdapter(requireContext(), this)
        binding.recycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recycler.adapter = adapter
    }

    private fun loadData() {
        viewModel.getStations()
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

}