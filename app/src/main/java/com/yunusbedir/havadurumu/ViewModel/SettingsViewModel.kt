package com.yunusbedir.havadurumu.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yunusbedir.havadurumu.Data.DataRepository
import com.yunusbedir.havadurumu.Data.OperationCallBack
import com.yunusbedir.havadurumu.Model.Region
import com.yunusbedir.havadurumu.Model.User


/**
 * Created by YUNUS BEDİR on 14.05.2020.
 */
class SettingsViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = DataRepository(app)

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    fun loadSettings() {
        _isViewLoading.postValue(true)

        repository.getUser(object : OperationCallBack<User> {
            override fun onSuccess(data: User?) {
                _isViewLoading.postValue(false)

                if (data == null) {
                    _isEmpty.postValue(true)
                } else {
                    _user.value = data
                }
            }

            override fun onError(error: String?) {
                _isViewLoading.postValue(false)
                _onMessageError.postValue(error)
            }

        })
    }

}