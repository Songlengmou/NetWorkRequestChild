package com.anningtex.networkrequestparent.jetpack.livedata

import androidx.lifecycle.MutableLiveData

/**
 * @author Song
 */
class StringLiveData(value: String = "") : MutableLiveData<String>(value) {
    override fun getValue(): String {
        return super.getValue()!!
    }
}