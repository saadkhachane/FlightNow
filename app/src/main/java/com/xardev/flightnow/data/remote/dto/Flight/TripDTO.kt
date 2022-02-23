package com.xardev.flightnow.data.remote.dto.Flight

data class TripDTO(
    val dates: List<DateDTO>,
    val destination: String,
    val destinationName: String,
    val origin: String,
    val originName: String,
    val routeGroup: String,
    val tripType: String,
    val upgradeType: String
)