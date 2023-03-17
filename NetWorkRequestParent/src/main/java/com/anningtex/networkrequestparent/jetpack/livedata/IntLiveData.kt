package com.anningtex.networkrequestparent.jetpack.livedata

import androidx.lifecycle.MutableLiveData

/**
 * @author Song
 */
class IntLiveData(value: Int = 0) : MutableLiveData<Int>(value) {
    override fun getValue(): Int {
        return super.getValue()!!
    }
}