package com.yunusbedir.havadurumu.Data

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.yunusbedir.havadurumu.Data.Api.ApiRepository
import com.yunusbedir.havadurumu.Data.Api.WeatherService
import com.yunusbedir.havadurumu.Data.Room.RoomRepository
import com.yunusbedir.havadurumu.Data.Room.UserDatabase
import com.yunusbedir.havadurumu.Model.weather.BaseWeather
import com.yunusbedir.havadurumu.Model.User
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers


/**
 * Created by YUNUS BEDİR on 13.05.2020.
 */
class DataRepository(context: Context) {
    private val TAG = this.javaClass.simpleName
    private val roomRepository = RoomRepository(context)
    private val apiRepository = ApiRepository(context)

    fun getWeather(
        lat: Int,
        lon: Int,
        operationCallBack: OperationCallBack<BaseWeather>
    ) {
        apiRepository.getWeather(lat, lon, operationCallBack)
    }

    fun getUser(operationCallBack: OperationCallBack<User>) {
        roomRepository.getUser(operationCallBack)
    }

    fun updateUser(user: User, operationCallBack: OperationCallBack<Boolean>) {
        roomRepository.updateUser(user, operationCallBack)
    }
}