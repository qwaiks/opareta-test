package com.oparate.test.view_model

import android.view.View
import androidx.lifecycle.ViewModel
import com.oparate.test.data.repository.CryptoRepository
import com.oparate.test.services.MainListner
import android.widget.AdapterView




class MainViewModel : ViewModel() {

    var amount: String? = null
    var selectedCurrency : String? = null
    var mainListener: MainListner? = null
    var cryptoRepository:CryptoRepository ? = CryptoRepository()
    var loading :Boolean? = null

    fun onSelectItem(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        selectedCurrency = parent?.selectedItem.toString();


    }

    fun onGetCrypto(v:View){
        mainListener?.onGetLatestCrypto()
        var response = cryptoRepository?.latestCrypto("20", selectedCurrency)
        response?.let { mainListener?.onSuccess(it) }
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