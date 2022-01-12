package com.oparate.test.view_model

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.oparate.test.data.repository.CryptoRepository
import com.oparate.test.services.MainListner
import android.widget.AdapterView
import com.oparate.test.data.Api
import com.oparate.test.helper.SQLiteHelper


class MainViewModel : ViewModel() {

    var amount: String? = "1"
    var selectedCurrency: String? = null
    var mainListener: MainListner? = null
    var cryptoRepository: CryptoRepository? = CryptoRepository()


    fun onSelectItem(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        selectedCurrency = parent?.selectedItem.toString()
        retrieveLatestCrypto()
    }

    fun onGetCrypto(v: View) {
        if (amount != null && amount!!.isNotEmpty()) {
            mainListener?.onGetLatestCrypto()
            retrieveLatestCrypto()
        } else {
            mainListener?.onError("Please enter more than 1 value")
        }

    }

    private fun retrieveLatestCrypto() {
        var response = cryptoRepository?.latestCrypto(Api.RESPONSE_DATA_LIMIT, Api.CONVERT_VALUES)
        Log.e("ad", selectedCurrency.toString())
        response?.let { mainListener?.onSuccess(it, selectedCurrency, amount) }
    }

    fun onGetCryptoValue(str: CharSequence, s: Int, e: Int, count: Int) {
        if (str.length < 2) {
            mainListener?.onError("Please enter more than 1 value")
            return
        }
        /*mainListener?.onGetLatestCrypto()
        var response = cryptoRepository?.latestCrypto("20", selectedCurrency)
        response?.let { mainListener?.onSuccess(it) }*/
    }


}