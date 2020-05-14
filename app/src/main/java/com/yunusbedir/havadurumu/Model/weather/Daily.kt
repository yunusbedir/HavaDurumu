package com.yunusbedir.havadurumu.Model.weather


import com.google.gson.annotations.SerializedName

data class Daily(
    @SerializedName("clouds")
    var clouds: Int?,
    @SerializedName("dew_point")
    var dewPoint: Double?,
    @SerializedName("dt")
    var dt: Int?,
    @SerializedName("feels_like")
    var feelsLike: FeelsLike?,
    @SerializedName("humidity")
    var humidity: Int?,
    @SerializedName("pressure")
    var pressure: Int?,
    @SerializedName("sunrise")
    var sunrise: Int?,
    @SerializedName("sunset")
    var sunset: Int?,
    @SerializedName("temp")
    var temp: Temp?,
    @SerializedName("uvi")
    var uvi: Double?,
    @SerializedName("weather")
    var weather: List<Weather>?,
    @SerializedName("wind_deg")
    var windDeg: Int?,
    @SerializedName("wind_speed")
    var windSpeed: Double?
)