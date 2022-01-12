package com.oparate.test.services

import androidx.lifecycle.MutableLiveData
import com.oparate.test.model.CryptoResponse

interface MainListner {
    fun onGetLatestCrypto()
    fun onRefreshed(response: MutableLiveData<CryptoResponse?>)
    fun onSuccess(
        response: MutableLiveData<CryptoResponse?>,
        baseCurrency: String?,
        amount: String?
    )
    fun onError(str: String)
    fun onFailed(str: String, getLocally: Boolean)
}