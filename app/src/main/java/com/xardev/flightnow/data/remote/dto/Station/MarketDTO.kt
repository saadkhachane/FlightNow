package com.xardev.flightnow.data.remote.dto.Station

data class MarketDTO(
    val code: String,
    val group: Any,
    val onlyConnecting: Boolean,
    val onlyPrintedBoardingPass: Boolean,
    val pendingApproval: Boolean,
    val stops: List<StopDTO>
)