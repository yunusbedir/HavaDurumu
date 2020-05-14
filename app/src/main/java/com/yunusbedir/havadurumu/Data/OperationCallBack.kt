package com.yunusbedir.havadurumu.Data


/**
 * Created by YUNUS BEDİR on 14.05.2020.
 */
interface OperationCallBack<T> {
    fun onSuccess(data: T?)
    fun onError(error: String?)
}