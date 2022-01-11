package com.oparate.test.services

interface MainListner {
    fun onGetLatestCrypto()
    fun onError(str: String)
    fun onFailed(str: String, getLocally: Boolean)
}