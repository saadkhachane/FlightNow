package com.xardev.flightnow.repositories

import android.util.Log
import com.google.gson.Gson
import com.xardev.flightnow.R
import com.xardev.flightnow.data.remote.ApiService
import com.xardev.flightnow.data.remote.dto.Flight.AvailabilityDTO
import com.xardev.flightnow.data.remote.dto.Station.StationsDTO
import com.xardev.flightnow.models.Flight
import com.xardev.flightnow.models.Station
import com.xardev.flightnow.utils.Constants
import com.xardev.flightnow.utils.Result.*
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.http.Query
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject
import com.xardev.flightnow.utils.Result as Result

private const val TAG = "here"

class MainRepositoryImpl @Inject constructor(
    private val api: ApiService
) : MainRepository {

    override fun getStations(): Single<List<Station>> {

        return api.getStations(Constants.API_URL_STATIONS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { response ->
                if (response.isSuccessful){
                    Log.d(TAG, "response: ${response.body()}")
                    if (response.body() != null){
                        response.body()!!.stations.map { st -> st.toStation() }
                    }else {
                        emptyList()
                    }

                }else {
                    emptyList()
                }
            }
    }

    override fun getFlights(params: Map<String, String>): Single<List<Flight>> {

        return api.getFlights(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { response ->
                if (response.isSuccessful){
                    Log.d(TAG, "response: ${response.body()}")
                    if (response.body() != null){
                        response.body()!!.toFlights()
                    }else {
                        emptyList()
                    }

                }else {
                    Log.d(TAG, "Error response: ${response.errorBody()!!.string()}")
                    emptyList()
                }
            }
    }

}