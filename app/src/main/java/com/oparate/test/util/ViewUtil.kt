package com.oparate.test.util

import android.content.Context
import android.view.View
import android.widget.Toast

fun Context.toast(str:String){
    Toast.makeText(this, str,Toast.LENGTH_SHORT).show()
}

fun View.show(){
    visibility = View.VISIBLE
}

fun View.hide(){
    visibility = View.GONE
}
