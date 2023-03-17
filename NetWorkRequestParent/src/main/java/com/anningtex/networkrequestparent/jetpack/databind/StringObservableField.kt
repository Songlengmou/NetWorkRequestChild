package com.anningtex.networkrequestparent.jetpack.databind

import androidx.databinding.ObservableField

/**
 * @author Song
 * desc:自定义的String类型 ObservableField  提供了默认值，防止返回的值出现空的情况
 */
class StringObservableField(value: String = "") : ObservableField<String>(value) {
    override fun get(): String {
        return super.get()!!
    }
}