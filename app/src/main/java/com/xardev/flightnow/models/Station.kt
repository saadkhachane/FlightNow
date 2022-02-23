package com.xardev.flightnow.models


data class Station(
    val code: String?,
    val countryCode: String?,
    val countryGroupCode: String?,
    val countryGroupName: String?,
    val countryName: String?,
    val mobileBoardingPass: Boolean?,
    val name: String?,
    val timeZoneCode: String?,
    val tripCardImageUrl: String?
)
