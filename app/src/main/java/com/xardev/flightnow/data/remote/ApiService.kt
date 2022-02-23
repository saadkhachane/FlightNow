package com.xardev.flightnow.data.remote

import com.xardev.flightnow.data.remote.dto.Flight.AvailabilityDTO
import com.xardev.flightnow.data.remote.dto.Flight.FlightDTO
import com.xardev.flightnow.data.remote.dto.Station.StationDTO
import com.xardev.flightnow.data.remote.dto.Station.StationsDTO
import com.xardev.flightnow.utils.Constants
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap
import retrofit2.http.Url


interface ApiService {

    @GET
    fun getStations( @Url url: String ) : Single<Response<StationsDTO>>

    @GET("Availability")
    fun getFlights( @QueryMap params: Map<String, String> ) : Single<Response<AvailabilityDTO>>

}