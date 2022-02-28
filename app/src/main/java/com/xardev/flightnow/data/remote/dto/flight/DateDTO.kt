package com.xardev.flightnow.data.remote.dto.flight

data class DateDTO(
    val dateOut: String,
    val flights: List<FlightDTO>
)