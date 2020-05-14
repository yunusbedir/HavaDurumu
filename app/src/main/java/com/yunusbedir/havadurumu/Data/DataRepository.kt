package com.yunusbedir.havadurumu.Data

import android.annotation.SuppressLint
import android.util.Log
import com.yunusbedir.havadurumu.Data.Api.WeatherService
import com.yunusbedir.havadurumu.Model.BaseWeather
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers


/**
 * Created by YUNUS BEDİR on 13.05.2020.
 */
class DataRepository {

    @SuppressLint("CheckResult")
    fun getWeather(
        lat: Int,
        lon: Int,
        onSuccess: (BaseWeather) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        WeatherService.getWeather(lat, lon)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<BaseWeather>() {
                override fun onSuccess(baseWeather: BaseWeather) {
                    onSuccess(baseWeather)
                }

                override fun onError(e: Throwable) {
                    onError(e)
                }

            })
    }
}