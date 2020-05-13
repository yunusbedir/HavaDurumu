package com.yunusbedir.havadurumu.Model


import com.google.gson.annotations.SerializedName

data class Hourly(
    @SerializedName("clouds")
    var clouds: Int?,
    @SerializedName("dew_point")
    var dewPoint: Double?,
    @SerializedName("dt")
    var dt: Int?,
    @SerializedName("feels_like")
    var feelsLike: Double?,
    @SerializedName("humidity")
    var humidity: Int?,
    @SerializedName("pressure")
    var pressure: Int?,
    @SerializedName("temp")
    var temp: Double?,
    @SerializedName("weather")
    var weather: List<Weather>?,
    @SerializedName("wind_deg")
    var windDeg: Int?,
    @SerializedName("wind_speed")
    var windSpeed: Double?
)