package com.xardev.flightnow.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_UP
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.slider.RangeSlider
import com.google.android.material.slider.Slider
import com.google.android.material.snackbar.Snackbar
import com.xardev.flightnow.R
import com.xardev.flightnow.adapters.FlightsRecyclerAdapter
import com.xardev.flightnow.databinding.FragmentSearchFlightsBinding
import com.xardev.flightnow.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


private const val TAG = "here"

@AndroidEntryPoint
class SearchFlightsFragment : Fragment() {

    private lateinit var binding : FragmentSearchFlightsBinding

    private lateinit var adapter: FlightsRecyclerAdapter

     val viewModel: MainViewModel by activityViewModels()

    var params = HashMap<String, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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

        setupRecycler()
        setObservers()
        setClickListeners()

        return binding.root
    }

    private fun setClickListeners() {
        binding.btnDeparture.setOnClickListener {

            val bundle = Bundle()
            bundle.putCharSequence("selectionType", "Departure")

            findNavController()
                .navigate(R.id.action_searchFlightsFragment_to_searchStationFragment
                ,bundle)
        }
        binding.btnDestination.setOnClickListener {
            val bundle = Bundle()
            bundle.putCharSequence("selectionType", "Destination")

            findNavController()
                .navigate(R.id.action_searchFlightsFragment_to_searchStationFragment
                    ,bundle)
        }

        binding.btnSwap.setOnClickListener {
            val x = params["origin"]
            params["origin"] = params["destination"]!!
            params["destination"] = x!!
            viewModel.search_params.postValue(params)
        }

        binding.btnAddAdult.setOnClickListener {
            addPassenger(binding.txtAdult, "adt")
        }
        binding.btnRemoveAdult.setOnClickListener {
            removePassenger(binding.txtAdult, "adt")
        }
        binding.btnAddChild.setOnClickListener {
            addPassenger(binding.txtChild, "chd")
        }
        binding.btnRemoveChild.setOnClickListener {
            removePassenger(binding.txtChild, "chd")
        }
        binding.btnAddTeen.setOnClickListener {
            addPassenger(binding.txtTeen, "teen")
        }
        binding.btnRemoveTeen.setOnClickListener {
            removePassenger(binding.txtTeen, "teen")
        }
        binding.btnDate.setOnClickListener {
            showDateDialog()
        }
        binding.btnBest.setOnClickListener {
            loadData()
        }
        binding.btncheapest.setOnClickListener {
            loadData()
        }
        binding.btnExpand.setOnClickListener {
            if(binding.layoutPassengers.visibility == View.VISIBLE) hideView(binding.layoutPassengers)
            else showView(binding.layoutPassengers)
        }
        binding.rangeSlider.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener{
            @SuppressLint("RestrictedApi")
            override fun onStartTrackingTouch(slider: RangeSlider) {
            }

            @SuppressLint("RestrictedApi")
            override fun onStopTrackingTouch(slider: RangeSlider) {
                binding.txtPriceMin.text = slider.values[0].toString()
                binding.txtPriceMax.text = slider.values[1].toString()
                loadData()
            }

        })

    }

    private fun showDateDialog() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        val dpd = DatePickerDialog(requireContext(), { view, year, monthOfYear, dayOfMonth ->

            params["dateout"] = "$year-${monthOfYear + 1}-$dayOfMonth"
            viewModel.search_params.postValue(params)

        }, year, month, day)
        dpd.datePicker.minDate = System.currentTimeMillis()
        dpd.show()
    }

    private fun addPassenger(view: Button, s: String) {
        var n = view.text.toString().toInt()
        n++
        params[s] = n.toString()
        viewModel.search_params.postValue(params)
    }

    private fun removePassenger(view: Button, s: String) {
        var n = view.text.toString().toInt()
        if (n > 0) n--
        params[s] = n.toString()
        viewModel.search_params.postValue(params)
    }

    private fun setObservers() {

        viewModel.isLoading.observe(requireActivity()){
            if (it != null){

            }
        }

        viewModel.flights.observe(requireActivity()){ list ->
            if (list != null){
                if(list.isNotEmpty()){
                    binding.txtNoFlights.visibility = View.GONE

                    val minPrice = binding.rangeSlider.values[0]
                    val maxPrice = binding.rangeSlider.values[1]

                    var flights = list.filter { it.amount?.toFloat()!! in minPrice..maxPrice }

                    if (binding.btnBest.isChecked) adapter.updateList(flights)
                    else if (binding.btncheapest.isChecked){
                        flights = flights.sortedBy { it.amount?.toFloat() }
                        adapter.updateList(flights)
                    }

                    if (flights.isEmpty())
                        binding.txtNoFlights.visibility = View.VISIBLE

                }else {
                    binding.txtNoFlights.visibility = View.VISIBLE
                }
            }


        }

        viewModel.error.observe(requireActivity()){
            if (it != null){
                kotlin.runCatching {
                    Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
                }
            }
        }

        viewModel.search_params.observe(requireActivity()){
            params = it
            bindParams()
        }
    }

    private fun setupRecycler() {
        adapter = FlightsRecyclerAdapter(requireContext(), this)
        binding.recycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recycler.adapter = adapter
    }

    private fun loadData() {

        if(allParamsSet()){

            viewModel.getFlights(params)
        }
    }

    private fun allParamsSet(): Boolean {
        try{
            return params["dateout"] != "" &&
                    params["origin"] != "" &&
                    params["destination"] != "" &&
                    params["adt"]?.toFloat()!! >= 0f &&
                    params["chd"]?.toFloat()!! >= 0f &&
                    params["teen"]?.toFloat()!! >= 0f &&
                    params["inf"]?.toFloat()!! >= 0f &&
                    params["ToUs"] == "AGREED"
        }catch (e: Exception){
            return false
        }

    }

    override fun onResume() {
        super.onResume()
        bindParams()
    }

    private fun bindParams() {

        if (!params.isNullOrEmpty()){
            binding.txtDeparture.text = if (params["origin"] != "") params["origin"] else "Departure"
            binding.txtDestination.text = if (params["destination"] != "") params["destination"] else "Destination"
            binding.btnDate.text = if (params["dateout"] != "") params["dateout"] else "Pick a date"
            binding.txtAdult.text = params["adt"]
            binding.txtChild.text = params["chd"]
            binding.txtTeen.text = params["teen"]

            binding.txtPassenger.text = ""
            val passengers = StringBuilder()

            if (params["adt"]?.toInt()!! >= 1){
                passengers.append("${params["adt"]} Adult ")
                binding.txtAdult
            }
            if (params["teen"]?.toInt()!! >= 1){
                passengers.append("${params["teen"]} Teen ")
            }
            if (params["chd"]?.toInt()!! >= 1){
                passengers.append("${params["chd"]} Child")
            }
            binding.txtPassenger.text = passengers.toString()

            // Loading Data..
            loadData()
        }

    }

    private fun hideView(view: View) {
        TransitionManager.beginDelayedTransition(binding.root as ViewGroup?, AutoTransition())
        view.visibility = View.GONE
        binding.btnExpand.rotation = 90f
    }

    private fun showView(view: View) {
        TransitionManager.beginDelayedTransition(binding.root as ViewGroup?, AutoTransition())
        view.visibility = View.VISIBLE
        binding.btnExpand.rotation = 0f

    }

}