package com.xardev.flightnow.data.remote.dto.Station

import com.xardev.flightnow.models.Station

data class StationDTO(
    val airportShopping: Boolean?,
    val alias: List<String>?,
    val alternateName: Any?,
    val code: String?,
    val countryAlias: Any?,
    val countryCode: String?,
    val countryGroupCode: String?,
    val countryGroupName: String?,
    val countryName: String?,
    val latitude: String?,
    val longitude: String?,
    val markets: List<MarketDTO>?,
    val mobileBoardingPass: Boolean?,
    val name: String?,
    val notices: Any?,
    val timeZoneCode: String?,
    val tripCardImageUrl: String?
){

    fun toStation() =
        Station(
            code,
            countryCode,
            countryGroupCode,
            countryGroupName,
            countryName,
            mobileBoardingPass,
            name,
            timeZoneCode,
            tripCardImageUrl
        )

}

