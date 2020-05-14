package com.yunusbedir.havadurumu.Data

import android.annotation.SuppressLint
import android.content.Context
import com.yunusbedir.havadurumu.Data.Api.WeatherService
import com.yunusbedir.havadurumu.Data.Csv.ReadCsv
import com.yunusbedir.havadurumu.Data.Room.UserDao
import com.yunusbedir.havadurumu.Data.Room.UserDatabase
import com.yunusbedir.havadurumu.Model.weather.BaseWeather
import com.yunusbedir.havadurumu.Model.Region
import com.yunusbedir.havadurumu.Model.User
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.internal.operators.flowable.FlowableObserveOn
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber


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
                }
            })
    }

    fun getSearchedList(text: String): List<Region> {
        return ReadCsv(context).getSearchedList(text)
    }

    @SuppressLint("CheckResult")
    fun getUser(operationCallBack: OperationCallBack<User>) {
        UserDatabase.invoke(context).userDao().getUser()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<User>() {
                override fun onSuccess(t: User) {
                    operationCallBack.onSuccess(t)
                }

                override fun onError(e: Throwable) {
                    operationCallBack.onError(e.localizedMessage)
                }

            })
    }
}