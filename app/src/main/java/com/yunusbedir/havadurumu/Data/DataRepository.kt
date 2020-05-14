package com.yunusbedir.havadurumu.Data

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.yunusbedir.havadurumu.Data.Api.WeatherService
import com.yunusbedir.havadurumu.Data.Csv.ReadCsv
import com.yunusbedir.havadurumu.Model.BaseWeather
import com.yunusbedir.havadurumu.Model.Region
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers


/**
 * Created by YUNUS BEDİR on 13.05.2020.
 */
class DataRepository(private val context: Context) {

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
                    operationCallBack.onError(e.localizedMessage!!)
                    //
                }



            })
    }

    fun getSearchedList(text: String): List<Region> {
        return ReadCsv(context).getSearchedList(text)
    }
}