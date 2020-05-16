package com.yunusbedir.havadurumu.Data.Room

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.yunusbedir.havadurumu.Data.OperationCallBack
import com.yunusbedir.havadurumu.Model.User
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers


/**
 * Created by YUNUS BEDİR on 17.05.2020.
 */
class RoomRepository(private val context: Context) {
    private val TAG = this.javaClass.simpleName


    @SuppressLint("CheckResult")
    private fun insertUser(user: User, operationCallBack: OperationCallBack<Boolean>) {
        UserDatabase.invoke(context).userDao().insert(user)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : CompletableObserver {
                override fun onComplete() {
                    Log.i(TAG, "success insert User")
                    operationCallBack.onSuccess(true)
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    operationCallBack.onError(e)
                }

            })
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
                    operationCallBack.onError(e)
                }

            })
    }

    fun updateUser(user: User, operationCallBack: OperationCallBack<Boolean>) {
        getUser(object : OperationCallBack<User> {
            @SuppressLint("CheckResult")
            override fun onSuccess(data: User?) {
                user.userId = data?.userId
                if (user.region == null)
                    user.region = data?.region
                UserDatabase.invoke(context).userDao().update(user)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : CompletableObserver {
                        override fun onComplete() {
                            Log.i(TAG, "success update User")
                            operationCallBack.onSuccess(true)
                        }

                        override fun onSubscribe(d: Disposable) {

                        }

                        override fun onError(e: Throwable) {
                            operationCallBack.onError(e)
                        }

                    })
            }

            override fun onError(error: Throwable?) {
                insertUser(user, operationCallBack)
            }

        })
    }


}