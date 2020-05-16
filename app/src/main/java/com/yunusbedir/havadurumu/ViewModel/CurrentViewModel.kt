package com.yunusbedir.havadurumu.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yunusbedir.havadurumu.Data.DataRepository
import com.yunusbedir.havadurumu.Data.OperationCallBack
import com.yunusbedir.havadurumu.Model.Region
import com.yunusbedir.havadurumu.Model.User
import com.yunusbedir.havadurumu.Model.weather.BaseWeather


/**
 * Created by YUNUS BEDİR on 14.05.2020.
 */
class CurrentViewModel(app: Application) : AndroidViewModel(app) {
    private val TAG = this.javaClass.simpleName
    private val repository = DataRepository(app)

    private val _weather = MutableLiveData<BaseWeather>()
    val weather: LiveData<BaseWeather> = _weather

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    private val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList

    fun loadCurrentWeather(region: Region?) {
        _isViewLoading.postValue(true)

        if (region != null) {
            repository.getWeather(
                region.lat!!,
                region.lon!!,
                object : OperationCallBack<BaseWeather> {
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
        } else {
            _isViewLoading.postValue(false)
            _onMessageError.postValue(Throwable(message = "Kullanici yok,konum belirlenmemiş"))
        }
    }

    fun loadUser() {
        repository.getUser(object : OperationCallBack<User> {
            override fun onSuccess(data: User?) {
                data?.let { user ->
                    _user.value = user
                }
            }

            override fun onError(error: Throwable?) {
                _user.value = null
            }

        })
    }
}