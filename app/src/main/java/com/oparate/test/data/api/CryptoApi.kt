package com.oparate.test.data.api

import com.oparate.test.data.Api
import com.oparate.test.model.CryptoResponse
import com.oparate.test.services.NetworkConnectionInterceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CryptoApi {

    @GET(Api.LATEST_CRYPTO)
    fun getLatestCrypto(
        @Query("limit") limit: String?,
        @Query("convert") convert: String?,
        @Header("X-CMC_PRO_API_KEY") apiKey: String?,
    ): Call<CryptoResponse>

    companion object {
        var retrofitService: CryptoApi? = null

        fun getInstance(): CryptoApi {
           /* val okHttpClient =
                OkHttpClient.Builder().addInterceptor().build()*/
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()/*.client(okHttpClient)*/
                    .baseUrl(Api.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(CryptoApi::class.java)
            }
            return retrofitService!!
        }
    }
}