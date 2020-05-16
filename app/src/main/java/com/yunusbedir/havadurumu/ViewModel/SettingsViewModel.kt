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
    private val TAG = this.javaClass.simpleName
    private val repository = DataRepository(app)

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _onMessageError = MutableLiveData<Throwable>()
    val onMessageError: LiveData<Throwable> = _onMessageError

    fun loadSettings() {
        repository.getUser(object : OperationCallBack<User> {
            override fun onSuccess(data: User?) {
                if (data != null) {
                    _user.value = data
                }
            }

            override fun onError(error: Throwable?) {
                _onMessageError.postValue(error)
            }

        })
    }

}