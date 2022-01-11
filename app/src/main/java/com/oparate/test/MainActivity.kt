package com.oparate.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.oparate.test.databinding.ActivityMainBinding
import com.oparate.test.services.MainListner
import com.oparate.test.util.toast
import com.oparate.test.view_model.MainViewModel

class MainActivity : AppCompatActivity(), MainListner {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        val viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding.viewmodel = viewModel
        viewModel.mainListener = this

    }

    override fun onGetLatestCrypto() {
        toast("Getting latest amounts")
    }

    override fun onError(str: String) {
        toast(str)
    }

    override fun onFailed(str: String, getLocally: Boolean) {
        toast(str)
        if (getLocally) {
            toast("Checking local values")
        }

    }
}