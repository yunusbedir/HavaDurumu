package com.yunusbedir.havadurumu.Data.Api

import com.yunusbedir.havadurumu.Model.BaseWeather
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Created by YUNUS BEDİR on 13.05.2020.
 */
interface WeatherApi {

    /*
    lat = enlem
    lon = boylam
    exclude = almak istediğimiz veri tiplerini yazıyoruz. Aralarında virgül olacak şekilde
        aşağıdakiler yazılabilir;
            * current
            * minutely
            * hourly
            * daily
    appid = uygulama id si
    lang = hangi dil olduğunu belirliyoruz.
        Türkçe için: tr
    units = derece türünü belirliyoruz.
        Kelvin için: default değer vermemize gerek yok
        Celsius için: 'metric'
        Fahrenheit için: 'imperial'
    */
    @GET("/data/2.5/onecall")
    fun getWeather(
        @Query("lat") lat: Int,
        @Query("lon") lon: Int,
        @Query("appid") appId: String = "bf4b8d4a6c8b74617aefae66ab406abb",
        @Query("lang") lang: String = "tr",
        @Query("units") units: String = "metric"
    ): Single<BaseWeather>
}