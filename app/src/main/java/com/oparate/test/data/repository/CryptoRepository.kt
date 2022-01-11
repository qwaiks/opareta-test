package com.oparate.test.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.oparate.test.data.Api
import com.oparate.test.data.api.CryptoApi
import com.oparate.test.model.CryptoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CryptoRepository {

    private val retrofitService: CryptoApi = CryptoApi.getInstance()
    private val cryptoResponseLiveData: MutableLiveData<CryptoResponse?> =
        MutableLiveData<CryptoResponse?>()
    fun latestCrypto(limit: String?, convert: String?) : MutableLiveData<CryptoResponse?>  {
        retrofitService.getLatestCrypto(limit, convert, Api.API_KEY)
            .enqueue(object : Callback<CryptoResponse?> {

                override fun onResponse(
                    call: Call<CryptoResponse?>,
                    response: Response<CryptoResponse?>
                ) {
                    if (response.isSuccessful) {
                        cryptoResponseLiveData.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<CryptoResponse?>, t: Throwable) {
                    cryptoResponseLiveData.postValue(null)
                }


            }
            )
        return cryptoResponseLiveData

    }

    fun getLatestResponseBodyLiveData(): LiveData<CryptoResponse?> {
        return cryptoResponseLiveData
    }
}