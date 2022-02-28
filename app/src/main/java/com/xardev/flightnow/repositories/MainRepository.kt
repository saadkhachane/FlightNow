package com.xardev.flightnow.repositories

import com.xardev.flightnow.models.Flight
import com.xardev.flightnow.models.Station
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface MainRepository {

    fun getStations() : Single<List<Station>>

    fun getFlights(params: Map<String, String>) : Single<List<Flight>>

}