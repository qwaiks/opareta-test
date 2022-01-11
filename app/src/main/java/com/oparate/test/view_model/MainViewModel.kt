package com.oparate.test.view_model

import android.view.View
import androidx.lifecycle.ViewModel
import com.oparate.test.services.MainListner

class MainViewModel : ViewModel() {

    var amount: String? = null
    var mainListener: MainListner? = null

    fun onGetCryptoValue(str: CharSequence, s: Int, e: Int, count: Int) {

        if (str.length < 2) {
            mainListener?.onError("Please enter more than 1 value")
            return
        }
        mainListener?.onGetLatestCrypto()


    }
}