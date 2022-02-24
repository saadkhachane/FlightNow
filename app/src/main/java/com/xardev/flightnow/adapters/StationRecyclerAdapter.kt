package com.xardev.flightnow.adapters

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.xardev.flightnow.R
import com.xardev.flightnow.databinding.FlightItemBinding
import com.xardev.flightnow.databinding.StationItemBinding
import com.xardev.flightnow.models.Flight
import com.xardev.flightnow.models.Station
import com.xardev.flightnow.ui.SearchStationFragment
import com.xardev.flightnow.viewmodels.MainViewModel
import java.text.SimpleDateFormat

private const val TAG = "here"

class StationRecyclerAdapter(var context: Context, val fragment: SearchStationFragment)
    : RecyclerView.Adapter<StationRecyclerAdapter.StationViewHolder>() {

    private var list : List<Station> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder {
        val binder : StationItemBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.station_item, parent, false )
        return StationViewHolder(binder)
    }

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        val binder = holder.binder

        if (list.isNotEmpty()){
            val s = list[position]
            binder.station = s

            if (position == 0){
                binder.txtCountry.visibility = View.VISIBLE
            }else if (position > 0){
                if (list[position - 1].countryGroupName != s.countryGroupName){
                    binder.txtCountry.visibility = View.VISIBLE
                }
            }

            binder.cardStation.setOnClickListener {

                if (fragment.selectionType == "Departure")
                    fragment.viewModel.search_params.value?.set("origin", s.code!!)
                else
                    fragment.viewModel.search_params.value?.set("destination", s.code!!)

                fragment.findNavController().navigateUp()

            }

        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class StationViewHolder(var binder: StationItemBinding) : RecyclerView.ViewHolder(binder.root){

    }

    fun updateList(list: List<Station>) {
        this.list = list
        notifyDataSetChanged()
    }

    private fun animateItem(view: View, pos: Int){

        val translateY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 50f, 0f)
        val alpha = PropertyValuesHolder.ofFloat(View.ALPHA, 0f, 1f)

        ObjectAnimator.ofPropertyValuesHolder(
            view,
            translateY,
            alpha
        ).also {
            it.duration = 500
            it.interpolator = DecelerateInterpolator()
            it.startDelay = (pos * 300).toLong()
            it.start()
        }

    }
}