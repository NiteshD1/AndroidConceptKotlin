package com.androidready.demo

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    var counter = 0

    fun getCountValue(): Int{
        counter += 1;
        return counter
    }
}