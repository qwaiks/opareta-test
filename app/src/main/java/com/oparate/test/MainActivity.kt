package com.oparate.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.oparate.test.adapter.CryptoListAdapter
import com.oparate.test.databinding.ActivityMainBinding
import com.oparate.test.helper.SQLiteHelper
import com.oparate.test.model.CryptoResponse
import com.oparate.test.model.DataResponse
import com.oparate.test.services.MainListner
import com.oparate.test.util.hide
import com.oparate.test.util.show
import com.oparate.test.util.toast
import com.oparate.test.view_model.MainViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class MainActivity : AppCompatActivity(), MainListner {
    var binding: ActivityMainBinding? = null
    var adapter: CryptoListAdapter? = null
    private var sqLiteHelper: SQLiteHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        val viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)


        binding?.viewmodel = viewModel
        viewModel.mainListener = this
        adapter = CryptoListAdapter()
        binding?.rcyCrypto?.adapter = adapter
        sqLiteHelper = SQLiteHelper(this)


    }

    override fun onGetLatestCrypto() {
        //show progress bar, hide recycler
        binding?.rcyCrypto?.hide()
        binding?.lytProgressBar?.show()
    }

    override fun onRefreshed(response: CryptoResponse?) {
        if(response != null){
            saveResponse(response)
        }
    }


    override fun onSuccess(
        response: MutableLiveData<CryptoResponse?>,
        baseCurrency: String?,
        amount: String?
    ) {
        binding?.lytProgressBar?.hide()
        binding?.rcyCrypto?.show()
        response.observe(this, Observer {
            if (it?.data == null || it.data.isEmpty()) {
                onFailed("Please check your network connection", true)
            } else {
                adapter?.setValues(it.data, amount!!, baseCurrency!!)
                Log.e("adx", baseCurrency!!)
                onRefreshed(it)
            }

        })

    }


    override fun onError(str: String) {
        //show error message, hide recycler
        binding?.txtError?.text = str
        binding?.lytProgressBar?.hide()
        binding?.rcyCrypto?.hide()
        binding?.txtError?.show()

    }


    override fun onFailed(str: String, getLocally: Boolean) {
        //toast(str)
        if (getLocally) {
           // toast("Retrieving from local")
            retrieveFromSQLite()
        } else {
            onError(str)
        }

    }

    fun saveResponse(res: CryptoResponse) {
        val status = sqLiteHelper?.insertCurrency(res)

    }

    private fun retrieveFromSQLite() {
        val cryptoResponse = sqLiteHelper?.getLatestCurrency()
        if (cryptoResponse != null) {
            var timeStamp = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss").parse(cryptoResponse?.status!!.timestamp)
            var currentStamp = Date()
            var diff = (currentStamp.time - timeStamp.time) / 1000
           // toast(diff.toString())
            if (diff > 4000) {
                //Data elapsed 60 seconds
                onFailed("Failed, Outdated Data", false)
                adapter?.setValues(
                    listOf(),
                    binding?.viewmodel?.amount!!,
                    binding?.viewmodel?.selectedCurrency!!
                )
                return
            }
            adapter?.setValues(
                cryptoResponse!!.data,
                binding?.viewmodel?.amount!!,
                binding?.viewmodel?.selectedCurrency!!
            )
        } else {
            onError("Failed No Data to Preview")
        }
    }
}