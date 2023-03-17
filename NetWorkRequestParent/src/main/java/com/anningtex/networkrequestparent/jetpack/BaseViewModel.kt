package com.anningtex.networkrequestparent.jetpack

import androidx.lifecycle.ViewModel
import com.anningtex.networkrequestparent.jetpack.state.SingleLiveEvent

/**
 * @author Song
 * desc: ViewModel的基类
 */
open class BaseViewModel : ViewModel() {

    val uiChange: UiChange by lazy { UiChange() }

    /**
     * 可通知Activity/fragment 做相关Ui操作
     */
    inner class UiChange {
        //显示加载框
        val showDialog by lazy { SingleLiveEvent<String>() }

        //隐藏
        val dismissDialog by lazy { SingleLiveEvent<Void>() }
    }
}