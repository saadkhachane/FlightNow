package com.xardev.flightnow.data.remote.dto.Flight

data class FareDTO(
    val amount: Double,
    val count: Int,
    val discountAmount: Double,
    val discountInPercent: Int,
    val hasBogof: Boolean,
    val hasDiscount: Boolean,
    val hasPromoDiscount: Boolean,
    val publishedFare: Double,
    val type: String
)