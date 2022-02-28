package com.xardev.flightnow.repositories

import android.util.Log
import com.xardev.flightnow.data.remote.ApiService
import com.xardev.flightnow.models.Flight
import com.xardev.flightnow.models.Station
import com.xardev.flightnow.utils.Constants
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

private const val TAG = "here"

class MainRepositoryImpl @Inject constructor(
    private val api: ApiService
) : MainRepository {

    override fun getStations(): Single<List<Station>> {

        return api.getStations(Constants.API_URL_STATIONS)
            .map { response ->
                if (response.isSuccessful) {
                    Log.d(TAG, "response: ${response.body()}")
                    if (response.body() != null) {
                        response.body()!!.stations.map { st -> st.toStation() }
                    } else {
                        emptyList()
                    }

                } else {
                    emptyList()
                }
            }
    }

    override fun getFlights(params: Map<String, String>): Single<List<Flight>> {

        return api.getFlights(params)
            .map { response ->
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        response.body()!!.toFlights()
                    } else {
                        emptyList()
                    }

                } else {
                    emptyList()
                }
            }
    }

}