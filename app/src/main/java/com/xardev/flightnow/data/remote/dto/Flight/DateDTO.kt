package com.xardev.flightnow.data.remote.dto.Flight

data class DateDTO(
    val dateOut: String,
    val flights: List<FlightDTO>
)