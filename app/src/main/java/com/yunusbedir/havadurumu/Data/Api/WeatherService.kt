package com.yunusbedir.havadurumu.Data.Api

import com.yunusbedir.havadurumu.Model.weather.BaseWeather
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by YUNUS BEDİR on 13.05.2020.
 */
object WeatherService {

    private const val BASE_URL = "https://api.openweathermap.org"

    private val api: WeatherApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    fun getWeather(lat: Int, lon: Int): Single<BaseWeather> {
        return api.getWeather(lat, lon)
    }

    fun getWeather(lat: Int, lon: Int, units: String): Single<BaseWeather> {
        return api.getWeather(lat, lon, units = units)
    }

    fun getWeather(lat: Int, lon: Int, lang: String, units: String): Single<BaseWeather> {
        return api.getWeather(lat, lon, units = units, lang = lang)
    }

}