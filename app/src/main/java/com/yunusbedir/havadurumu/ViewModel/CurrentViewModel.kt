package com.yunusbedir.havadurumu.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yunusbedir.havadurumu.Data.DataRepository
import com.yunusbedir.havadurumu.Data.OperationCallBack
import com.yunusbedir.havadurumu.Model.weather.BaseWeather


/**
 * Created by YUNUS BEDİR on 14.05.2020.
 */
class CurrentViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = DataRepository(app)

    private val _weather = MutableLiveData<BaseWeather>()
    val weather: LiveData<BaseWeather> = _weather

    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    private val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList

    fun loadCurrentWeather() {
        _isViewLoading.postValue(true)

        repository.getWeather(33, 33, object : OperationCallBack<BaseWeather> {
            override fun onSuccess(data: BaseWeather?) {
                _isViewLoading.postValue(false)

                if (data == null) {
                    _isEmptyList.postValue(true)
                } else {
                    _weather.value = data
                }
            }

            override fun onError(error: Throwable?) {
                _isViewLoading.postValue(false)
                _onMessageError.postValue(error)
            }
        })
    }
}