package com.xardev.flightnow.data.remote

import com.xardev.flightnow.data.remote.dto.flight.AvailabilityDTO
import com.xardev.flightnow.data.remote.dto.station.StationsDTO
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap
import retrofit2.http.Url


interface ApiService {

    @GET
    fun getStations( @Url url: String ) : Single<Response<StationsDTO>>

    @GET("Availability")
    fun getFlights( @QueryMap params: Map<String, String> ) : Single<Response<AvailabilityDTO>>

}