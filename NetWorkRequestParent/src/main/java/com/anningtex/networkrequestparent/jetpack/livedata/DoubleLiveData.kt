package com.anningtex.networkrequestparent.jetpack.livedata

import androidx.lifecycle.MutableLiveData

/**
 * @author Song
 */
class DoubleLiveData(var value: Double = 0.0) : MutableLiveData<Double>(value) {
    override fun getValue(): Double {
        return super.getValue()!!
    }
}