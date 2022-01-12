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

    override fun onRefreshed(response: MutableLiveData<CryptoResponse?>) {
        response.observe(this, Observer {
            saveResponse(it!!)

        })
    }


    override fun onSuccess(
        response: MutableLiveData<CryptoResponse?>,
        baseCurrency: String?,
        amount: String?
    ) {
        binding?.lytProgressBar?.hide()
        binding?.rcyCrypto?.show()
        response.observe(this, Observer {
            if (it?.data == null || it?.data.isEmpty()) {
                onFailed("Please check your network connection", true)
            } else {
                adapter?.setValues(it!!.data, amount!!, baseCurrency!!)
                onRefreshed(response!!)
                //it?.data?.first()?.let { it1 -> Log.e("QWEDD", it1.name) }
            }

        })

    }


    override fun onError(str: String) {
        //show error message, hide recycler
    }


    override fun onFailed(str: String, getLocally: Boolean) {
        toast(str)
        if (getLocally) {
            toast("Retrieving from local")
            retrieveFromSQLite()
        }

    }

    fun saveResponse(res: CryptoResponse) {
        val status = sqLiteHelper?.insertCurrency(res)
        if (status!! > -1) {
            toast("Saved to SQLite")
        }

    }

    fun retrieveFromSQLite() {
        val cryptoResponse = sqLiteHelper?.getLatestCurrency()
        var timeStamp =SimpleDateFormat("yyyy-MM-dd").parse(cryptoResponse?.status!!.timestamp)
        var currentStamp = Date()
        var diff = (currentStamp.time - timeStamp.time) / 1000
        if (diff > 60) {
            //Data elapsed 60 seconds
            onFailed("Failed, Outdated data",false)
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
    }
}