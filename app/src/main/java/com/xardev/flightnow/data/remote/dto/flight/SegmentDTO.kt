package com.xardev.flightnow.data.remote.dto.flight

data class SegmentDTO(
    val destination: String,
    val duration: String,
    val flightNumber: String,
    val origin: String,
    val segmentNr: Int,
    val time: List<String>,
    val timeUTC: List<String>
)