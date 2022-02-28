package com.xardev.flightnow.data.remote.dto.flight

data class RegularFareDTO(
    val fareClass: String,
    val fareKey: String,
    val fares: List<FareDTO>
)