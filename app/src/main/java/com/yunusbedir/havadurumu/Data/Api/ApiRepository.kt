package com.yunusbedir.havadurumu.Data.Api

import android.annotation.SuppressLint
import android.content.Context
import com.yunusbedir.havadurumu.Data.OperationCallBack
import com.yunusbedir.havadurumu.Model.weather.BaseWeather
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers


/**
 * Created by YUNUS BEDİR on 17.05.2020.
 */
class ApiRepository(private val context : Context) {
    private val TAG = this.javaClass.simpleName

    @SuppressLint("CheckResult")
    fun getWeather(
        lat: Int,
        lon: Int,
        operationCallBack: OperationCallBack<BaseWeather>
    ) {
        WeatherService.getWeather(lat, lon)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<BaseWeather>() {
                override fun onSuccess(baseWeather: BaseWeather) {
                    operationCallBack.onSuccess(baseWeather)
                }

                override fun onError(e: Throwable) {
                    operationCallBack.onError(e)
                }
            })
    }

}