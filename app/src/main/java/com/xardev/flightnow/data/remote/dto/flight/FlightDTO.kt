package com.xardev.flightnow.data.remote.dto.flight

data class FlightDTO(
    val duration: String,
    val faresLeft: Int,
    val flightKey: String,
    val flightNumber: String,
    val infantsLeft: Int,
    val operatedBy: String,
    val regularFare: RegularFareDTO,
    val segments: List<SegmentDTO>,
    val time: List<String>,
    val timeUTC: List<String>
)