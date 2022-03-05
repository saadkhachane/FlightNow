package com.xardev.flightnow.models

import android.os.Parcel
import android.os.Parcelable

data class Flight(
    var duration: String? = "",
    val flightKey: String? = "",
    val flightNumber: String? = "",
    var operatedBy: String? = "",
    var time: List<String>?,
    val timeUTC: List<String>?,
    val amount: String? = "",
    val currency: String? = "",
    val tripType: String? = "",
    val origin: String? = "",
    val originName: String? = "",
    val destinationName: String? = "",
    val destination: String? = ""
) : Parcelable, Comparable<Flight> {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.createStringArrayList(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(duration)
        parcel.writeString(flightKey)
        parcel.writeString(flightNumber)
        parcel.writeString(operatedBy)
        parcel.writeStringList(time)
        parcel.writeStringList(timeUTC)
        parcel.writeString(amount)
        parcel.writeString(currency)
        parcel.writeString(tripType)
        parcel.writeString(origin)
        parcel.writeString(originName)
        parcel.writeString(destinationName)
        parcel.writeString(destination)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Flight> {
        override fun createFromParcel(parcel: Parcel): Flight {
            return Flight(parcel)
        }

        override fun newArray(size: Int): Array<Flight?> {
            return arrayOfNulls(size)
        }
    }

    override fun compareTo(other: Flight): Int {
        return this.compareTo(other)
    }
}
