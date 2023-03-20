package com.anningtex.networkrequestchild.demo.weight

import com.anningtex.networkrequestchild.R
import com.kingja.loadsir.callback.Callback

class ErrorCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.layout_error
    }
}
