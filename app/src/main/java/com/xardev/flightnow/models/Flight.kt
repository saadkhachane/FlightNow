package com.xardev.flightnow.models

import com.xardev.flightnow.data.remote.dto.Flight.RegularFareDTO
import com.xardev.flightnow.data.remote.dto.Flight.SegmentDTO

data class Flight(
    val duration: String?,
    val flightKey: String?,
    val flightNumber: String?,
    val operatedBy: String?,
    val time: List<String>?,
    val timeUTC: List<String>?,
    val amount: String?,
    val currency: String?,
    val tripType: String?
)
