package com.oparate.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.oparate.test.databinding.ActivityMainBinding
import com.oparate.test.model.CryptoResponse
import com.oparate.test.services.MainListner
import com.oparate.test.util.hide
import com.oparate.test.util.show
import com.oparate.test.util.toast
import com.oparate.test.view_model.MainViewModel

class MainActivity : AppCompatActivity(), MainListner {
    var binding:ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        val viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding?.viewmodel = viewModel
        viewModel.mainListener = this


    }

    override fun onGetLatestCrypto() {
        //show progress bar, hide recycler
        binding?.rcyCrypto?.hide()
        binding?.lytProgressBar?.show()
    }

    override fun onSuccess(response: MutableLiveData<CryptoResponse?>) {
        binding?.lytProgressBar?.hide()
        binding?.rcyCrypto?.show()
        response.observe(this, Observer {
            toast(it.toString())
            it?.data?.first()?.let { it1 -> Log.e("apiRespone", it1.name) };

        })

    }



    override fun onError(str: String) {
       //show error message, hide recycler
    }



    override fun onFailed(str: String, getLocally: Boolean) {
        toast(str)
        if (getLocally) {
            toast("Checking local values")
        }

    }
}