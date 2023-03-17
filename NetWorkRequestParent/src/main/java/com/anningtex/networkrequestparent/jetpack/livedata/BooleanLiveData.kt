package com.anningtex.networkrequestparent.jetpack.livedata

import androidx.lifecycle.MutableLiveData

/**
 * @author Song
 */
class BooleanLiveData(value: Boolean = false) : MutableLiveData<Boolean>(value) {
    override fun getValue(): Boolean {
        return super.getValue()!!
    }
}