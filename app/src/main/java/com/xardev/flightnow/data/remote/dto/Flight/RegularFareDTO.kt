package com.xardev.flightnow.data.remote.dto.Flight

data class RegularFareDTO(
    val fareClass: String,
    val fareKey: String,
    val fares: List<FareDTO>
)