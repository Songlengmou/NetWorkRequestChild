package com.anningtex.networkrequestparent.jetpack.ext

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.anningtex.networkrequestparent.jetpack.BaseViewModel
import java.lang.reflect.ParameterizedType

/**
 * 获取vm clazz
 */
@Suppress("UNCHECKED_CAST")
fun <VM> getVmClazz(obj: Any): VM {
    return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as VM
}

fun <VM : BaseViewModel> AppCompatActivity.getViewmodel(): VM {
    return ViewModelProvider(this)[getVmClazz(this) as Class<VM>]
}
