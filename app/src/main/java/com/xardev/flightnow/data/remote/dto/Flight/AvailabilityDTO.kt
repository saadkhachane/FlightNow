package com.xardev.flightnow.data.remote.dto.Flight

import com.xardev.flightnow.models.Flight

data class AvailabilityDTO(
    val currPrecision: Int?,
    val currency: String?,
    val routeGroup: String?,
    val serverTimeUTC: String?,
    val termsOfUse: String?,
    val tripType: String?,
    val trips: List<TripDTO>?,
    val upgradeType: String?
) {

    fun toFlights() : List<Flight>{

        val flights = ArrayList<Flight>()

        if (!trips.isNullOrEmpty()){
            for(trip in trips){

                if (trip.dates.isNotEmpty()){
                    val date = trip.dates[0]

                    if (date.flights.isNotEmpty()){

                        for (flight in date.flights){
                            val fares = flight.regularFare.fares

                            flights.add(
                                Flight(
                                    tripType = tripType,
                                    currency = currency,
                                    duration = flight.duration,
                                    flightKey = flight.flightKey,
                                    flightNumber = flight.flightNumber,
                                    operatedBy = flight.operatedBy,
                                    amount = if (fares.isNotEmpty()) fares[0].amount.toString() else "",
                                    time = flight.time,
                                    timeUTC = flight.timeUTC
                                )
                            )

                        }

                    }

                }

            }
        }


        return flights
    }

}