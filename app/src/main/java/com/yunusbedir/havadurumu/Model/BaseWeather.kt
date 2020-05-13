package com.yunusbedir.havadurumu.Model


import com.google.gson.annotations.SerializedName

data class BaseWeather(
    @SerializedName("current")
    var current: Current?,
    @SerializedName("daily")
    var daily: List<Daily>?,
    @SerializedName("hourly")
    var hourly: List<Hourly>?,
    @SerializedName("lat")
    var lat: Int?,
    @SerializedName("lon")
    var lon: Int?,
    @SerializedName("timezone")
    var timezone: String?
)