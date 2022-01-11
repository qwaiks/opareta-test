package com.oparate.test.services

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.oparate.test.model.CryptoResponse

interface MainListner {
    fun onGetLatestCrypto()
    fun onSuccess(response: MutableLiveData<CryptoResponse?>)
    fun onError(str: String)
    fun onFailed(str: String, getLocally: Boolean)
}